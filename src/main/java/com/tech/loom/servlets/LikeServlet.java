package com.tech.loom.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.tech.loom.dao.LikeDao;
import com.tech.loom.helper.ConnectionProvider;

/**
 * Servlet implementation class LikeServlet
 */
public class LikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		String operation = request.getParameter("operation");
		int uid = Integer.parseInt(request.getParameter("uid"));
		int pid = Integer.parseInt(request.getParameter("pid"));
		
//		out.println("data from server");
//		out.println(operation);
//		out.println(uid);
//		out.println(pid);
//		
//		out.println("<h1>This is Like servlet page</h1>");
		
		LikeDao ldao = new LikeDao(ConnectionProvider.getConnection());
		if(operation.equals("like")) {
			if(!ldao.isLikedByUser(pid, uid)) {
				boolean f = ldao.insertLike(pid, uid);
				out.println(f);
				System.out.println(f);
			}else {
				boolean b = ldao.deleteLike(pid, uid);
				b = false;
				out.println(b);
				System.out.println(b);
			}
			
		}
		
	}

	

}
