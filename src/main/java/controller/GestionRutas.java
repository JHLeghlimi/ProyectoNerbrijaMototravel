package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Ruta;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import dao.DaoRuta;

/**
 * Servlet implementation class GestionRutas
 */
@MultipartConfig
public class GestionRutas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Decidir la ruta de almacenamiento de las fotos subidas.
	private String pathFiles = "C:\\Users\\Usuario\\eclipse-workspace\\ProyectoMototravel\\src\\main\\webapp\\uploadedPhotos";
	private File uploads = new File(pathFiles);

    /**
     * Default constructor. 
     */
    public GestionRutas() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * Recordar que request es la entrada y response la salida.
	 * Cuando se reciba una llamada por Get, llame a la BD genere el json y lo devuelva.
	 * Generar un objeto String con respuesta json que incluya lo que incluye el método DaoRuta, al cual llamaremos listarJson.
	 * 
	 * Crear un objeto Print Writer que va a servir para enviar datos.
	 * Se usará Singelton siempre que se pueda.
	 * De esta manera el cliente recibe los datos por web, los cuales recibirá JavaScript y porcesará.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		try {
			String respuestaJSON;
			respuestaJSON = DaoRuta.getInstance().listarJson();
			System.out.println(respuestaJSON);
			
			PrintWriter out = response.getWriter();
			out.print(respuestaJSON);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Singelton	
		
	}

	/**
	 * En request especificamos el 'name' puesto en HTML.
	 * Para subir una foto el buffer (camino o ruta) es el siguiente: ruta - datos - nombreArchivo
	 * En una foto se obtienen los datos del archivo del formulario, no la foto en sí.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String titulo = request.getParameter("titulo");
		String estilo = request.getParameter("estilo");
		String descripcion = request.getParameter("descripcion");
		String fecha = request.getParameter("fecha");
		
		//Subir una foto:
		Part part = request.getPart("foto"); //datos binarios de la foto
		
		Path path = Paths.get(part.getSubmittedFileName()); //obtener la ruta/nombre del archivo
		String fileName = path.getFileName().toString();
		
		InputStream input = part.getInputStream(); //buffer o camino
		
		File file = new File (uploads, fileName); //contenedor
		
		//Gestión de errores
		try {
			
			Files.copy(input, file.toPath()); //copiado de los datos del archivo
			
		} catch (Exception e) {
			// TODO: handle exception
			
			System.out.println("Error al copiar archivo");
			PrintWriter respuesta = response.getWriter();
			respuesta.print("<h4> Se ha producido un error, contacte con el administrador</h4>");
		}
		
		//Objeto Ruta con el constructor deseado
		Ruta r1 = new Ruta(titulo, estilo, descripcion, fecha, fileName);
		System.out.println(r1.toString());
		
		try {
			r1.publicarRuta();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Error en base de datos");
		}	
		response.sendRedirect("listarRutas.html");
	}

}
