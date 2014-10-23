package com.elex.ssp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeUtils {
	
	private static Map<String,Zone> timeZone = new HashMap<String,Zone>();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Calendar ca = Calendar.getInstance();
	private static final String DAWN="dawn";
	private static final String FORENOON="foreNoon";
	private static final String NOON="noon";
	private static final String AFTERNOON="afterNoon";
	private static final String EVENING="evening";
	private static final String WORKING="workDay";
	private static final String VACATION="restDay";
	
	static{
		try {
			loadTimeZone();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		Date now = new Date();
		
		String[] myArgs = {sdf.format(now),"qa"};
		System.out.println(isWorkOrVacation(now));

	}
	
	private static void loadTimeZone() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("time_zone.csv")));
		String line;
		String[] kv;
		while ((line = reader.readLine()) != null) {
			kv= line.split(",");
			if(kv.length==2){
				
				if(kv[1].equals("0")){
					timeZone.put(kv[0],new Zone(null,0,0));
				}else{
					timeZone.put(kv[0],new Zone(kv[1].substring(0, 1),Integer.parseInt(kv[1].substring(1, kv[1].indexOf(":"))),Integer.parseInt(kv[1].substring(kv[1].indexOf(":")+1, kv[1].length()))));
				}
			}
			
		}
		reader.close();	
	}
	
	public static String getDayPart(Date day){
		ca.setTime(day);
		int hour = ca.get(Calendar.HOUR_OF_DAY);
		Map<String,Integer> con =  PropertiesUtils.getDayPartConditonMap();
		if(hour >= con.get("db") && hour <con.get("de")){
			return DAWN;
		}else if(hour >= con.get("fnb") && hour <con.get("fne")){
			return FORENOON;
		}else if(hour >= con.get("nb") && hour <con.get("ne")){
			return NOON;
		}else if(hour >= con.get("anb") && hour <con.get("ane")){
			return AFTERNOON;
		}else if(hour >= con.get("eb") && hour <con.get("ee")){
			return EVENING;
		}else{
			return "other";
		}

	}
	
	public static Date getTimeByNation(String[] args) throws ParseException{
		Date origDay = sdf.parse(args[0]);		
		Zone zone = timeZone.get(args[1]);
		if(zone == null){
			return origDay;
		}else{
			if(zone.getOperator()==null){
				return origDay;
			}else{
				ca.setTime(origDay);
				if(zone.getOperator().equals("+")){					
					ca.add(Calendar.HOUR, zone.getHour());
					ca.add(Calendar.MINUTE, zone.getMinute());
					return ca.getTime();
				}else if(zone.getOperator().equals("-")){
					ca.add(Calendar.HOUR, -(zone.getHour()));
					ca.add(Calendar.MINUTE, -(zone.getMinute()));
					return ca.getTime();
				}else{
					return origDay;
				}
			}
		}
		
	}
	
	public static String getHour(Date day){
		ca.setTime(day);
		return  Integer.toString(ca.get(Calendar.HOUR_OF_DAY));
	}
	
	public static String isWorkOrVacation(Date day){
		if(day == null){
			return null;
		}
		ca.setTime(day);
		int week = ca.get(Calendar.DAY_OF_WEEK);
		if(week==0 || week ==7){
			return VACATION;
		}else{
			return WORKING;
		}
	}
		

}
