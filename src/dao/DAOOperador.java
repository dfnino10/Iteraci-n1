package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Operador;
import vos.Cliente.Vinculo;
import vos.Operador.CategoriaOperador;

public class DAOOperador {
	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOOperador()
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
	
	public ArrayList<Operador> darOperadores() throws SQLException, Exception 
	{
		ArrayList<Operador> operadores = new ArrayList<Operador>();
		
		String sql = "SELECT * FROM OPERADORES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println(rs.next());

		while (rs.next()) 
		{
			long id = Long.parseLong(rs.getString("ID"));
			long documento = Long.parseLong(rs.getString("DOCUMENTO"));
			String nombre = rs.getString("NOMBRE");
			int registro = Integer.parseInt(rs.getString("REGISTRO"));	
			CategoriaOperador categoría= CategoriaOperador.valueOf(rs.getString("CATEGORÍA"));				
			DAOEspacio daoEspacio = new DAOEspacio();
			daoEspacio.setConn(conn);
			
			
			operadores.add(new Operador(id, registro, nombre, categoría, documento));
		}
		return operadores;
	}	
	
	public void addOperador(Operador operador) throws SQLException, Exception 
	{				
		String sql = "INSERT INTO OPERADORES VALUES (";
		sql += "id = "+ operador.getId() + ",";
		sql += "documento = " + operador.getDocumento() + ",";
		sql += "nombre = " + operador.getNombre()+",";
		sql += "registro = " + operador.getRegistro()+",";
		sql += "categoría = " +operador.getCategoria().toString()+")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void updateOperador(Operador operador) throws SQLException, Exception
	{
		String sql = "UPDATE OPERADORES SET ";
		sql += "documento = " + operador.getDocumento() + ",";
		sql += "nombre = " + operador.getNombre()+",";
		sql += "registro = " + operador.getRegistro() + ",";
		sql += "categoría = " +operador.getCategoria().toString()+",";
		sql += " WHERE ID = " + operador.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteOperador(Operador operador) throws SQLException, Exception
	{
		String sql = "DELETE FROM OPERADOR";
		sql += " WHERE ID = " + operador.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}	
	
	public Operador buscarOperador(long id) throws SQLException, Exception 
	{		
		String sql = "SELECT * FROM OPERADORES WHERE ID  ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();		
		
		long documento = Long.parseLong(rs.getString("DOCUMENTO"));
		String nombre = rs.getString("NOMBRE");
		long registro = Long.parseLong(rs.getString("REGISTRO"));
		CategoriaOperador categoria= CategoriaOperador.valueOf(rs.getString("CATEGORÍA"));

		return new Operador(id, documento,nombre, registro, categoria);
	}
	
	public Operador buscarOperadorIdEspacio(long id) throws SQLException, Exception 
	{
		String sql = "SELECT * FROM ESPACIOS WHERE ID  ='" + id + "'";
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();	
		
		Long idOperador= Long.parseLong(rs.getString("IDOPERADOR"));
		return buscarOperador(idOperador);
	}
}
