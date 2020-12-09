package business;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.mysql.jdbc.Connection;

import beans.User;
import data.DataAccessInterface;
import util.UserNotFoundException;

@Stateless
public class UserBusiness implements UserBusinessInterface {

	@EJB
	DataAccessInterface<User> dao;
	
	public UserBusiness() {
	}
	
	@Override
	public boolean create(User u) {
		return dao.create(u);
	}
	
	@Override
	public User login(String email, String password) throws UserNotFoundException {
		return dao.get(email, password);
	}
	
	@Override
	public User getOne(int id) {
		return dao.getById(id);
	}

	
	
	
	
	@Override
	public User[] read() {
		return new User[0];
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
}
