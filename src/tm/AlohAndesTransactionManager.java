package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import dao.DAOCliente;
import vos.Cliente;
import vos.ListaClientes;

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
	
	
}
