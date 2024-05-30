package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import model.Usuario;

/**
 * Uso de Singelton para la conexión.
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
	
	public String listarJson() throws SQLException {
		
		String json = "";
		Gson gson = new Gson();
		
		json = gson.toJson(this.listarUsuarios());
		
		return json;
		
	}
		

}











