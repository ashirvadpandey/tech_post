package com.tech.loom.servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import jakarta.servlet.ServletContext;

import com.tech.loom.dao.UserDao;
import com.tech.loom.entities.Message;
import com.tech.loom.entities.Users;
import com.tech.loom.helper.ConnectionProvider;
import com.tech.loom.helper.Helper;

@MultipartConfig
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		try {

			// Fetch all data of user update
			String userName = request.getParameter("user_name");
			String userEmail = request.getParameter("user_email");
			String userAbout = request.getParameter("user_about");
			String userPassword = request.getParameter("user_password");
			Part part = request.getPart("profile_pic");
			String imageName = part.getSubmittedFileName();

			// Get the user from the session
			HttpSession session = request.getSession();

			Users user = (Users) session.getAttribute("currentUser");
			user.setName(userName);
			user.setEmail(userEmail);
			user.setPassword(userPassword);
			user.setAbout(userAbout);
			String oldFile = user.getProfile();
			user.setProfile(imageName);

			// Update Database
			UserDao userDao = new UserDao(ConnectionProvider.getConnection());
			boolean ans = userDao.updateUser(user);
			if (ans) {

				// get path of pic
				ServletContext servletContext = getServletContext();
				String path = servletContext.getRealPath("/") + "pics" + File.separator + user.getProfile();

				// Helper.deleteFile(path)
				String pathOldFile = servletContext.getRealPath("/") + "pics" + File.separator + oldFile;

				if (!oldFile.equals("default.png")) {
					Helper.deleteFile(pathOldFile);
				}
				
				// Save file
				if (Helper.saveFile(part.getInputStream(), path)) {

					Message msg = new Message("Profile updated successfully ", "success", "alert-success");
					session.setAttribute("msg", msg);
				} else {

					Message msg = new Message("File not saved! ", "error", "alert-danger");
					session.setAttribute("msg", msg);
				}
			} else {
				Message msg = new Message("File not deleted! ", "error", "alert-danger");
				session.setAttribute("msg", msg);
			}

			response.sendRedirect("Profile.jsp");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
