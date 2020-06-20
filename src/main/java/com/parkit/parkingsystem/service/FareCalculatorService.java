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
        
        if (durationHours < 0.5) {
        	ticket.setPrice(0);

        } else {
        	calculateFareForUser(ticket, durationHours);
        }
    }

	public void calculateFareForUser(Ticket ticket, double durationHours) {

		boolean isReturningUser = ticket.getIsReturningUser();
		
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(durationHours * Fare.CAR_RATE_PER_HOUR * (isReturningUser ? 0.95 : 1));
                break;
            }
            case BIKE: {
                ticket.setPrice(durationHours * Fare.BIKE_RATE_PER_HOUR * (isReturningUser ? 0.95 : 1));
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
	}
	
}