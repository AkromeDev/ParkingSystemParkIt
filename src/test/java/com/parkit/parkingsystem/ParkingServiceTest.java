package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

    private static ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;

    @BeforeEach
    private void setUpPerTest() {
        try {
//            when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");

            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
            Ticket ticket = new Ticket();
            ticket.setInTime(new Date(System.currentTimeMillis() - (60*60*1000)));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");
//            when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
//            when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
//            when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        }
    }
    
    @Test
    @DisplayName("tests if CAR is returned")
    public void getVehichleTypeCarTest(){
    	//ARRANGE
    	when(inputReaderUtil.readSelection()).thenReturn(1);
    	ParkingService parkingService1 = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    	
    	// ACT & ASSERT
        assertEquals(parkingService1.getVehichleType(), ParkingType.CAR);
    }
    
    @Test
    @DisplayName("tests if BIKE returned")
    public void getVehichleTypeBikeTest(){
    	//ARRANGE
    	when(inputReaderUtil.readSelection()).thenReturn(2);
    	ParkingService parkingService1 = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    	
    	// ACT & ASSERT
        assertEquals(parkingService1.getVehichleType(), ParkingType.BIKE);
    }
    
    @Test 
    @DisplayName("tests if IllegalArgumentException is thrown when invalid input")
    public void getVehichleTypeIncorrectInputTest(){
    	//ARRANGE
    	when(inputReaderUtil.readSelection()).thenReturn(10);
    	ParkingService parkingService1 = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    	
    	// ACT & ASSERT
    	assertThrows(IllegalArgumentException.class, () -> parkingService1.getVehichleType());
    }

    @Test 
    @DisplayName("tests if parking spot is returned correctly")
    public void getNextParkingNumberIfAvailable(){
    	//ARRANGE
    	when(inputReaderUtil.readSelection()).thenReturn(10);
    	ParkingService parkingService1 = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    	
    	// ACT & ASSERT
    	assertThrows(IllegalArgumentException.class, () -> parkingService1.getVehichleType());
    }

}
