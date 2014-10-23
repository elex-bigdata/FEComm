package com.elex.ssp;

public class Zone {
	
private String operator;
private int hour;
private int minute;

public Zone(String operator, int hour, int minute) {
super();
this.operator = operator;
this.minute = minute;
this.hour = hour;
}
public String getOperator() {
return operator;
}
public void setOperator(String operator) {
this.operator = operator;
}
public int getMinute() {
return minute;
}
public void setMinute(int minute) {
this.minute = minute;
}
public int getHour() {
return hour;
}
public void setHour(int hour) {
this.hour = hour;
}


}
