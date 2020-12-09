package business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONObject;

import beans.ChartData;
import beans.Weather;

@Stateless
public class WeatherBusinessService {
	public WeatherBusinessService() {
		
	}
	
	public ChartData getDailyWeather() {
		ChartData data = new ChartData();
		
		try {
			URL url = new URL("http://localhost:8888/simulated_iot/api/getWeather.php?type=daily");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			con.getResponseCode();
			
			BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			
			JSONObject obj = new JSONObject(content.toString());
			System.out.println(obj);	
			int statusCode = obj.getJSONObject("status").getInt("code");
			
			if (statusCode == 200) {
				JSONArray arr = obj.getJSONArray("data");
				ArrayList<Weather> weathers = new ArrayList<Weather>();
				
				for(int i = 0; i < arr.length(); i++) {
					JSONObject o = arr.getJSONObject(i);
					String time = o.getString("time");
					String date = o.getString("date");
					String month = o.getString("month");
					double temp = o.getDouble("temp");
					
					weathers.add(new Weather(time, date, month, temp));
				}
				data.setType(0);
				data.setId(1);
				data.setWeatherList(weathers);

			} else {
				return null;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	public ChartData getWeeklyWeather() {
		ChartData data = new ChartData();
		
		try {
			URL url = new URL("http://localhost:8888/simulated_iot/api/getWeather.php?type=weekly");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			con.getResponseCode();
			
			BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			
			JSONObject obj = new JSONObject(content.toString());
			System.out.println(obj);	
			int statusCode = obj.getJSONObject("status").getInt("code");
			
			if (statusCode == 200) {
				JSONArray arr = obj.getJSONArray("data");
				ArrayList<Weather> weathers = new ArrayList<Weather>();
				
				for(int i = 0; i < arr.length(); i++) {
					JSONObject o = arr.getJSONObject(i);
					String time = o.getString("time");
					String date = o.getString("date");
					String month = o.getString("month");
					double temp = o.getDouble("temp");
					
					weathers.add(new Weather(time, date, month, temp));
				}
				data.setType(1);
				data.setId(1);
				data.setWeatherList(weathers);

			} else {
				return null;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	public ChartData getMonthlyWeather() {
		ChartData data = new ChartData();
		
		try {
			URL url = new URL("http://localhost:8888/simulated_iot/api/getWeather.php?type=monthly");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			con.getResponseCode();
			
			BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			
			JSONObject obj = new JSONObject(content.toString());
			System.out.println(obj);	
			int statusCode = obj.getJSONObject("status").getInt("code");
			
			if (statusCode == 200) {
				JSONArray arr = obj.getJSONArray("data");
				ArrayList<Weather> weathers = new ArrayList<Weather>();
				
				for(int i = 0; i < arr.length(); i++) {
					JSONObject o = arr.getJSONObject(i);
					String time = o.getString("time");
					String date = o.getString("date");
					String month = o.getString("month");
					double temp = o.getDouble("temp");
					
					weathers.add(new Weather(time, date, month, temp));
				}
				data.setType(2);
				data.setId(1);
				data.setWeatherList(weathers);

			} else {
				return null;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
}
