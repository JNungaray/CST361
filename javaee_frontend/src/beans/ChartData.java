package beans;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.json.JSONObject;

@ManagedBean
@ViewScoped
public class ChartData {
	int id;
	int type;
	ArrayList<Weather> weatherList;
	
	public ChartData() {
		this.id = 0;
		this.type = 0;
		this.weatherList = new ArrayList<Weather>();
	}
	
	public ChartData(int id, int type, ArrayList<Weather> weatherList) {
		this.id = id;
		this.type = type;
		this.weatherList = weatherList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Weather> getWeatherList() {
		return weatherList;
	}

	public void setWeatherList(ArrayList<Weather> weatherList) {
		this.weatherList = weatherList;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getDataString() {		
		
		JSONObject obj = new JSONObject();
		
		int arrSize = this.weatherList.size();
		String[] labels = new String[arrSize];
		double[] temps = new double[arrSize];
		
		int numItems = 0;
		
		for(Weather w : weatherList) {
			if(type == 0) {
				labels[numItems] = w.getTime();
			} else {
				labels[numItems] = w.getDate();
			}
			temps[numItems] = w.getTemperature();
			numItems++;
		}
		
		obj.put("labels", labels);
		obj.put("temps", temps);
		obj.put("numItems", numItems);
		
		return obj.toString();
	}
}
