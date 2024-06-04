package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import dao.DaoUsuario;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion; //inicializar sesion  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String username = request.getParameter("username");
		//String pass = request.getParameter("pass");
		String pass = getMD5(request.getParameter("pass")); //cifrado
		
		if (username == null || username.isEmpty() || pass == null || pass.isEmpty()) {
            response.sendRedirect("login.html");
            return;
        }
		
		Usuario u = new Usuario();
		u.setUsername(username);
		
		//protección
		
		try {
			
			if(u.logeo(pass)) { // si el usuario está, lo guarda en sesión.
				
				sesion = request.getSession(); //inicializar sesion
				sesion.setAttribute("iduser", u.getIduser());
				sesion.setAttribute("permiso", u.getPermiso()); //identificación
				
				response.sendRedirect("index.html");
				System.out.println("Acceso permitido");
				
			}else { // no accede aquí, solucionar
				response.sendRedirect("login.html");
				System.out.println("Acceso denegado");
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Función para el cifrado MD5.
	 * @param input
	 * @return
	 */
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
