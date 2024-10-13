package WeatherFinder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;

public class Weather {
	
	private double latitude;
	private double longitude;
	private String link;
	private String forecastLink;
	
	private String timePeriod;
	private Boolean isDayTime;
	private Long temperature;
	private String shortForecast;
	private String detailedForecast;
	
	public void findWeather(String address) throws IOException {
		
		forecastLink = null;
		try {
			forecastLink = findForecast(address);
			if (forecastLink == null) return;
			@SuppressWarnings("deprecation")
			URL url = new URL(forecastLink);
			URLConnection connect = url.openConnection();
			InputStream is = connect.getInputStream();
			
			Scanner io = new Scanner(is);
			String result = "";
			try {
				result += io.nextLine();
			} catch(Exception e) {
				System.out.println("Error");
			}
			while(io.hasNextLine()) {
				result += io.nextLine();
				result += "\n";
			}
			io.close();
			
			String jsonText = result;
			
			JSONParser parse = new JSONParser();
			try {
				JSONObject obj = (JSONObject) parse.parse(jsonText);
				JSONObject propObj = (JSONObject) parse.parse(String.valueOf(obj.get("properties")));
				JSONArray perObj = (JSONArray) parse.parse(String.valueOf(propObj.get("periods")));
				JSONObject period1 = (JSONObject) parse.parse(String.valueOf(perObj.get(0)));
				temperature =  (long) period1.get("temperature");
				timePeriod = (String) period1.get("name");
				isDayTime = (Boolean) period1.get("isDayTime");
				shortForecast = (String) period1.get("shortForecast");
				detailedForecast = (String) period1.get("detailedForecast");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private String findForecast(String address) throws IOException {
		String forecastLink = null;
		
		try {
			double[] coordinates = findCoord(address);
			latitude = coordinates[0];
			longitude = coordinates[1];
		} catch(Exception e) {
			e.printStackTrace();
			return forecastLink;
		}
		
		
		link = "https://api.weather.gov/points/"+latitude+","+longitude;
		
		
		try {
			@SuppressWarnings("deprecation")
			URL url = new URL(link);
			URLConnection connect = url.openConnection();
			InputStream is = connect.getInputStream();
			
			Scanner io = new Scanner(is);
			String result = "";
			try {
				result += io.nextLine();
			} catch(Exception e) {
				System.out.println("Error");
			}
			while(io.hasNextLine()) {
				result += io.nextLine();
				result += "\n";
			}

			io.close();
			
			String jsonText = result;
			
			JSONParser parse = new JSONParser();
			try {
				JSONObject obj = (JSONObject) parse.parse(jsonText);
				JSONObject propObj = (JSONObject) parse.parse(String.valueOf(obj.get("properties")));
				forecastLink = String.valueOf(propObj.get("forecast"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return forecastLink;
	}
	
	private double[] findCoord(String address) throws IOException {
		
		final String API_KEY = "66e98bf606001516350783rtmf0b86d";
		String geocoderLink = "https://geocode.maps.co/search?q="+address+"&api_key="+API_KEY;
		double latitude = 0.0;
		double longitude = 0.0;
		
		try {
			@SuppressWarnings("deprecation")
			URL url = new URL(geocoderLink);
			URLConnection connect = url.openConnection();
			InputStream is = connect.getInputStream();
			
			Scanner io = new Scanner(is);
			String result = "";
			try {
				result += io.nextLine();
			} catch(Exception e) {
				System.out.println("Error");
			}
			while(io.hasNextLine()) {
				result += io.nextLine();
				result += "\n";
			}
			//System.out.println(result);
			io.close();
			
			String jsonText = result;
			
			JSONParser parse = new JSONParser();
			try {
				JSONArray jArray = (JSONArray) parse.parse(jsonText);
				JSONObject jObj = (JSONObject) jArray.get(0);
				//System.out.println(jObj.get("lat").getClass());
				latitude = Double.parseDouble((String)jObj.get("lat"));
				longitude = Double.parseDouble((String)jObj.get("lon"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return new double[]{latitude,longitude};
	}
	
	public boolean isDayTime() {
		return isDayTime;
	}
	
	public String getDetailedForecast() {
		return "Today: " + detailedForecast;
	}
	
	@Override
	public String toString() {
		return timePeriod + ": Temperature of " + temperature + "F, " + shortForecast; 
	}
	
	
}
