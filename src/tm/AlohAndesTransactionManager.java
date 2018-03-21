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
import dao.DAOPreferencia;
import dao.DAOReserva;
import vos.Cliente;
import vos.Cliente.Vinculo;
import vos.Espacio;
import vos.ListaClientes;
import vos.Operador;
import vos.Preferencia;
import vos.Operador.CategoriaOperador;
import vos.Reserva;

public class AlohAndesTransactionManager 
{
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	private  String connectionDataPath;

	private String user;

	private String password;

	private String url;

	private String driver;

	private Connection conn;

	public AlohAndesTransactionManager(String contextPathP) 
	{
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}
	
	private void initConnectionData()
	{
		try 
		{
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
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private Connection darConexion() throws SQLException 
	{
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}
	
	//RF4
	
	public void addReserva(Reserva reserva) throws Exception
	{
		DAOReserva daoReserva = new DAOReserva();
		DAOCliente daoCliente = new DAOCliente();
		DAOEspacio daoEspacio = new DAOEspacio();
		
		try 
		{
			//////Transacci�n
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoCliente.setConn(conn);
			daoEspacio.setConn(conn);
			
			Cliente cliente = null;
			Espacio espacio = null;
			try
			{
				cliente =daoCliente.buscarCliente(reserva.getIdCliente());
				espacio = daoEspacio.buscarEspacio(reserva.getIdEspacio());
			}
			catch(Exception e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw e;
			}			
			
			Date fecha = new Date();
			
			if(fecha.after(reserva.getFechaInicio()))
			{
				throw new Exception("La reserva debe iniciar despu�s que la fecha actual");
			}
			
			if(espacio.getOperador().getCategoria() == CategoriaOperador.MIEMBRO_DE_LA_COMUNIDAD || espacio.getOperador().getCategoria() == CategoriaOperador.PERSONA_NATURAL)
			{
				if(reserva.getDuracion() <= 30)
				{
					throw new Exception("La reserva tiene que durar m�nimo 30 d�as si se quiere reservar un espacio de ese operador");
				}
			}			
			
			if(espacio.getCapacidad() < espacio.calcularOcupacionEnFecha(reserva.getFechaInicio()) + 1)
			{
				throw new Exception("La nueva reserva excedir�a la capacidad del espacio a reservar");
			}
			
			if(espacio.getOperador().getCategoria() == CategoriaOperador.VIVIENDA_UNIVERSITARIA && (cliente.getVinculo() != Vinculo.ESTUDIANTE || cliente.getVinculo() != Vinculo.PROFESOR || cliente.getVinculo() != Vinculo.EMPLEADO || cliente.getVinculo() != Vinculo.PROFESOR_INVITADO))
			{
				throw new Exception("S�lo estudiantes, profesores y empleados pueden usar vivienda universitaria");
			}
			
			if(cliente.reservaHoy(reserva.getFechaReserva()))
			{
				throw new Exception("No puede hacerse m�s de una resrva al d�a");
			}
			
			if( espacio.getFechaRetiro() != null && reserva.calcularFechaFin().after(espacio.getFechaRetiro()))	
			{
				throw new Exception("No se puede reservar con esta duraci�n y fecha de inicio porque el espacio se retira antes de finalizar la reserva");
			}
			
			daoReserva.addReserva(reserva);
			conn.commit();
		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		catch (Exception e) 
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception)
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	//RF5
	
	public void deleteReserva(Reserva reserva) throws Exception 
	{
		DAOReserva daoReserva = new DAOReserva();
		DAOCliente daoCliente = new DAOCliente();
		DAOEspacio daoEspacio = new DAOEspacio();
		
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoCliente.setConn(conn);
			daoEspacio.setConn(conn);
			
			Cliente cliente = null;
			Espacio espacio = null;
			try
			{
				cliente =daoCliente.buscarCliente(reserva.getIdCliente());
				espacio = daoEspacio.buscarEspacio(reserva.getIdEspacio());
			}
			catch(Exception e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw e;
			}					
			
			Date fechaCancelacion = new Date();
			
			if(reserva.getDuracion() < 7 && fechaCancelacion.before(reserva.calcularFechaConDiasDespues(4)))
			{
				throw new Exception("Debe pagarse el 10% de la reserva: " + espacio.getPrecio()*0.1);
			}
			
			if(reserva.getDuracion() < 7 && fechaCancelacion.after(reserva.calcularFechaConDiasDespues(3)))
			{
				throw new Exception("Debe pagarse el 30% de la reserva: " + espacio.getPrecio()*0.3);
			}
			
			if(reserva.getDuracion() > 7 && fechaCancelacion.before(reserva.calcularFechaConDiasDespues(8)))
			{
				throw new Exception("Debe pagarse el 10% de la reserva: " + espacio.getPrecio()*0.1);
			}
			
			if(reserva.getDuracion() > 7 && fechaCancelacion.after(reserva.calcularFechaConDiasDespues(7)))
			{
				throw new Exception("Debe pagarse el 30% de la reserva: " + espacio.getPrecio()*0.3);
			}
			
			daoReserva.deleteReserva(reserva);

		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		catch (Exception e) 
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				daoReserva.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	//RF6
	
	public void cancelarEspacio(Espacio espacio, Date fechaCancelacion) throws Exception 
	{
		DAOReserva daoReserva = new DAOReserva();
		DAOOperador daoOperador = new DAOOperador();
		DAOEspacio daoEspacio = new DAOEspacio();
		
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoOperador.setConn(conn);
			daoEspacio.setConn(conn);
			
			Operador operador = null;
			List<Reserva> reservas = null;
			
			try
			{
				espacio = daoEspacio.buscarEspacio(espacio.getId());
			}
			catch(Exception e)
			{
				throw new Exception("No hay espacio con dicho id para poder cancelarlo.");
			}
			try
			{
				operador =daoOperador.buscarOperador(espacio.getOperador().getId());
				reservas = daoEspacio.buscarEspacio(espacio.getId()).getReservas();
			}
			catch(Exception e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw e;
			}					
			
			for(Reserva r : reservas)
			{
				if(r.calcularFechaFin().after(fechaCancelacion))
				{
					throw new Exception ("Hay reservas hechas en el espacio que culminan despu�s de la cancelaci�n propuesta. Aseg�rese que no se est� comprometido.");
				}
			}			
			
			espacio.setFechaRetiro(fechaCancelacion);
			daoEspacio.updateEspacio(espacio);
		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		catch (Exception e) 
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				daoReserva.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
}
