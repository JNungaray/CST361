package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ManagedBean
@ViewScoped
public class User
{	
	private int id;
	
	@NotNull
	@Size(min=3, max=50)
	private String firstname;
	
	@NotNull
	@Size(min=3, max=50)
	private String lastname;
	
	@NotNull
	@Size(min=5, max=100)
	private String email;
	
	@NotNull
	@Size(min=8, max=50)
	private String password;

	public User() {
		id = 0;
		firstname = "";
		lastname = "";
		email = "";
		password = "";
	}
	
	public User(String firstname, String lastname, String email, String password, int id) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
