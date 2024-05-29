package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Una clase para conectarse y desconectarse cuando se necesite.
 * Un objeto DBConnetion que será llamado y se conectará cuando sea necesario.
 * 
 * Será 'static': un atributo estático se extiende fuera del objeto, es común para todos los objetos instanciados.
 * Será 'final': es constante, no se podrá modificar.
 * 
 * Desde cualquier parte del programa instancie este objeto,
 * devolverá un objeto Connection, es decir, una conexión a la BD (a la URL especificada).
 */
public class DBConnection {
	
	public static final String JDBC_URL = "jdbc:mysql://localhost:3306/mototravel";
	public static Connection instance = null; // objeto Connection
	
	/**
	 * Para evitar una conexión si ya está conectado, ya que daría error, se hace uso del condicional 'if'.
	 * Si no está conectado, simplemente devuelve la instancia.
	 * 
	 * La gestión de errores se realiza con el try catch. En caso contrario hacer uso de la excepción throws.
	 * En este caso omitimos la gestión de errores.
	 * 
	 * Uso de patrón Singelton.
	 * Se le pasa (URL, user , password) de la BD.
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConexion() throws SQLException {
		
		if (instance == null) {
			
			instance = DriverManager.getConnection(JDBC_URL, "root" , "1234");
			
		/* 	
		try {
			instance = DriverManager.getConnection(JDBC_URL, "root", "1234");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// Aquí gestionar los errores.
		}
		*/
			
		}
		return instance;
		
	}

}
