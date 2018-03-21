package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vos.Espacio;
import vos.Operador;
import vos.Operador.CategoriaOperador;
import vos.RFC1;

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
			long registro = Integer.parseInt(rs.getString("REGISTRO"));	
			CategoriaOperador categoría= CategoriaOperador.valueOf(rs.getString("CATEGORÍA"));				
			DAOEspacio daoEspacio = new DAOEspacio();
			daoEspacio.setConn(conn);
			
			List<Integer> lista = daoEspacio.buscarEspaciosIdOperador(id);
			
			ArrayList<Espacio> listaEspacio= new ArrayList<>();
			
			for (Integer integer : lista) {
				listaEspacio.add(daoEspacio.buscarEspacio(integer));
			}
			
			operadores.add(new Operador(id, registro, nombre, categoría, listaEspacio, documento));
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
		DAOEspacio daoEspacio = new DAOEspacio();
		daoEspacio.setConn(conn);
		
		List<Integer> lista = daoEspacio.buscarEspaciosIdOperador(id);
		
		ArrayList<Espacio> listaEspacio= new ArrayList<>();
		
		for (Integer integer : lista) {
			listaEspacio.add(daoEspacio.buscarEspacio(integer));
		}

		return new Operador(id, registro, nombre, categoria, listaEspacio, documento);
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
	
	//RFC1
	
	public List<RFC1> obtenerIngresosOperadores() throws SQLException, Exception
	{
		Date ahora = new Date();
		Date inicioAñoAnterior = new Date(ahora.getYear()-1,1,1);
		String sql = "SELECT ESPACIOS.IDOPERADOR AS ID, SUM(RESERVAS.PRECIO) AS INGRESOS FROM RESERVAS, ESPACIOS WHERE RESERVAS.IDESPACIO = ESPACIOS.ID AND RESERVAS.FECHAINICIO =" + inicioAñoAnterior + " GROUP BY ESPACIOS.IDOPERADOR";
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();	
		
		System.out.println(rs.next());
		
		List<RFC1> ingresos = new ArrayList<RFC1>();
		
		while(rs.next())
		{
			RFC1 nuevo = new RFC1(Long.parseLong(rs.getString("ID")),Double.parseDouble(rs.getString("INGRESOS")));
			ingresos.add(nuevo);
		}
		
		return ingresos;
	}	
}
