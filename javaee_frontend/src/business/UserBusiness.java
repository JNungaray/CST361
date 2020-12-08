package business;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ejb.Stateless;

import com.mysql.jdbc.Connection;

import beans.User;
import util.UserNotFoundException;

@Stateless
public class UserBusiness implements UserBusinessInterface {

	private Connection conn;
	
	private static final String CONNECTION_STRING = "jdbc:mysql://localhost:8889/cst361clc";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	
	public UserBusiness() {
		try {
			conn = (Connection) DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean create(User u) {
		try
		{
			String sql = "SELECT count(*) as total FROM user WHERE email = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getEmail());
			
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			int taken = rs.getInt("total");
			
			if (taken > 0) {
				// email taken
			}
			else {
				// add user
				String insertStatement = "INSERT INTO user (firstname, lastname, email, password) VALUES (?, ?, ?, ?)";
				PreparedStatement insert = conn.prepareStatement(insertStatement);
				insert.setString(1, u.getFirstname());
				insert.setString(2, u.getLastname());
				insert.setString(3, u.getEmail());
				insert.setString(4, u.getPassword());
				
				int result = insert.executeUpdate();
				
				if (result == 1) {
					return true;
				}
				else {
					return false;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public User login(String email, String password) throws UserNotFoundException {
		User u = null;
		String sql = "SELECT * FROM user WHERE email = ? AND BINARY password = ?";
		try
		{
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next())
			{
				int id = rs.getInt("id");
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				u = new User(firstname, lastname, email, "", id);
			}
			else {
				throw new UserNotFoundException();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public User[] read() {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM user";
		try
		{
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String firstname = rs.getString("fistname");
				String lastname = rs.getString("lastname");
				User u = new User(firstname, lastname, email, password, id);
				users.add(u);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (User[]) users.toArray();
	}

	@Override
	public User getOne(int id) {
		User u = null;
		String sql = "SELECT * FROM user WHERE id = ?";
		try
		{
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next())
			{
				String email = rs.getString("email");
				String firstname = rs.getString("fistname");
				String lastname = rs.getString("lastname");
				u = new User(firstname, lastname, email, "", id);
			}
			else {
				throw new UserNotFoundException();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public int update(User u, int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
