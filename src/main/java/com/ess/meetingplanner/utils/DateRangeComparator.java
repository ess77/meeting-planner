package com.ess.meetingplanner.utils;

import java.time.LocalDateTime;
import java.util.Date;

public class DateRangeComparator {

	    private Date startDate;
	    private Date endDate;
	    
	    private LocalDateTime startLocalDateTime;
	    private LocalDateTime endLocalDateTime;

	    public DateRangeComparator(Date startDate, Date endDate) {
	        this.startDate = startDate;
	        this.endDate = endDate;
	    }
	    
	    public DateRangeComparator(LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {
	    	this.startLocalDateTime = startLocalDateTime;
	    	this.endLocalDateTime = endLocalDateTime;
	    }

	    public boolean isWithinRangeDate(Date testDate) {
	        return testDate.getTime() >= startDate.getTime() &&
	                testDate.getTime() <= endDate.getTime();
	    }
	    
	    public boolean isWithinRangeLocalDateTime(LocalDateTime testLocalDateTime) {
	    	if(testLocalDateTime == null) return false;
	    	if(startLocalDateTime == null || endLocalDateTime == null ) return false;
	    	return  (testLocalDateTime.isEqual(startLocalDateTime) || testLocalDateTime.isEqual(endLocalDateTime)) ? true :  testLocalDateTime.isAfter(startLocalDateTime) &&	testLocalDateTime.isBefore(endLocalDateTime);
	    }

}
