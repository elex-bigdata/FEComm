package com.elex.ssp;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class PropertiesUtils {

	private static Properties pop = new Properties();
	private static Map<String,Integer> dayPartConditonMap = new HashMap<String,Integer>();
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
		dayPartConditonMap.put("mnb", Integer.parseInt(con[0]));//午夜开始
		dayPartConditonMap.put("mne", Integer.parseInt(con[1]));//午夜结束
		dayPartConditonMap.put("db", Integer.parseInt(con[1]));//凌晨开始
		dayPartConditonMap.put("de", Integer.parseInt(con[2]));//凌晨结束
		dayPartConditonMap.put("mb", Integer.parseInt(con[2]));//早晨开始
		dayPartConditonMap.put("me", Integer.parseInt(con[3]));//早晨结束
		dayPartConditonMap.put("fnb", Integer.parseInt(con[3]));//上午开始
		dayPartConditonMap.put("fne", Integer.parseInt(con[4]));//上午结束
		dayPartConditonMap.put("nb", Integer.parseInt(con[4]));//中午开始
		dayPartConditonMap.put("ne", Integer.parseInt(con[5]));//中午结束
		dayPartConditonMap.put("anb", Integer.parseInt(con[5]));//下午开始
		dayPartConditonMap.put("ane", Integer.parseInt(con[6]));//下午结束
		dayPartConditonMap.put("eb", Integer.parseInt(con[6]));//晚上开始
		dayPartConditonMap.put("ee", Integer.parseInt(con[7]));//晚上结束
		
		
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
