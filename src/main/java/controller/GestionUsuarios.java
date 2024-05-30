package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import dao.DaoRuta;
import dao.DaoUsuario;

/**
 * Servlet implementation class GestionUsuarios
 */
public class GestionUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionUsuarios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			String respuestaJSON;
			respuestaJSON = DaoUsuario.getInstance().listarJson();
			System.out.println(respuestaJSON);
			
			PrintWriter out = response.getWriter();
			out.print(respuestaJSON);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}

	/**
	 * getParameter siempre devuelve String, para obtener un número hay que parsear.
	 * sendRedirect al final para que redirija a la web que se desee, para que el servlet no quede "muerto".
	 * En este caso el usuario seráredirigido a la lista de usuarios al insertar.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String nombre = request.getParameter("nombre");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		int permiso = Integer.parseInt(request.getParameter("permiso"));
		//String iduser = request.getParameter("iduser");
				
		Usuario u = new Usuario(nombre, username, email, permiso);
		System.out.println(u.toString());
		
		try {
			DaoUsuario.getInstance().insertarUsuario(u);
			/*
			if (iduser == "") {
				DaoUsuario.getInstance().insertarUsuario(u);
			}else {
				//int idInt = Integer.parseInt(iduser);
				//u.setIduser(idInt);
				//u.actualizar();
				//u.actualizar(Integer.parseInt(iduser)); //abreviatura
			}*/
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		//response.sendRedirect("listarUsuarios.html");	
		
	}

	
	
}
