package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.CategoriaServicio;
import vos.Servicio;

public class DAOCategoriaServicio 
{
	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOCategoriaServicio() {
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
	
	public ArrayList<CategoriaServicio> darCategoriasServicio() throws SQLException, Exception {
		ArrayList<CategoriaServicio> categoriasServicio = new ArrayList<CategoriaServicio>();

		String sql = "SELECT * FROM CATEGORIAS_SERVICIO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println(rs.next());

		while (rs.next()) {
			long id = Long.parseLong(rs.getString("ID"));
			String categoria = rs.getString("CATEGORIA");
			String descripcion = rs.getString("DESCRIPCIÓN");

			categoriasServicio.add(new CategoriaServicio(id, categoria, descripcion));
		}
		return categoriasServicio;
	}

	public void addCategoriaServicio(CategoriaServicio categoriaServicio) throws SQLException, Exception {
		String sql = "INSERT INTO CATEGORIAS_SERVICIO VALUES (";
		sql += "ID = " + categoriaServicio.getId() + ",";
		sql += "CATEGORIA= " + categoriaServicio.getCategoria() + ",";
		sql += "DESCRIPCION = " + categoriaServicio.getDescripcion() + ")";		

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void updateCategoriaServicio(CategoriaServicio categoriaServicio) throws SQLException, Exception {
		String sql = "UPDATE CATEGORIAS_SERVICIO SET ";
		sql += "ID = " + categoriaServicio.getId() + ",";
		sql += "CATEGORIA= " + categoriaServicio.getCategoria() + ",";
		sql += "DESCRIPCION = " + categoriaServicio.getDescripcion() + ")";		

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void deleteCategoriaServicio(CategoriaServicio categoriaServicio) throws SQLException, Exception {
		String sql = "DELETE FROM CATEGORIAS_SERVICIO";
		sql += " WHERE ID = " + categoriaServicio.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public CategoriaServicio buscarCategoriaServicio(long id) throws SQLException, Exception {
		String sql = "SELECT * FROM CATEGORIAS_SERVICIO WHERE ID  ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		String categoria = rs.getString("CATEGORIA");
		String descripcion = rs.getString("DESCRIPCIÓN");

		return new CategoriaServicio(id, categoria, descripcion);
	}
	
	public CategoriaServicio buscarCategoriaServicioIdServicio(long id) throws SQLException, Exception{
		DAOServicio daoServicio = new DAOServicio();
		
		daoServicio.setConn(conn);
		
		Servicio servicio = daoServicio.buscarServicio(id);
		
		return servicio.getCategoria();
	}
}
