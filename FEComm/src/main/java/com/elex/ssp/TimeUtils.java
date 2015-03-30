package com.elex.ssp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeUtils {
	
	private static Map<String,Zone> timeZone = new HashMap<String,Zone>();
	//private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Calendar ca = Calendar.getInstance();
	public static final String DAWN="dawn";//凌晨
	public static final String MORNING="morning";//早上
	public static final String FORENOON="foreNoon";//上午
	public static final String NOON="noon";//中午
	public static final String AFTERNOON="afterNoon";//下午
	public static final String EVENING="evening";//晚上
	public static final String MIDNIGHT="midnight";//午夜
	public static final String WORKING="workDay";
	public static final String VACATION="restDay";
	
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
		//Date now = new Date();
		
		String[] myArgs = {"2015-01-25 19:20:03","zh"};
		//System.out.println(isWorkOrVacation(now));
		if(getTimeDimension(myArgs) !=null){
			for(String t:getTimeDimension(myArgs)){
				System.out.println(t);
			}
		}
		

		
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
		if(hour >= con.get("mnb") && hour <con.get("mne")){
			return MIDNIGHT;
		}else if(hour >= con.get("db") && hour <con.get("de")){
			return DAWN;
		}else if(hour >= con.get("mb") && hour <con.get("me")){
			return MORNING;
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
	
	private static Date getTimeByNation(Date origDay, String nation){
        Zone zone = timeZone.get(nation);
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

    public static Date getTimeByNation(String[] args){
        Date origDay = Timestamp.valueOf(args[0]);
        return getTimeByNation(origDay, args[1]);
    }
	
	public static String[] getTimeDimension(String[] args){
		if(args.length==2){
			if(args[0]!=null && args[1]!=null){
				Date day = TimeUtils.getTimeByNation(args);
				String[] timeDim = new String[3];
				timeDim[0] = TimeUtils.getHour(day);
				timeDim[1] = TimeUtils.getDayPart(day);
				timeDim[2] = TimeUtils.isWorkOrVacation(day);
				return timeDim;
			}
			return null;
		}
		return null;
		
	}

    public static String[] getTimeDimension(Date origDay, String nation) throws ParseException{
        if(origDay!=null && nation!=null){
            Date day = TimeUtils.getTimeByNation(origDay, nation);
            String[] timeDim = new String[3];
            timeDim[0] = TimeUtils.getHour(day);
            timeDim[1] = TimeUtils.getDayPart(day);
            timeDim[2] = TimeUtils.isWorkOrVacation(day);
            return timeDim;
        }
        return null;
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
