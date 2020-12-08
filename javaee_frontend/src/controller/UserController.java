package controller;

import java.io.Console;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import beans.ChartData;
import beans.Login;
import beans.User;
import business.UserBusiness;
import business.WeatherBusinessService;
import util.UserNotFoundException;
import util.UserTakenException;

@ManagedBean
@ViewScoped
public class UserController 
{
	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.invalidate();
		return "index.xhtml";
	}
	
	public String register(User user)
	{
		UserBusiness service = new UserBusiness();
		FacesContext.getCurrentInstance().getExternalContext()
			.getRequestMap().put("user", user);
		try {
			service.create(user);
		} catch (UserTakenException e) {
			return "Register.xhtml";
		}
		return "Login.xhtml";
	}
	
	public String login(Login login)
	{
		UserBusiness service = new UserBusiness();
		
		FacesContext.getCurrentInstance().getExternalContext()
		.getRequestMap().put("login", login);
		
		try {
			User u = service.login(login.getEmail(), login.getPassword());
			FacesContext.getCurrentInstance().getExternalContext()
			.getRequestMap().put("user", u);
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("user_id", u.getId());
			
			WeatherBusinessService w = new WeatherBusinessService();
			ChartData chartData = w.getDailyWeather();
			FacesContext.getCurrentInstance().getExternalContext()
				.getRequestMap().put("chartData", chartData);
			System.out.println(chartData.getDataString());
			
		} catch (UserNotFoundException e) {
			return "Login.xhtml";
		} catch (Exception e) {
			e.printStackTrace();
			return "Login.xhtml";
		}
		
		return "index.xhtml";
	}
}
