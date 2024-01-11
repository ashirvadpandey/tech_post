package com.tech.loom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tech.loom.entities.Category;
import com.tech.loom.entities.Post;

public class PostDao {

	Connection con;

	public PostDao(Connection con) {
		this.con = con;
	}

	// function to getAll Category from database
	public ArrayList<Category> getAllCategories() {
		ArrayList<Category> list = new ArrayList<Category>();

		try {

			String q = "select * from categories";
			Statement st = this.con.createStatement();
			ResultSet set = st.executeQuery(q);

			while (set.next()) {
				int cid = set.getInt("cid");
				String name = set.getString("name");
				String description = set.getString("description");
				Category c = new Category(cid, name, description);
				list.add(c);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// function to save post in database

	public boolean savePost(Post p) {
		boolean f = false;
		try {

			String q = "insert into posts( pTitle, pContent, pCode, pPic, catId, userId) values(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(q);
			ps.setString(1, p.getpTitle());
			ps.setString(2, p.getpContent());
			ps.setString(3, p.getpCode());
			ps.setString(4, p.getpPic());
			ps.setInt(5, p.getCatId());
			ps.setInt(6, p.getUserId());

			ps.executeUpdate();
			f = true;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return f;
	}

	// get all posts
	public List<Post> getAllPosts() {

		List<Post> list = new ArrayList<Post>();
		// Fetch all posts

		try {

			PreparedStatement p = con.prepareStatement("select * from posts order by pid desc");

			ResultSet set = p.executeQuery();
			while (set.next()) {

				int pid = set.getInt("pid");
				String pTitle = set.getString("pTitle");
				String pContent = set.getString("pContent");
				String pCode = set.getString("pCode");
				String pPic = set.getString("pPic");
				Timestamp date = set.getTimestamp("pDate");
				int catId = set.getInt("catId");
				int userId = set.getInt("userId");

				Post post = new Post(pid , pTitle, pContent, pCode, pPic, date, catId, userId);

				list.add(post);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	// get post by cat id
	public List<Post> getPostByCatId(int catId) {

		List<Post> list = new ArrayList<Post>();

		// fetch all post by cat id
		try {

			PreparedStatement p = con.prepareStatement("select * from posts where catId=?");
			p.setInt(1, catId);
			ResultSet set = p.executeQuery();
			
			while (set.next()) {

				int pid = set.getInt("pid");
				String pTitle = set.getString("pTitle");
				String pContent = set.getString("pContent");
				String pCode = set.getString("pCode");
				String pPic = set.getString("pPic");
				Timestamp date = set.getTimestamp("pDate");
				int userId = set.getInt("userId");

				Post post = new Post(pid, pTitle, pContent, pCode, pPic, date, catId, userId);

				list.add(post);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

		// Get post by post id
	public Post getPostByPostId(int postId) {
	    Post post = null;

	    try {
	        PreparedStatement p = con.prepareStatement("SELECT * FROM posts WHERE pid = ?");
	        p.setInt(1, postId);
	        ResultSet set = p.executeQuery();

	        if (set.next()) {
	            int pid = set.getInt("pid");
	            String pTitle = set.getString("pTitle");
	            String pContent = set.getString("pContent");
	            String pCode = set.getString("pCode");
	            String pPic = set.getString("pPic");
	            Timestamp date = set.getTimestamp("pDate");
	            int catId = set.getInt("catId");
	            int userId = set.getInt("userId");

	            post = new Post(pid, pTitle, pContent, pCode, pPic, date, catId, userId);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return post;
	}
	
	// Get all post by userId
	public List<Post> getAllPostsByUserId(int userId) {
	    List<Post> posts = new ArrayList<>();

	    try {
	        PreparedStatement p = con.prepareStatement("SELECT * FROM posts WHERE userId = ?");
	        p.setInt(1, userId);
	        ResultSet set = p.executeQuery();

	        while (set.next()) {
	            int pid = set.getInt("pid");
	            String pTitle = set.getString("pTitle");
	            String pContent = set.getString("pContent");
	            String pCode = set.getString("pCode");
	            String pPic = set.getString("pPic");
	            Timestamp date = set.getTimestamp("pDate");
	            int catId = set.getInt("catId");

	            // Add the retrieved post to the list
	            Post post = new Post(pid, pTitle, pContent, pCode, pPic, date, catId, userId);
	            posts.add(post);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return posts;
	}
	
	
	// get post by  categoryId and user id
	public List<Post> getPostByCatIdAndUserId(int catId, int userId) {
	    List<Post> posts = new ArrayList<>();

	    try {
	        PreparedStatement p = con.prepareStatement("SELECT * FROM posts WHERE catId = ? AND userId = ?");
	        p.setInt(1, catId);
	        p.setInt(2, userId);
	        ResultSet set = p.executeQuery();

	        while (set.next()) {
	            int pid = set.getInt("pid");
	            String pTitle = set.getString("pTitle");
	            String pContent = set.getString("pContent");
	            String pCode = set.getString("pCode");
	            String pPic = set.getString("pPic");
	            Timestamp date = set.getTimestamp("pDate");

	            // Add the retrieved post to the list
	            Post post = new Post(pid, pTitle, pContent, pCode, pPic, date, catId, userId);
	            posts.add(post);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return posts;
	}

	
}
