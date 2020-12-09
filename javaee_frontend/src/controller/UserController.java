package controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;

import beans.ChartData;
import beans.Login;
import beans.User;
import business.UserBusinessInterface;
import business.WeatherBusinessService;
import util.LoggingInterceptor;
import util.UserNotFoundException;
import util.UserTakenException;

@Named
@ViewScoped
@Interceptors(LoggingInterceptor.class)
public class UserController implements Serializable
{
	@EJB
	UserBusinessInterface userService;
	
	@EJB
	WeatherBusinessService weatherService;
	
	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.invalidate();
		return "index.xhtml";
	}
	
	public String register(User user)
	{
		FacesContext.getCurrentInstance().getExternalContext()
			.getRequestMap().put("user", user);
		try {
			userService.create(user);
		} catch (UserTakenException e) {
			return "Register.xhtml";
		}
		return "Login.xhtml";
	}
	
	public String login(Login login)
	{
		
		FacesContext.getCurrentInstance().getExternalContext()
		.getRequestMap().put("login", login);
		
		try {
			User u = userService.login(login.getEmail(), login.getPassword());
			FacesContext.getCurrentInstance().getExternalContext()
			.getRequestMap().put("user", u);
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("user_id", u.getId());
			
			ChartData chartData = weatherService.getDailyWeather();
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
