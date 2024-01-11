package com.tech.loom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LikeDao {

	Connection con;

	public LikeDao(Connection con) {
		super();
		this.con = con;
	}

	// insert like into DB
	public boolean insertLike(int pid, int uid) {

		boolean like = false;

		try {

			String q = "insert into likes(pid, uid) values(?,?)";
			PreparedStatement ps = this.con.prepareStatement(q);
			// set values dynamicaly
			ps.setInt(1, pid);
			ps.setInt(2, uid);

			ps.executeUpdate();
			like = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return like;
	}

	// fetch likes count on specific post from DB
	public int countLikeOnPost(int pid) {

		int count = 0;
		try {

			String q = "select count(*) from likes where pid=?";
			PreparedStatement ps = this.con.prepareStatement(q);
			ps.setInt(1, pid);

			ResultSet set = ps.executeQuery();

			if (set.next()) {
				count = set.getInt("count(*)");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	// To check user liked the post or not?

	public boolean isLikedByUser(int pid, int uid) {

		boolean liked = false;

		try {

			String q = " select * from likes where pid=? and uid=?";
			PreparedStatement ps = this.con.prepareStatement(q);
			ps.setInt(1, pid);
			ps.setInt(2, uid);
			ResultSet set = ps.executeQuery();

			if (set.next()) {
				liked = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return liked;
	}

	// function for dislike the post
	public boolean deleteLike(int pid, int uid) {
		boolean dislike = false;

		try {

			String q = "delete from likes where pid=? and uid=?";
			PreparedStatement ps = this.con.prepareStatement(q);
			ps.setInt(1, pid);
			ps.setInt(2, uid);
			ps.executeUpdate();

			dislike = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dislike;
	}

	
}
