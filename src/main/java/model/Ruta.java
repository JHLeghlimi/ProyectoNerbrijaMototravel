package model;

import java.sql.SQLException;

import dao.DaoRuta;

/**
 * Propiedades o atributos 'private' para cumplir el principio de encapsulamiento de POO.
 * Se van creando los constructores que se requieran.
 * 
 * Generación de getters y setters.
 * Se accede a propiedades encapsuladas con getters y setters, siendo la propiedad privada. Para los métodos de igual manera.
 */
public class Ruta {
	
	private int idruta;
	private String titulo;
	private String estilo;
	private String descripcion;
	private String fecha;
	private String foto;
	
	/**
	 * Constructor vacío Ruta
	 */
	public Ruta() {
		
	}

	/**
	 * Constructor con todos los campos.
	 * @param idruta
	 * @param titulo
	 * @param estilo
	 * @param descripcion
	 * @param fecha
	 * @param foto
	 */
	public Ruta(int idruta, String titulo, String estilo, String descripcion, String fecha, String foto) {
		super();
		this.idruta = idruta;
		this.titulo = titulo;
		this.estilo = estilo;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.foto = foto;
	}

	/**
	 * Constructor sin 'id'.
	 * @param titulo
	 * @param estilo
	 * @param descripcion
	 * @param fecha
	 * @param foto
	 */
	public Ruta(String titulo, String estilo, String descripcion, String fecha, String foto) {
		super();
		this.titulo = titulo;
		this.estilo = estilo;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.foto = foto;
	}

	/**
	 * Constructor sin 'id' y sin 'foto'.
	 * @param titulo
	 * @param estilo
	 * @param descripcion
	 * @param fecha
	 */
	public Ruta(String titulo, String estilo, String descripcion, String fecha) {
		super();
		this.titulo = titulo;
		this.estilo = estilo;
		this.descripcion = descripcion;
		this.fecha = fecha;
	}

	public int getIdruta() {
		return idruta;
	}

	public void setIdruta(int idruta) {
		this.idruta = idruta;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	/**
	 * toString con todos los campos.
	 */
	@Override
	public String toString() {
		return "Ruta [idruta=" + idruta + ", titulo=" + titulo + ", estilo=" + estilo + ", descripcion=" + descripcion
				+ ", fecha=" + fecha + ", foto=" + foto + "]";
	}

	/**
	 * Se usa el Patrón Singelton.
	 * De esta manera es sencillo, de esta manera si se usa otro método bastaría con hacer la misma operación.
	 * 
	 * La llamada a la inserción (publicarRuta) debe estar dentro del objeto Ruta.
	 * El objeto Ruta es el que debe dar la orden de su inserción al objeto DaoRuta para insertarse en la BD.
	 * 
	 * El flujo de la operación es el siguiente: el usuario rellena el formulario de inserción, java lo recibe en el servlet,
	 * llama al modo publicarRuta, publicarRuta llama a su Dao, y dicho Dao lo guarda en la base de datos.
	 * @throws SQLException
	 */
	public void publicarRuta() throws SQLException {
		
		DaoRuta.getInstance().publicarRuta(this);
		
		/* Método sin Singelton
		DaoRuta dao = new DaoRuta();
		dao.publicarRuta(this); // publicarRuta es de DaoRuta.
		*/
		
	}

}
