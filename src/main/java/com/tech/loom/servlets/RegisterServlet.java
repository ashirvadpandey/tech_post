package com.tech.loom.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.tech.loom.dao.UserDao;
import com.tech.loom.entities.Users;
import com.tech.loom.helper.ConnectionProvider;

/**
 * Servlet implementation class RegisterServlet
 */
@MultipartConfig
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		//fetch all form data
		
		
		try {
			
			String name = request.getParameter("user_name");
			String email = request.getParameter("user_email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			String about = request.getParameter("about");
			String check = request.getParameter("check");
			//create user object and set all data to that object
			Users user = new Users(name, email, password, gender, about);
			
			
			// create a user dao object
			UserDao dao = new UserDao(ConnectionProvider.getConnection());
			boolean emailExists = dao.doesEmailExist(email);
			
			if(emailExists) {
				response.getWriter().write("emailExists");
				
			} else if (check == null || !check.equals("on")) {
	            response.getWriter().write("checkboxNotChecked");
				
			}
			
			else if(dao.saveUser(user)) {
				//save
				out.println("done");
			}else {
				out.println("error");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
