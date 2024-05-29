package model;

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

	
	public int getId() {
		return iduser;
	}

	public void setId(int iduser) {
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

	/**
	 * toString con todos los campos.
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + iduser + ", nombre=" + nombre + ", username=" + username + ", email=" + email + ", permiso="
				+ permiso + "]";
	}
	
	

}
