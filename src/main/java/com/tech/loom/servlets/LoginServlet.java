package com.tech.loom.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import com.tech.loom.dao.UserDao;
import com.tech.loom.entities.Message;
import com.tech.loom.entities.Users;
import com.tech.loom.helper.ConnectionProvider;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		//Login-- fetch username and password
		String userEmail = request.getParameter("email");
		String userPassword = request.getParameter("password");
		
		UserDao dao = new UserDao(ConnectionProvider.getConnection());
		
		Users u_ser = dao.getUserByEmailAndPassword(userEmail, userPassword);
		
		if(u_ser==null) {
			//error
//			out.println("Invalid Details..try again");
			
			Message msg = new Message("Invalid username or password ", "error", "alert-danger");
			HttpSession session = request.getSession();
			session.setAttribute("msg", msg);
			
			response.sendRedirect("Login.jsp");
			
		}else {
			//Login success
			HttpSession session = request.getSession();
			session.setAttribute("currentUser", u_ser);
			response.sendRedirect("Profile.jsp");
			
		}
		
	}

}
