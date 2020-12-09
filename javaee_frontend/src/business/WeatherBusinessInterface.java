package business;

import beans.ChartData;

public interface WeatherBusinessInterface {
	public ChartData getDailyWeather();
	public ChartData getWeeklyWeather();
	public ChartData getMonthlyWeather();
}
