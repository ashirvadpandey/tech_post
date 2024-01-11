package com.tech.loom.dao;

import java.sql.*;

import com.tech.loom.entities.Users;

public class UserDao {

	private Connection con;

	public UserDao(Connection con) {
		this.con = con;
	}

	// Method to insert user to database

	public boolean saveUser(Users user) {
		boolean f = false;
		try {

			String query = "insert into users(name, email, password, gender, about) values(?,?,?,?,?)";
			PreparedStatement ps = this.con.prepareStatement(query);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getGender());
			ps.setString(5, user.getAbout());

			ps.executeUpdate();
			f = true;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return f;
	}

	// user update
	public boolean updateUser(Users user) {

		boolean isUpdated = false;
		try {
			String query = "UPDATE users SET name = ?, email = ?, password=?, gender=?,  about = ?, profile=? WHERE id=?";
			PreparedStatement ps = this.con.prepareStatement(query);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getGender());
			ps.setString(5, user.getAbout());
			ps.setString(6, user.getProfile());
			ps.setInt(7, user.getId());

			ps.executeUpdate();
			isUpdated = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdated;

	}

	// get user by email and password
	public Users getUserByEmailAndPassword(String email, String password) {
		Users user = null;

		try {

			String query = "select * from users where email=? and password=?";
			PreparedStatement ps = this.con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);

			ResultSet set = ps.executeQuery();

			if (set.next()) {
				user = new Users();

				// Data from db
				String name = set.getString("name");
				user.setName(name);
				user.setId(set.getInt("id"));
				user.setEmail(set.getString("email"));
				user.setPassword(set.getString("password"));
				user.setGender(set.getString("gender"));
				user.setAbout(set.getString("about"));
				user.setDateTime(set.getTimestamp("dateTime"));
				user.setProfile(set.getString("profile"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	// Get user details by userId
	public Users getUserByUserId(int userId) {

		Users user = null;

		try {

			String q = "select * from users where id=?";
			PreparedStatement ps = this.con.prepareStatement(q);
			ps.setInt(1, userId);
			ResultSet set = ps.executeQuery();

			if (set.next()) {
				user = new Users();

				// Data from db
				String name = set.getString("name");
				user.setName(name);
				user.setId(set.getInt("id"));
				user.setEmail(set.getString("email"));
				user.setPassword(set.getString("password"));
				user.setGender(set.getString("gender"));
				user.setAbout(set.getString("about"));
				user.setDateTime(set.getTimestamp("dateTime"));
				user.setProfile(set.getString("profile"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	// Check that email exists or not
	@SuppressWarnings("finally")
	public boolean doesEmailExist(String email) {

		boolean emailExists = false;
		PreparedStatement ps = null;
		ResultSet set = null;

		try {

			String q = "select count(*) from users where email=?";
			ps = this.con.prepareStatement(q);
			ps.setString(1, email);

			set = ps.executeQuery();

			if (set.next()) {
				int count = set.getInt(1);
				if (count > 0) {
					emailExists = true;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the ResultSet, PreparedStatement, and any database connections
			try {
				if (set != null) {
					set.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// Handle the exception or log the error
			}

			return emailExists;

		}
	}
}
