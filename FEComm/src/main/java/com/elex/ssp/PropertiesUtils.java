package com.elex.ssp;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class PropertiesUtils {

	private static Properties pop = new Properties();
	private static Map<String,Integer> dayPartConditonMap = new HashMap<String,Integer>();
	private static String[] pbids;
	private static String[] pbTags;
	static{
		InputStream is = null;
		try{
			is = PropertiesUtils.class.getClassLoader().getResourceAsStream("config.properties");
			pop.load(is);
			LoadDayPartConditonMap();
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			try {
				if(is!=null)is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	
	
	public static void LoadDayPartConditonMap(){
		
		String[] con = pop.getProperty("dayPart").split(",");
		dayPartConditonMap.put("db", Integer.parseInt(con[0]));
		dayPartConditonMap.put("de", Integer.parseInt(con[1]));
		dayPartConditonMap.put("fnb", Integer.parseInt(con[1]));
		dayPartConditonMap.put("fne", Integer.parseInt(con[2]));
		dayPartConditonMap.put("nb", Integer.parseInt(con[2]));
		dayPartConditonMap.put("ne", Integer.parseInt(con[3]));
		dayPartConditonMap.put("anb", Integer.parseInt(con[3]));
		dayPartConditonMap.put("ane", Integer.parseInt(con[4]));
		dayPartConditonMap.put("eb", Integer.parseInt(con[4]));
		dayPartConditonMap.put("ee", Integer.parseInt(con[5]));
		
	}



	public static Map<String, Integer> getDayPartConditonMap() {
		return dayPartConditonMap;
	}
	
	public static String[] getPbIDs(){
		return pop.getProperty("pbids").split(",");
	}
	
	public static String[] getPbTags(){
		return pop.getProperty("pbtags").split(",");
	}
	
	
}
