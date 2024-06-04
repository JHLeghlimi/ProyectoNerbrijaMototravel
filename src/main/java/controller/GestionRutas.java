package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Ruta;
import model.Usuario;

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
	HttpSession sesion;
	
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
     * Métodos comprobacionLogin y comprobacionPermiso: Estos métodos verifican si el usuario está logueado y si tiene los permisos necesarios para realizar la acción solicitada.
     * comprobacionLogin: Verifica si la sesión es válida y si hay un usuario registrado en la sesión.
     * @param request
     * @return
     */
    private boolean comprobacionLogin(HttpServletRequest request) {
        sesion = request.getSession(false);
        return sesion != null && sesion.getAttribute("iduser") != null;
    }

    /**
     * comprobacionPermiso: Verifica si el usuario tiene los permisos requeridos para realizar la acción.
     * @param request
     * @param permiso
     * @return
     */
    private boolean comprobacionPermiso(HttpServletRequest request, int permiso) {
        Usuario u = (Usuario) sesion.getAttribute("permiso");
        return u != null && u.getPermiso() >= permiso;
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
	 * Verificación de sesión y permisos en doGet y doPost antes de ejecutar cualquier acción, 
	 * se verifica si el usuario está logueado y si tiene los permisos necesarios.
	 * Si el usuario no está logueado, se redirige a la página de login.
	 * Si el usuario no tiene los permisos necesarios, se redirige a una página que queramos.
	 * 
	 * El bloque if !comprobacionPermiso o !comprobacionLogin significa "si no se cumple la condición de que el usuario tiene los permisos necesarios"
	 * o no está logeado. Es decir, si se cumple que no está logeado,entra en el fi y lo expulsa.
	 * 
	 * Se pone inverso (exclamación) para evitar meter todo el código dentro del if siendo menos claro. Por limpieza de código.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		if (!comprobacionLogin(request)) {
			response.sendRedirect("login.html");
			return;
		}

		PrintWriter out = response.getWriter();
		int opcion = Integer.parseInt(request.getParameter("op"));

		switch (opcion) {

		case 1: // listar rutas
			if (comprobacionPermiso(request, 1)) { // acceso a permiso 1 o mayor
				try {
					String respuestaJSON;
					respuestaJSON = DaoRuta.getInstance().listarJson();
					out.print(respuestaJSON);
					System.out.println("Opcion 1: listar funciona!");

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Singelton
			} else {
				response.sendRedirect("index.html");
			}
			break;

		case 2: // editar ruta

			if (comprobacionPermiso(request, 9)) { // acceso a administrador
				int idruta = Integer.parseInt(request.getParameter("idruta"));
				try {
					Ruta ruta = DaoRuta.getInstance().obtenerPorId(idruta);
					String rutaJson = ruta.dameJson();
					out.print(rutaJson);
					System.out.println("Opción 2: editar funciona!");

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				response.sendRedirect("listarRutas.html");
			}
			break;

		case 3: // borrar ruta

			if (comprobacionPermiso(request, 9)) { // acceso a administrador
				int idruta1 = Integer.parseInt(request.getParameter("idruta"));
				try {
					DaoRuta r = new DaoRuta();
					r.borrarRuta(idruta1);
					out.print(r.listarJson());
					System.out.println("Opcion 3: borrar funciona!");

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				response.sendRedirect("listarRutas.html");

			}
			break;

		default:
			System.out.println("Algo ha fallado");
			break;
		}

	}

	/**
	 * En request especificamos el 'name' puesto en HTML.
	 * Para subir una foto el buffer (camino o ruta) es el siguiente: ruta - datos - nombreArchivo
	 * En una foto se obtienen los datos del archivo del formulario, no la foto en sí.
	 * 
	 * ***Explicación del control de sesiones y permisos en el doGet.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if (!comprobacionLogin(request)) {
			response.sendRedirect("login.html");
			return;
		}

		if (!comprobacionPermiso(request, 9)) { // solo los administradores de momento pueden publicar rutas
			response.sendRedirect("index.html");
			return;
		}
		
		String titulo = request.getParameter("titulo");
		String estilo = request.getParameter("estilo");
		String descripcion = request.getParameter("descripcion");
		String fecha = request.getParameter("fecha");
		int idruta = Integer.parseInt(request.getParameter("idruta"));
		
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
			System.out.println("llega");
			if (idruta == 0 ) {
				DaoRuta.getInstance().publicarRuta(r1);
				System.out.println("publica");
			}else {
				r1.setIdruta(idruta);
				r1.editarRuta();
				System.out.println("edita");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Error en base de datos");
		}	
		response.sendRedirect("listarRutas.html");
	
	}

}
