package com.tra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterService implements Constants {
	private static Logger lgr = Logger.getLogger("com.tra.RegisterService");

	public String saveUser(User usr) {
		Connection con = null;
		PreparedStatement ps1 = null;
		con = JDBCHelper.getConnection();
		try {
			String sql = "insert into user(fname,lname) values(?,?)";
			if (usr.getUserId() != 0) {
				sql = "update user set fname =?, lname = ? where id = ?";
			}
			ps1 = con.prepareStatement(sql);
			ps1.setString(1, usr.getFname());
			ps1.setString(2, usr.getLname());

			if (usr.getUserId() != 0) {
				ps1.setInt(3, usr.getUserId());
			}

			ps1.executeUpdate();
		} catch (SQLException e) {
			lgr.log(Level.WARNING,
					"Something went wrong.. Couldn't save the user", e);

		} finally {
			JDBCHelper.close(ps1);
			JDBCHelper.close(con);
		}
		return SUCCESS;
	}

	public List<User> getUsers() {
		Connection con = null;
		Statement ps1 = null;
		ResultSet rs = null;
		try {
			con = JDBCHelper.getConnection();
			String sql = "select id,fname,lname from user order by fname";
			ps1 = con.createStatement();
			rs = ps1.executeQuery(sql);
			List<User> li = new ArrayList<User>();
			User usr = null;
			while (rs.next()) {
				usr = new User();
				usr.setUserId(rs.getInt("id"));
				usr.setFname(rs.getString("fname"));
				usr.setLname(rs.getString("lname"));
				li.add(usr);
			}
			return li;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCHelper.close(rs);
			JDBCHelper.close(ps1);
			JDBCHelper.close(con);
		}

	}

	public String updateUser(User usr) {
		Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		try {
			con = JDBCHelper.getConnection();
			String sql = "update user set fname=? and lname=? where id=?";
			ps1 = con.prepareStatement(sql);
			ps1.setString(1, usr.getFname());
			ps1.setString(2, usr.getLname());
			ps1.setInt(3, usr.getUserId());
			ps1.executeUpdate();
		} catch (SQLException e) {
			lgr.log(Level.WARNING,
					"Something went wrong.. Couldn't update the user", e);
		} finally {
			JDBCHelper.close(ps1);
			JDBCHelper.close(con);
		}
		return SUCCESS;
	}

	public User getUserById(User usr) {
		Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		try {
			con = JDBCHelper.getConnection();
			String sql = "select id,fname,lname from user where id=?";
			ps1 = con.prepareStatement(sql);
			ps1.setInt(1, usr.getUserId());
			rs = ps1.executeQuery();
			while (rs.next()) {
				usr = new User();
				usr.setUserId(rs.getInt("id"));
				usr.setFname(rs.getString("fname"));
				usr.setLname(rs.getString("lname"));
			}
			return usr;
		} catch (SQLException e) {
			lgr.log(Level.WARNING,
					"Something went wrong.. Couldn't edit the user", e);
			return null;
		} finally {
			JDBCHelper.close(ps1);
			JDBCHelper.close(con);
		}
	}

	public String deleteUser(User usr) {
		Connection con = null;
		PreparedStatement ps1 = null;
		try {
			con = JDBCHelper.getConnection();
			String sql = "delete from user where id=?";
			ps1 = con.prepareStatement(sql);
			ps1.setInt(1, usr.getUserId());
			ps1.executeUpdate();
		} catch (SQLException e) {
			lgr.log(Level.WARNING,
					"Something went wrong.. Couldn't delete the user", e);
			return null;
		} finally {
			JDBCHelper.close(ps1);
			JDBCHelper.close(con);
		}
		return SUCCESS;
	}
}
