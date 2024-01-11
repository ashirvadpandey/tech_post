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
import java.io.PrintWriter;

import com.tech.loom.dao.PostDao;
import com.tech.loom.entities.Message;
import com.tech.loom.entities.Post;
import com.tech.loom.entities.Users;
import com.tech.loom.helper.ConnectionProvider;
import com.tech.loom.helper.Helper;



@MultipartConfig
public class AddPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		int cid  =Integer.parseInt(request.getParameter("cid"));
		String pTitle = request.getParameter("pTitle");
		String pContent = request.getParameter("pContent");
		String pCode = request.getParameter("pCode");
//		int userId = Integer.parseInt(request.getParameter("userId)"));
		Part part = request.getPart("pic");
		
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("currentUser");
			
//		out.println("your post title is: "+pTitle);
//		out.println(part.getSubmittedFileName());
		
		Post p = new Post(pTitle, pContent, pCode, part.getSubmittedFileName(), null, cid, user.getId());
		PostDao dao = new PostDao(ConnectionProvider.getConnection());
		
		if(dao.savePost(p)) {
			
			ServletContext servletContext = getServletContext();
			String path = servletContext.getRealPath("/") + "blog_pics" + File.separator + part.getSubmittedFileName();
			Helper.saveFile(part.getInputStream(), path);
			
			out.println("done");
		}else {
			out.println("something went wrong");
		}
				
	
	}

}
