package com.test;

public class SumTime{
	
	public enum TypeEnum{
		time,//只有startTime和endTime
		day,//只有startDay和endDay
		timeDay,//有startTime，endTime，startDay和endDay
		timeDayTime//全部时间都有
	}
	
	private TypeEnum type;
	private String startTime;
	private String endTime;
	private String startDay;
	private String endDay;
	private String otherStartTime;
	private String otherEndTime;
	public TypeEnum getType() {
		return type;
	}
	public void setType(TypeEnum type) {
		this.type = type;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	public String getOtherStartTime() {
		return otherStartTime;
	}
	public void setOtherStartTime(String otherStartTime) {
		this.otherStartTime = otherStartTime;
	}
	public String getOtherEndTime() {
		return otherEndTime;
	}
	public void setOtherEndTime(String otherEndTime) {
		this.otherEndTime = otherEndTime;
	}
	@Override
	public String toString() {
		return "SumTime [type=" + type + ", startTime=" + startTime + ", endTime=" + endTime + ", startDay=" + startDay + ", endDay=" + endDay + ", otherStartTime=" + otherStartTime + ", otherEndTime=" + otherEndTime + "]";
	}
}
