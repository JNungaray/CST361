package beans;

public class Weather {
	public String time;
	public String date;
	public String month;
	public double temperature;

	public Weather() {
		this.time = "";
		this.date = "";
		this.month = "";
		this.temperature = 0;
	}
	
	public Weather(String time, String date, String month, double temperature) {
		this.time = time;
		this.date = date;
		this.month = month;
		this.temperature = temperature;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
}
