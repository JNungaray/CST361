package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import beans.ChartData;
import business.WeatherBusinessService;

@ManagedBean
@ViewScoped
public class WeatherController {

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
