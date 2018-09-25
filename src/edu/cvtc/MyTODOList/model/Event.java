package edu.cvtc.MyTODOList;

public class Event 
{
	private int eventID;
	private String eventName;
	private String eventDate;
	private String eventTime;
	private boolean eventRecur;
	private String eventStart;
	private String eventEnd;
	private int eventPriority;
	private boolean eventLimited;
	private String eventCategory;
	private boolean eventReminder;
	private String eventReminderTime;
	

	private enum eventRecurFreq
	{
		
	}
	
	public int getEventID() 
	{
		return eventID;
	}


	public void setEventID(int eventID) 
	{
		this.eventID = eventID;
	}


	public String getEventName() 
	{
		return eventName;
	}


	public void setEventName(String eventName) 
	{
		this.eventName = eventName;
	}


	public String getEventDate() 
	{
		return eventDate;
	}


	public void setEventDate(String eventDate) 
	{
		this.eventDate = eventDate;
	}


	public String getEventTime() 
	{
		return eventTime;
	}


	public void setEventTime(String eventTime) 
	{
		this.eventTime = eventTime;
	}


	public boolean isEventRecur() 
	{
		return eventRecur;
	}


	public void setEventRecur(boolean eventRecur) 
	{
		this.eventRecur = eventRecur;
	}


	public String getEventStart() 
	{
		return eventStart;
	}


	public void setEventStart(String eventStart) 
	{
		this.eventStart = eventStart;
	}


	public String getEventEnd() 
	{
		return eventEnd;
	}


	public void setEventEnd(String eventEnd) 
	{
		this.eventEnd = eventEnd;
	}


	public int getEventPriority() 
	{
		return eventPriority;
	}


	public void setEventPriority(int eventPriority) 
	{
		this.eventPriority = eventPriority;
	}


	public boolean isEventLimited() 
	{
		return eventLimited;
	}


	public void setEventLimited(boolean eventLimited) 
	{
		this.eventLimited = eventLimited;
	}


	public String getEventCategory() 
	{
		return eventCategory;
	}


	public void setEventCategory(String eventCategory) 
	{
		this.eventCategory = eventCategory;
	}


	public boolean isEventReminder() 
	{
		return eventReminder;
	}


	public void setEventReminder(boolean eventReminder) 
	{
		this.eventReminder = eventReminder;
	}


	public String getEventReminderTime() 
	{
		return eventReminderTime;
	}


	public void setEventReminderTime(String eventReminderTime) 
	{
		this.eventReminderTime = eventReminderTime;
	}
	

}
