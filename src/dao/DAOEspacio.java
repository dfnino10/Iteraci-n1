package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Espacio;
import vos.Habitacion;
import vos.Operador;
import vos.Reserva;
import vos.Servicio;

public class DAOEspacio {
	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOEspacio() {
		recursos = new ArrayList<Object>();
	}

	public void cerrarRecursos() {
		for (Object ob : recursos) {
			if (ob instanceof PreparedStatement) {
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}
	}

	public void setConn(Connection con) {
		this.conn = con;
	}

	public ArrayList<Espacio> darEspacios() throws SQLException, Exception {
		ArrayList<Espacio> espacios = new ArrayList<Espacio>();

		String sql = "SELECT * FROM ESPACIOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println(rs.next());

		while (rs.next()) {
			long id = Long.parseLong(rs.getString("ID"));
			long registro = Long.parseLong(rs.getString("REGISTRO"));
			int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
			double tamaño = Double.parseDouble(rs.getString("TAMAÑO"));
			String ubicacion = rs.getString("UBICACION");
			double precio = Double.parseDouble(rs.getString("PRECIO"));
			Date fechaRetiro = Date.valueOf(rs.getString("FECHARETIRO"));

			DAOHabitacion daoHabitacion = new DAOHabitacion();
			daoHabitacion.setConn(conn);

			List<Long> habitacionesId = daoHabitacion.buscarHabitacionesIdEspacio(id);

			List<Habitacion> habitaciones = new ArrayList<Habitacion>();

			for (Long hId : habitacionesId) {
				habitaciones.add(daoHabitacion.buscarHabitacion(hId));
			}

			DAOReserva daoReserva = new DAOReserva();
			daoReserva.setConn(conn);

			List<Reserva> reservas = daoReserva.buscarReservasIdEspacio(id);

			DAOOperador daoOperador = new DAOOperador();
			daoOperador.setConn(conn);

			Operador operador = daoOperador.buscarOperadorIdEspacio(id);

			DAOServicio daoServicio = new DAOServicio();
			daoServicio.setConn(conn);

			List<Servicio> servicios = daoServicio.buscarServiciosIdEspacio(id);

			espacios.add(new Espacio(id, registro, capacidad, tamaño, ubicacion, precio, fechaRetiro, operador,
					reservas, servicios, habitaciones));
		}
		return espacios;
	}

	public void addEspacio(Espacio espacio) throws SQLException, Exception {
		String sql = "INSERT INTO ESPACIOS VALUES (";
		sql += "id = " + espacio.getId() + ",";
		sql += "registro = " + espacio.getRegistro() + ",";
		sql += "ubicacion = " + espacio.getUbicacion() + ",";
		sql += "capacidad = " + espacio.getCapacidad() + ",";
		sql += "fechaRetiro = " + espacio.getFechaRetiro().toString() + ",";
		sql += "precio = " + espacio.getPrecio() + ",";
		sql += "tamaño = " + espacio.getTamaño() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void updateEspacio(Espacio espacio) throws SQLException, Exception {
		String sql = "UPDATE ESPACIOS SET ";
		sql += "registro = " + espacio.getRegistro() + ",";
		sql += "ubicacion = " + espacio.getUbicacion() + ",";
		sql += "capacidad = " + espacio.getCapacidad() + ",";
		sql += "fechaRetiro = " + espacio.getFechaRetiro().toString() + ",";
		sql += "precio = " + espacio.getPrecio() + ",";
		sql += "tamaño = " + espacio.getTamaño() + ")";
		sql += " WHERE ID = " + espacio.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void deleteEspacio(Espacio espacio) throws SQLException, Exception 
	{
		String sql = "DELETE FROM ESPACIOS";
		sql += " WHERE ID = " + espacio.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public Espacio buscarEspacio(long id) throws SQLException, Exception {
		String sql = "SELECT * FROM ESPACIOS WHERE ID  ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		long registro = Long.parseLong(rs.getString("REGISTRO"));
		int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
		double tamaño = Double.parseDouble(rs.getString("TAMAÑO"));
		String ubicacion = rs.getString("UBICACION");
		double precio = Double.parseDouble(rs.getString("PRECIO"));
		Date fechaRetiro = Date.valueOf(rs.getString("FECHARETIRO"));

		DAOHabitacion daoHabitacion = new DAOHabitacion();
		daoHabitacion.setConn(conn);

		List<Long> habitacionesId = daoHabitacion.buscarHabitacionesIdEspacio(id);

		List<Habitacion> habitaciones = new ArrayList<Habitacion>();

		for (Long hId : habitacionesId) {
			habitaciones.add(daoHabitacion.buscarHabitacion(hId));
		}

		DAOReserva daoReserva = new DAOReserva();
		daoReserva.setConn(conn);

		List<Reserva> reservas = daoReserva.buscarReservasIdEspacio(id);

		DAOOperador daoOperador = new DAOOperador();
		daoOperador.setConn(conn);

		Operador operador = daoOperador.buscarOperadorIdEspacio(id);

		DAOServicio daoServicio = new DAOServicio();
		daoServicio.setConn(conn);

		List<Servicio> servicios = daoServicio.buscarServiciosIdEspacio(id);

		return new Espacio(id, registro, capacidad, tamaño, ubicacion, precio, fechaRetiro, operador, reservas,
				servicios, habitaciones);
	}

	public ArrayList<Integer> buscarEspaciosIdOperador(long pId) throws SQLException, Exception {
		ArrayList<Integer> espacios = new ArrayList<Integer>();

		String sql = "SELECT * FROM OPERADORESYESPACIOS WHERE IDOPERADOR = " + pId;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("IDESPACIO"));
			espacios.add(id);
		}
		return espacios;
	}

	public int buscarEspacioIdReserva(long pId) throws SQLException, Exception {
		String sql = "SELECT * FROM RESERVASYESPACIOS WHERE IDRESERVA = " + pId;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();

		int id = Integer.parseInt(rs.getString("IDESPACIO"));
		int espacio = id;

		return espacio;
	}

	public int buscarEspacioIdHabitacion(long pId) throws SQLException, Exception {
		String sql = "SELECT * FROM ESPACIOSYHABITACIONES WHERE IDHABITACION = " + pId;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();

		int id = Integer.parseInt(rs.getString("IDESPACIO"));
		int espacio = id;

		return espacio;
	}

	// RFC2

	public List<Espacio> espaciosPopulares() throws Exception, SQLException {
		String sql = "SELECT ID FROM (SELECT RESERVAS.IDESPACIO AS ID, COUNT(RESERVAS.IDCLIENTE) AS CONTEO FROM RESERVAS GROUP BY RESERVAS.IDESPACIO ORDER BY CONTEO DESC) WHERE ROWNUM <= 20";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		System.out.println(rs.next());

		List<Espacio> espacios = new ArrayList<Espacio>();

		while (rs.next()) {
			long id = Long.parseLong(rs.getString("ID"));
			Espacio resultante = buscarEspacio(id);
			espacios.add(resultante);
		}

		return espacios;
	}
}
