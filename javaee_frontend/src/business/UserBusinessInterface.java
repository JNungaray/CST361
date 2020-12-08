package business;

import beans.User;

public interface UserBusinessInterface
{
	public boolean create(User u);
	
	public User[] read();
	
	public User login(String email, String password);
	
	public User getOne(int id);
	
	public int update(User u, int id);
	
	public int delete(int id);
}
