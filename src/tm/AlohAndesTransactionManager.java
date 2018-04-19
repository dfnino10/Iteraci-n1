package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import dao.DAOCliente;
import dao.DAOEspacio;
import dao.DAOOperador;
import dao.DAOReserva;
import vos.Cliente;
import vos.Espacio;
import vos.Operador;
import vos.RFC1;
import vos.Reserva;

public class AlohAndesTransactionManager 
{
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	private String connectionDataPath;

	private String user;

	private String password;

	private String url;

	private String driver;

	private Connection conn;

	public AlohAndesTransactionManager(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	// RF4

	public void addReserva(Reserva reserva) throws Exception 
	{
		DAOReserva daoReserva = new DAOReserva();
		DAOCliente daoCliente = new DAOCliente();
		DAOEspacio daoEspacio = new DAOEspacio();
		DAOOperador daoOperador = new DAOOperador();

		try {
			////// Transacci�n
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoCliente.setConn(conn);
			daoEspacio.setConn(conn);
			daoOperador.setConn(conn);

			Cliente cliente = null;
			Espacio espacio = null;
			try {
				cliente = daoCliente.buscarCliente(reserva.getIdCliente());
				espacio = daoEspacio.buscarEspacio(reserva.getIdEspacio());
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw e;
			}		
			
			Date fecha = new Date();
			
			if (fecha.after(reserva.getFechaInicioDate())) {
				throw new Exception("La reserva debe iniciar despu�s que la fecha actual");
			}

			if (daoOperador.buscarOperador(espacio.getOperador()).getCategoria().getCategoria().toUpperCase().equals("MIEMBRO_DE_LA_COMUNIDAD")
					|| daoOperador.buscarOperador(espacio.getOperador()).getCategoria().getCategoria().toUpperCase().equals("PERSONA_NATURAL")) {
				if (reserva.getDuracion() <= 30) {
					throw new Exception(
							"La reserva tiene que durar m�nimo 30 d�as si se quiere reservar un espacio de ese operador");
				}
			}

			if (espacio.getCapacidad() < espacio.calcularOcupacionEnFecha(reserva.getFechaInicioDate(), conn) + 1) {
				throw new Exception("La nueva reserva excedir�a la capacidad del espacio a reservar");
			}

			if (daoOperador.buscarOperador(espacio.getOperador()).getCategoria().getCategoria().toUpperCase().equals("VIVIENDA_UNIVERSITARIA")
					&& (cliente.getVinculo().getVinculo().toUpperCase().equals("ESTUDIANTE") || cliente.getVinculo().getVinculo().toUpperCase().equals("PROFESOR")
							|| cliente.getVinculo().getVinculo().toUpperCase().equals("EMPLEADO")
							|| cliente.getVinculo().getVinculo().toUpperCase().equals("PROFESOR_INVITADO"))) {
				throw new Exception("S�lo estudiantes, profesores y empleados pueden usar vivienda universitaria");
			}
			
			
			
			if (cliente.reservaHoy(conn, fecha)) {
				throw new Exception("No puede hacerse m�s de una reserva al d�a");
			}

			if (espacio.getFechaRetiro() != null && reserva.calcularFechaFin().after(espacio.getFechaRetiroDate())) {
				throw new Exception(
						"No se puede reservar con esta duraci�n y fecha de inicio porque el espacio se retira antes de finalizar la reserva");
			}

			daoReserva.addReserva(reserva);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	// RF5

	public void cancelarReserva(Reserva reserva) throws Exception {
		DAOReserva daoReserva = new DAOReserva();
		DAOCliente daoCliente = new DAOCliente();
		DAOEspacio daoEspacio = new DAOEspacio();

		try {
			////// Transacci�n
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoCliente.setConn(conn);
			daoEspacio.setConn(conn);

			Cliente cliente = null;
			Espacio espacio = null;
			try {
				cliente = daoCliente.buscarCliente(reserva.getIdCliente());
				espacio = daoEspacio.buscarEspacio(reserva.getIdEspacio());
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw e;
			}

			if (reserva.isCancelado()) {
				throw new Exception("La reserva ya est� cancelada");
			}

			Date fechaCancelacion = new Date();

			if (reserva.getDuracion() < 7 && fechaCancelacion.before(reserva.calcularFechaConDiasDespues(4))) {
				reserva.setCancelado(true);
				espacio.setPrecio(espacio.getPrecio() * 0.1);
			}

			if (reserva.getDuracion() < 7 && fechaCancelacion.after(reserva.calcularFechaConDiasDespues(3))) {
				reserva.setCancelado(true);
				espacio.setPrecio(espacio.getPrecio() * 0.3);
			}

			if (reserva.getDuracion() > 7 && fechaCancelacion.before(reserva.calcularFechaConDiasDespues(8))) {
				reserva.setCancelado(true);
				espacio.setPrecio(espacio.getPrecio() * 0.1);
			}

			if (reserva.getDuracion() > 7 && fechaCancelacion.after(reserva.calcularFechaConDiasDespues(7))) {
				reserva.setCancelado(true);
				espacio.setPrecio(espacio.getPrecio() * 0.3);
			}

			daoReserva.updateReserva(reserva);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReserva.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	// RF6

	public void cancelarEspacio(Espacio espacio, Date fechaCancelacion) throws Exception {
		DAOReserva daoReserva = new DAOReserva();
		DAOOperador daoOperador = new DAOOperador();
		DAOEspacio daoEspacio = new DAOEspacio();

		try {
			////// Transacción
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoOperador.setConn(conn);
			daoEspacio.setConn(conn);

			Operador operador = null;
			List<Reserva> reservas = new ArrayList<Reserva>();

			try {
				espacio = daoEspacio.buscarEspacio(espacio.getId());
			} catch (Exception e) {
				throw new Exception("No hay espacio con dicho id para poder cancelarlo.");
			}
			try {
				operador = daoOperador.buscarOperador(espacio.getOperador());
				List<Long> reservasId = daoReserva.buscarReservasIdEspacio(espacio.getId());
				for(long id : reservasId)
				{
					reservas.add(daoReserva.buscarReserva(id, espacio.getId()));
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw e;
			}

			for (Reserva r : reservas) {
				if (r.calcularFechaFin().after(fechaCancelacion)) {
					throw new Exception(
							"Hay reservas hechas en el espacio que culminan despu�s de la cancelaci�n propuesta. Aseg�rese que no se est� comprometido.");
				}
			}		
			
			espacio.setFechaRetiroDate(fechaCancelacion);
			
			daoEspacio.updateEspacio(espacio);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReserva.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	// RFC1

	public List<RFC1> ingresosOperadores() throws Exception {
		DAOOperador daoOperador = new DAOOperador();

		List<RFC1> resultado = new ArrayList<RFC1>();
		try {
			this.conn = darConexion();
			daoOperador.setConn(conn);

			resultado = daoOperador.obtenerIngresosOperadores();

			return resultado;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperador.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	// RFC2

	public List<Espacio> espaciosPopulares() throws Exception {
		DAOEspacio daoEspacio = new DAOEspacio();

		List<Espacio> resultado = new ArrayList<Espacio>();
		try {
			this.conn = darConexion();
			daoEspacio.setConn(conn);

			resultado = daoEspacio.espaciosPopulares();

			return resultado;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspacio.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
}
