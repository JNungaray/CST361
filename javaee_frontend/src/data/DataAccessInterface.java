package data;

public interface DataAccessInterface<T> {
	public boolean create(T t);
	public T get(String email, String password);
	public T getById(int id);
}
