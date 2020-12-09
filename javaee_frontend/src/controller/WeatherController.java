package controller;

import javax.faces.view.ViewScoped;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.interceptor.Interceptors;

import beans.ChartData;
import business.WeatherBusinessService;
import util.LoggingInterceptor;

@Named
@ViewScoped
@Interceptors(LoggingInterceptor.class)
public class WeatherController implements Serializable
{

	public String getData(int type) {
		WeatherBusinessService service = new WeatherBusinessService();
		
		ChartData chartData = new ChartData();
		
		if (type == 0) {
			chartData = service.getDailyWeather();
		} else if (type == 1) {
			chartData = service.getWeeklyWeather();
		} else if (type == 2) {
			chartData = service.getMonthlyWeather();
		}
		
		FacesContext.getCurrentInstance().getExternalContext()
			.getRequestMap().put("chartData", chartData);
		
		return "index.xhtml";
	}
}
