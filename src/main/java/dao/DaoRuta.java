package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Ruta;

/**
 * Cuando se instance este objeto DaoRuta, el constructor de esta clase se conectará a BD.
 * Se hará uso del Patrón Singelton.
 */
public class DaoRuta {
	
	/**
	 * Connection es el que realiza la operación, que ya está preparada en DBConnection.
	 * Nota: private oculta, pero static visualiza.
	 */
	public static Connection con = null;
	
	private static DaoRuta instance = null; // Patrón Singelton
	
	/**
	 * Cuando se cree esta clase DaoRuta, se conectará nada más empezar.
	 * @throws SQLException
	 */
	public DaoRuta() throws SQLException {
		
		this.con = DBConnection.getConexion();
		
	}
	
	/**
	 * Este método es el que se utilizará para aplicar el Patrón Singelton.
	 * Crear un método estático (llamada igual que la clase) para ser llamado,
	 * y devolverá un objeto de la misma clase en la que nos estamos.
	 * Un objeto DaoRuta que podrá ser llamado desde cualquier lugar.
	 * Condicional IF para comprobar que no sea nulo.
	 * @return
	 * @throws SQLException
	 */
	public static DaoRuta getInstance() throws SQLException {
		
		if(instance == null) {
			instance = new DaoRuta();
		}
		return instance;
		
	}
	
	/**
	 * Método insertar ruta (publicarRuta) en la BD, con la secuencia sql formulada.
	 * No hace falta conectarse, ya hay una conexión activa 'con', con la query que deseamos utilizar.
	 * Cargar los datos con el param r y ps.SetString.
	 * Así estaría el prepareStatement listo para lanzarlo.
	 * 
	 * executeUpdate porque se envia, si es recibir sería executeQuery.
	 * No hace falta cerrar la conexión porque es un objeto, cuando deje de funcionar
	 * el recolector de basura de Java lo destruirá. Solo basta con cerrar el ps.
	 * @param r
	 * @throws SQLException
	 */
	public void publicarRuta(Ruta r) throws SQLException {
		
		String sql = "INSERT INTO rutas (titulo,estilo,descripcion,fecha,foto) VALUES (?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, r.getTitulo());
		ps.setString(2, r.getEstilo());
		ps.setString(3, r.getDescripcion());
		ps.setString(4, r.getFecha());
		ps.setString(5, r.getFoto());
		
		int filas = ps.executeUpdate();
		
		ps.close();
		
	}

}
