/*package com.elex.ssp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

public class GeoIpCity {

	private static  DatabaseReader reader;
	*//**
	 * @param args
	 * @throws GeoIp2Exception 
	 * @throws IOException 
	 *//*
	public static void main(String[] args) throws IOException, GeoIp2Exception {

		System.out.println(getCityName("180.251.178.183"));

	}
	
	static{
		
		try {
			reader = new DatabaseReader.Builder(GeoIpCity.class.getClassLoader().getResourceAsStream("GeoIP2-City.mmdb")).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getCityName(String ip){
		try {
			InetAddress ipAddress = InetAddress.getByName(ip);
			CityResponse response = reader.city(ipAddress);
			return response.getCity().getName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeoIp2Exception e) {
			e.printStackTrace();
		}
		
		return null;
						
	}

}
*/