package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Cliente;
import vos.Espacio;
import vos.Cliente.Vinculo;

public class DAOCliente 
{
	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOCliente()
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
	
	public ArrayList<Cliente> darClientes() throws SQLException, Exception 
	{
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		
		String sql = "SELECT * FROM CLIENTES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println(rs.next());

		while (rs.next()) 
		{
			long id = Long.parseLong(rs.getString("ID"));
			long identificacion = Long.parseLong(rs.getString("IDENTIFICACION"));
			String nombre = rs.getString("NOMBRE");
			int edad = Integer.parseInt(rs.getString("EDAD"));
			String direccion = rs.getString("DIRECCION");
			Vinculo vinculo = Vinculo.valueOf(rs.getString("VINCULO"));							
			DAOReserva daoReserva = new DAOReserva();
			daoReserva.setConn(conn);
			
			
			clientes.add(new Cliente(id, identificacion, nombre, edad, direccion, vinculo));
		}
		return clientes;
	}	
	
	public void addCliente(Cliente cliente) throws SQLException, Exception 
	{				
		String sql = "INSERT INTO CLIENTES VALUES (";
		sql += "id = "+ cliente.getId() + ",";
		sql += "identificacion = " + cliente.getIdentificacion() + ",";
		sql += "nombre = " + cliente.getNombre()+",";
		sql += "direccion = " + cliente.getDireccion()+",";
		sql += "edad = " + cliente.getEdad() + ",";
		sql += "vinculo = " +cliente.getVinculo().toString()+")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void updateCliente(Cliente cliente) throws SQLException, Exception
	{
		String sql = "UPDATE CLIENTES SET ";
		sql += "identificacion = " + cliente.getIdentificacion() + ",";
		sql += "nombre = " + cliente.getNombre()+",";
		sql += "direccion = " + cliente.getDireccion()+",";
		sql += "edad = " + cliente.getEdad() + ",";
		sql += "vinculo = " +cliente.getVinculo().toString()+",";
		sql += " WHERE ID = " + cliente.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteCliente(Cliente cliente) throws SQLException, Exception
	{
		String sql = "DELETE FROM CLIENTES";
		sql += " WHERE ID = " + cliente.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}	
	
	public Cliente buscarCliente(long id) throws SQLException, Exception 
	{		
		String sql = "SELECT * FROM CLIENTES WHERE CATEGORIA_ID  ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();		
		
		long identificacion = Long.parseLong(rs.getString("IDENTIFICACION"));
		String nombre = rs.getString("NOMBRE");
		int edad = Integer.parseInt(rs.getString("EDAD"));
		String direccion = rs.getString("DIRECCION");
		Vinculo vinculo = Vinculo.valueOf(rs.getString("VINCULO"));

		return new Cliente(id, identificacion,nombre, edad, direccion, vinculo);
	}
	
	public Cliente buscarClienteIdReserva(long id) throws SQLException, Exception 
	{
		String sql = "SELECT * FROM RESERVAS WHERE CATEGORIA_ID  ='" + id + "'";
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();	
		
		Long idCliente = Long.parseLong(rs.getString("IDCLIENTE"));
		return buscarCliente(idCliente);
	}
}
