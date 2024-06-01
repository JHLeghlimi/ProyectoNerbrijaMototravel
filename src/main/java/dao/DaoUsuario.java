package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import model.Usuario;

/**
 * Inicializar la conexión en todas las clases Dao.
 * Uso de Singelton.
 */
public class DaoUsuario {
	
	public static Connection con = null;
	
	private static DaoUsuario instance = null; 
	
	public DaoUsuario() throws SQLException {
		
		this.con = DBConnection.getConexion();
	}
	
	/**
	 * Mismo proceso que en Ruta (explicaciones del proceso detalladas en dicha clase).
	 * @return
	 * @throws SQLException 
	 */
	public static DaoUsuario getInstance() throws SQLException {
		
		if(instance == null) {
			instance = new DaoUsuario();
		}
		
		return instance;
	}
	
	/**
	 * Método que pregunta por el nombre de usuario y la contraseña.
	 * Dicho método, mandándole un usuario y un password devuelve ese usuario.
	 * @param u
	 * @param pass
	 * @return
	 * @throws SQLException
	 */
	public Usuario logeando(Usuario u, String pass) throws SQLException {
		
		String sql = "SELECT * FROM usuarios WHERE username=? AND pass=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, u.getUsername()); 
		ps.setString(2, pass);
				
		ResultSet rs = ps.executeQuery();
				
		rs.next();
				
		Usuario aux = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
				
		return aux;
	}
	
	/**
	 * Método insertar con PreparedStatement y las pertinentes querys sql.
	 * @param u
	 * @throws SQLException
	 */
	public void insertarUsuario(Usuario u) throws SQLException {
		
		String sql = "INSERT INTO usuarios (nombre,username,email,permiso) VALUES (?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, u.getNombre());
		ps.setString(2, u.getUsername());
		ps.setString(3, u.getEmail());
		ps.setInt(4, u.getPermiso());
				
		int filas = ps.executeUpdate();
		
		ps.close();
	}
	
	/**
	 * Método en el Dao que devolverá un solo registro por mediación de un id.
	 * @param iduser
	 * @return
	 * @throws SQLException
	 */
	public Usuario obtenerPorId(int iduser) throws SQLException {
		
		String sql = "SELECT * FROM usuarios WHERE iduser=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, iduser);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		Usuario u = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
		
		return u;
	}
	
	/**
	 * Importante tener copia de la BD, por seguridad, ejemplo de ello es ejecutar query sin el filtro WHERE podría eliminar datos.
	 * @param u
	 * @throws SQLException 
	 */
	public void actualizarUsuario(Usuario u) throws SQLException {
		
		String sql = "UPDATE usuarios SET nombre=?, username=?, email=?, permiso=? WHERE iduser=?";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, u.getNombre());
		ps.setString(2, u.getUsername());
		ps.setString(3, u.getEmail());
		ps.setInt(4, u.getPermiso());
		ps.setInt(5, u.getIduser());
				
		int filas = ps.executeUpdate();
				
		ps.close();
		
	}
	
	public void borrarUsuario(int iduser) throws SQLException {
		
		String sql = "DELETE FROM usuarios WHERE iduser=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, iduser);
		
		int filas = ps.executeUpdate();
		
		ps.close();
	}
	
	/**
	 * Cabe recordar que el ResultSet tiene un puntero y que en la distribución null[][][][]null,
	 * apunta a null, de ahí el next, para saltar el null. Recorre el array hasta terminar en null de nuevo.
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Usuario> listarUsuarios() throws SQLException {
		
		String sql = "SELECT * FROM usuarios"; 
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		ArrayList<Usuario> ls = null;
		
		while(rs.next()) {
			if(ls == null) {
				ls = new ArrayList<Usuario>();
			}
			ls.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
		}
		
		return ls;
	}
	
	/**
	 * Método listar que retorna los usuarios con el filtrado por tipo de usuario.
	 * @param tipoUsuario
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Usuario> listarUsuarios(int tipoUsuario) throws SQLException {
		
		String sql = "SELECT * FROM usuarios WHERE permiso=?"; 
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, tipoUsuario);
		ResultSet rs = ps.executeQuery();
		
		ArrayList<Usuario> ls = null;
		
		while(rs.next()) {
			if(ls == null) {
				ls = new ArrayList<Usuario>();
			}
			ls.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
		}
		
		return ls;
	}
	
	/**
	 * Método para convertir los datos en un archivo formato JSON.
	 * @return
	 * @throws SQLException
	 */
	public String listarJson() throws SQLException {
		
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.listarUsuarios());
		
		return json;
		
	}
	
	/**
	 * Sobrecarga de la función.
	 * Json con el parámetro por tipo de usuario.
	 * @param tipoUsuario
	 * @return
	 * @throws SQLException
	 */
	public String listarJson(int tipoUsuario) throws SQLException {
		
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.listarUsuarios(tipoUsuario));
		
		return json;
		
	}
		

}











