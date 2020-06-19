package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket) throws ClassNotFoundException{
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        long inHour = ticket.getInTime().getTime();
        long outHour = ticket.getOutTime().getTime();
        
        double durationMilliSeconds = outHour - inHour;
        double durationHours = durationMilliSeconds / (1000 * 60 * 60);
        
        boolean isReturningUser = ticket.getIsReturningUser();
        
        if (durationHours < 0.5) {
        	ticket.setPrice(0);

        } else if (isReturningUser = true){
        	calculateFareForReturningUser(ticket, durationHours);
        	
        } else {
	        switch (ticket.getParkingSpot().getParkingType()){
	            case CAR: {
	                ticket.setPrice(durationHours * Fare.CAR_RATE_PER_HOUR);
	                break;
	            }
	            case BIKE: {
	                ticket.setPrice(durationHours * Fare.BIKE_RATE_PER_HOUR);
	                break;
	            }
	            default: throw new IllegalArgumentException("Unkown Parking Type");
	        }
        }
    }

	public void calculateFareForReturningUser(Ticket ticket, double durationHours) {

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(durationHours * Fare.CAR_RATE_PER_HOUR * 0.95);
                break;
            }
            case BIKE: {
                ticket.setPrice(durationHours * Fare.BIKE_RATE_PER_HOUR * 0.95);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
	}
	
}