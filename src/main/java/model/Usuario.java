package model;

import java.sql.SQLException;

import com.google.gson.Gson;

import dao.DaoUsuario;

/**
 * Propiedades o atributos 'private' para cumplir el principio de encapsulamiento de POO.
 * Se van creando los constructores que se requieran.
 * 
 * Generación de getters y setters.
 * Se accede a propiedades encapsuladas con getters y setters, siendo la propiedad privada. Para los métodos de igual manera.
 */
public class Usuario {
	
	private int iduser;
	private String nombre;
	private String username;
	private String email;
	private int permiso;
	private String pass;
	
	/**
	 * Constructor vacío.
	 */
	public Usuario() {
		
	}

	/**
	 * Constructor con 'id'.
	 * super(); es para herencia. En este caso no se hará uso de la herencia.
	 * @param id
	 * @param nombre
	 * @param username
	 * @param email
	 * @param permiso
	 */
	public Usuario(int iduser, String nombre, String username, String email, int permiso) {
		super();
		this.iduser = iduser;
		this.nombre = nombre;
		this.username = username;
		this.email = email;
		this.permiso = permiso;
	}

	/**
	 * Constructor sin 'id'.
	 * @param nombre
	 * @param username
	 * @param email
	 * @param permiso
	 */
	public Usuario(String nombre, String username, String email, int permiso) {
		super();
		this.nombre = nombre;
		this.username = username;
		this.email = email;
		this.permiso = permiso;
	}
	
	public Usuario(int iduser, String nombre, String username, String email, int permiso, String pass) {
		super();
		this.iduser = iduser;
		this.nombre = nombre;
		this.username = username;
		this.email = email;
		this.permiso = permiso;
		this.pass = pass;
	}
	
	public Usuario(String nombre, String username, String email, int permiso, String pass) {
		super();
		this.nombre = nombre;
		this.username = username;
		this.email = email;
		this.permiso = permiso;
		this.pass = pass;
	}

	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPermiso() {
		return permiso;
	}

	public void setPermiso(int permiso) {
		this.permiso = permiso;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * toString con todos los campos.
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + iduser + ", nombre=" + nombre + ", username=" + username + ", email=" + email + ", permiso="
				+ permiso + "]";
	}
	
	/**
	 * Se hace uso del patrón Singelton.
	 * @throws SQLException
	 */
	public void insertarUsuario() throws SQLException {
		
		DaoUsuario.getInstance().insertarUsuario(this);
		
		// DaoUsuario dao = new DaoUsuario ();
		// dao.insertar(this);
	}
	
	/**
	 * Se hace uso del patrón Singelton.
	 * @throws SQLException
	 */
	public void actualizarUsuario() throws SQLException {
		
		DaoUsuario.getInstance().actualizarUsuario(this);
		
	}
	
	public void borrarUsuario(int iduser) throws SQLException {
		
		DaoUsuario.getInstance().borrarUsuario(iduser);
		
	}
	
	/**
	 * Generación  de un objeto auxiliar ('aux'), llamada al dao y guardado de dato en 'aux'.
	 * @param iduser
	 * @throws SQLException
	 */
	public void obtenerPorId(int iduser) throws SQLException {
		
		//Usuario aux = DaoUsuario.getInstance().obtenerPorId(iduser); //Singelton
		DaoUsuario dao = new DaoUsuario ();
		Usuario aux = dao.obtenerPorId(iduser);
		
		this.setIduser(aux.getIduser());
		this.setNombre(aux.getNombre());
		this.setUsername(aux.getUsername());
		this.setEmail(aux.getEmail());
		this.setPermiso(aux.getPermiso());
		
	}
	
	/**
	 * Método para el inicio de sesión. Método que devolverá un boolean.
	 * Uso de patrón Singelton.
	 * Si este metodo devuelve un objeto aux con sus datos significa que se ha logeado correctamente.
	 * Si aux es diferente a null siginifica que aux contiene los datos de este usuario.
	 * El método logeando del DaoUsuario es el que consulta con la BD.
	 * @param pass
	 * @return
	 * @throws SQLException
	 */
	public boolean logeo(String pass) throws SQLException {
		
		boolean ok = false;
		
		//Usuario aux = DaoUsuario.getInstance().logeando(this, pass);
		DaoUsuario dao = new DaoUsuario();
		Usuario aux = dao.logeando(this, pass);
		
		if(aux != null) {
			ok = true;
			
			this.setIduser(aux.getIduser());
			this.setNombre(aux.getNombre());
			this.setUsername(aux.getUsername());
			this.setEmail(aux.getEmail());
			this.setPermiso(aux.getPermiso());
			this.setPass(aux.getPass());
			
		}
		return ok;
	}
	
	/**
	 * Método que devuelva un String y devuelva tambien json.
	 * @return
	 */
	public String dameJson() {
		
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this);
				
		return json;
	}
	

	
	
	
	
}











