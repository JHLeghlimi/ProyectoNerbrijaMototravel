package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import dao.DaoRuta;
import dao.DaoUsuario;

/**
 * Servlet implementation class GestionUsuarios
 * Instanciación sesion.
 */
public class GestionUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionUsuarios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Uso de patrón Singelton. Potección de toda la sesión. Gestión de opciones con
	 * if else.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		sesion = request.getSession();

		//int idSesion = Integer.parseInt((String)sesion.getAttribute("iduser"));
		//int idSesion = (int) sesion.getAttribute("iduser");

		//if (idSesion != 0) { // registrado si se cumple

			PrintWriter out = response.getWriter();

			int opcion = Integer.parseInt(request.getParameter("op")); // recuperar la opción

			if (opcion == 2) { // opción editar
				
				int iduser = Integer.parseInt(request.getParameter("iduser"));
				try {
					Usuario u = new Usuario();
					u.obtenerPorId(iduser);
					out.print(u.dameJson());
					System.out.println(u.dameJson());
					response.sendRedirect("listarUsuarios.html");
					// comprobaciones

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (opcion == 1) { // opción listar

				try {
					DaoUsuario u = new DaoUsuario();
					out.print(u.listarJson());
					response.sendRedirect("listarUsuarios.html");

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (opcion == 3) { // opción borrar

				int iduser = Integer.parseInt(request.getParameter("iduser"));
				try {
					DaoUsuario u = new DaoUsuario();
					u.borrarUsuario(iduser);
					out.print(u.listarJson());

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (opcion == 4) { // opción listar por tipo

				int tipoUsuario = Integer.parseInt(request.getParameter("tipoUsuario"));
				try {
					// DaoUsuario.getInstance().listarJson(tipoUsuario); //Singelton mal hecho
					// out.print(tipoUsuario); //No me lista al seleccionar. Intentar luego.
					DaoUsuario u = new DaoUsuario();
					out.print(u.listarJson(tipoUsuario));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		/*} else {
			System.out.println("Acceso denegado");
			response.sendRedirect("login.html");
		}*/

	}

	/**
	 * getParameter siempre devuelve String, para obtener un número hay que parsear.
	 * sendRedirect al final para que redirija a la web que se desee, para que el servlet no quede "muerto".
	 * En este caso el usuario seráredirigido a la lista de usuarios al insertar.
	 * 
	 * Respetando la arquitectura de software y las reglas de POO.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String nombre = request.getParameter("nombre");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		int permiso = Integer.parseInt(request.getParameter("permiso"));
		String iduser = request.getParameter("iduser");
		String pass = getMD5(request.getParameter("pass"));
				
		Usuario u = new Usuario(nombre, username, email, permiso, pass);
		System.out.println(u.toString());
		
		try {	
			if (iduser == "") {
				DaoUsuario.getInstance().insertarUsuario(u); //Singelton
				//DaoUsuario dau = new DaoUsuario();
				//dao.insertar(u);
				
			}else {
				int iduserInt = Integer.parseInt(iduser);
				u.setIduser(iduserInt);
				u.actualizarUsuario();
				//u.actualizarUsuario(Integer.parseInt(iduser)); //abreviatura
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		response.sendRedirect("listarUsuarios.html");	
		
	}

	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

	}

	
}
