package edu.cvtc.MyTODOList.model;

public class Event 
{
	protected int eventID;
	protected String eventName;
	protected String eventDesc;
	protected String eventDate;
	protected String eventTime;
	protected boolean eventRecur;
	protected EventRecurFreq eventFrequency;
	protected String eventStart;
	protected String eventEnd;
	protected int eventPriority;
	protected boolean eventLimited = false;
	protected String eventCategory = "None";
	protected boolean eventReminder;
	protected String eventReminderTime;
	
	public enum EventRecurFreq
	{
		DAILY, WEEKLY, BIWEEKLY, MONTHLY, ANNUALLY
	}
	
	@Override
	public String toString() {
		return this.getEventTime() + " - " + this.getEventName();
	}
	
	public String eventDetails() {
		return "Event: " + this.getEventName() + "\n"
				+ "Event Description: " + this.getEventDesc() + "\n"
				+ "Event Date: " + this.getEventDate() + "\n"
				+ "Event Time: " + this.getEventTime() + "\n"
				+ "Priority: " + this.getEventPriority() + "\n"
				+ "Category: " + this.getEventCategory();
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
	
	public String getEventDesc()
	{
		return eventDesc;
	}

	public void setEventDesc(String eventDesc)
	{
		this.eventDesc = eventDesc;
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

	public EventRecurFreq getEventFrequency()
	{
		return this.eventFrequency;
	}

	public void setEventFrequency(EventRecurFreq freq)
	{
		this.eventFrequency = freq;
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
