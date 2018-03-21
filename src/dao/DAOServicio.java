package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Servicio;
import vos.Servicio.CategoriaServicio;

public class DAOServicio {
	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOServicio()
	{
		recursos = new ArrayList<Object>();
	}
	
	public void cerrarRecursos()
	{
		for(Object ob : recursos)
		{
			if(ob instanceof PreparedStatement)
			{
				try 
				{
					((PreparedStatement) ob).close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				}
			}
				
		}
	}
	
	public void setConn(Connection con)
	{
		this.conn = con;
	}	
	
	public ArrayList<Servicio> darServicios() throws SQLException, Exception 
	{
		ArrayList<Servicio> servicios = new ArrayList<Servicio>();
		
		String sql = "SELECT * FROM ServicioS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println(rs.next());

		while (rs.next()) 
		{
			long id = Long.parseLong(rs.getString("ID"));
			CategoriaServicio categoria = CategoriaServicio.valueOf(rs.getString("CATEGORÍA"));
			String descripcion = rs.getString("DESCRIPCIÓN");
			double precioAdicional= Double.parseDouble(rs.getString("PRECIOADICIONAL"));
			int inicioHorario = Integer.parseInt(rs.getString("INICIOHORARIO"));
			int finHorario = Integer.parseInt(rs.getString("FINHORARIO"));
			
			
			servicios.add(new Servicio(id,categoria,descripcion,precioAdicional,inicioHorario,finHorario));
		}
		return servicios;
	}	
	
	public void addServicio(Servicio servicio) throws SQLException, Exception 
	{				
		String sql = "INSERT INTO SERVICIOS VALUES (";
		sql += "ID = "+ servicio.getId() + ",";
		sql += "CATEGORÍA= "+ servicio.getCategoria() + ",";
		sql += "DESCRIPCIÓN = " + servicio.getDescripcion() + ",";
		sql += "PRECIOADICIONAL = " + servicio.getPrecioAdicional() + ",";
		sql += "INICIOHORARIO= " + servicio.getInicioHorario() +",";
		sql += "FINHORARIO = " + servicio.getFinHorario() +",";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void updateServicio(Servicio servicio) throws SQLException, Exception
	{
		String sql = "UPDATE SERVICIOS SET ";
		sql += "CATEGORÍA= "+ servicio.getCategoria() + ",";
		sql += "DESCRIPCIÓN = " + servicio.getDescripcion() + ",";
		sql += "PRECIOADICIONAL = " + servicio.getPrecioAdicional() + ",";
		sql += "INICIOHORARIO= " + servicio.getInicioHorario() +",";
		sql += "FINHORARIO = " + servicio.getFinHorario() +",";
		sql += " WHERE ID = " + servicio.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteServicio(Servicio servicio) throws SQLException, Exception
	{
		String sql = "DELETE FROM SERVICIOS";
		sql += " WHERE ID = " + servicio.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}	
	
	public Servicio buscarServicio(long id) throws SQLException, Exception 
	{		
		String sql = "SELECT * FROM SERVCIOS WHERE ID  ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();		
		
		CategoriaServicio categoria = CategoriaServicio.valueOf(rs.getString("CATEGORÍA"));
		String descripcion = rs.getString("DESCRIPCIÓN");
		double precioAdicional= Double.parseDouble(rs.getString("PRECIOADICIONAL"));
		int inicioHorario = Integer.parseInt(rs.getString("INICIOHORARIO"));
		int finHorario = Integer.parseInt(rs.getString("FINHORARIO"));
		
		return new Servicio(id,categoria,descripcion,precioAdicional,inicioHorario,finHorario);
	}
}
