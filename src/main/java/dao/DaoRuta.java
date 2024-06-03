package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import model.Ruta;
import model.Usuario;

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
	
	public Ruta obtenerPorId(int idruta) throws SQLException {
		
		String sql = "SELECT * FROM rutas WHERE idruta=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idruta);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		Ruta r = new Ruta(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
		
		return r;
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
		
		ps.executeUpdate();
		
		ps.close();
		
	}
	
	public void editarRuta(Ruta r) throws SQLException {
		
		String sql = "UPDATE rutas SET titulo=?, estilo=?, descripcion=?, fecha=?, foto=? WHERE idruta=?";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, r.getTitulo());
		ps.setString(2, r.getEstilo());
		ps.setString(3, r.getDescripcion());
		ps.setString(4, r.getFecha());
		ps.setString(5, r.getFoto());
		ps.setInt(6, r.getIdruta());
		
		ps.executeUpdate();
		
		ps.close();
	}
	
	public void borrarRuta(int idruta) throws SQLException {
		
		String sql = "DELETE FROM rutas WHERE idruta=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idruta);
		
		int filas = ps.executeUpdate();
		
		ps.close();
	}
	
	/**
	 * Un método con prepareStatement, conectarse con el atributo 'con' que ya tenemos elaborado.
	 * Ejecutar y guardar en un objeto de tipo ResultSet, ResultSet es el que utiliza mysql para almacenar esa colección.
	 * Indicar que ejecute el 'ps', pero dado que se van a recibir datos, no enviar, el que se usará es executeQuery en vez de executeUpdate. 
	 * 
	 * Inicializar la colección. 
	 * Se usarán las condicionales anidadas para nicializar el ArrayList 'res' si no lo está (es null), en caso contrario no es necesario.
	 * 
	 * En cada fila del ArrayList se instancia un objeto de tipo Ruta.
	 * Se elige un constructor (de la clase Modelo) que contenga el id también.
	 * @return
	 * @throws SQLException 
	 */
	public ArrayList<Ruta> listarRutas() throws SQLException {
		
		String sql = "SELECT * FROM rutas";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet res = ps.executeQuery(); //lista
		
		ArrayList<Ruta> rutas = null;

		while(res.next()) {
			
			if(rutas == null) {
				rutas = new ArrayList<Ruta>();
			}
			rutas.add(new Ruta(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6)));
		}
		
		return rutas;
	}
	
	/**
	 * Vncular el proyecto mediante el Build Path el archivo gson (libreria gson), y a la carpeta de Apache.
	 * Este método servirá para convertir todos los datos que obtiene a un archivo json.
	 * 
	 * Crear un objeto gson gracias a la librería que se ha añadido e importamos al Build Path.
	 * 
	 * Mediante este método toJson, se obtiene un String con todos los datos de la base de datos en formato JSON.
	 * En dicho String json, incluir lo que devuelva el objeto gson, con el método toJson,
	 * al cual se le da el método listarRutas, el cual es el que tiene todos los datos.
	 * Retorna el archivo json. Ahora el modelo es capaz de conectarse y trabajar en JSON.
	 * @return
	 * @throws SQLException
	 */
	public String listarJson() throws SQLException {
		
		String json = "";
		Gson gson = new Gson();
		
		json = gson.toJson(this.listarRutas());
		
		return json;
	}

	
}


