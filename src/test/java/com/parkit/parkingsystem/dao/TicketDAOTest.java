package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

import junit.framework.Assert;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

@ExtendWith(MockitoExtension.class)
public class TicketDAOTest {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown(){
    	dataBasePrepareService.clearDataBaseEntries();
    }
    
    @Test
    @DisplayName("Tests the getTicket sets the values properly")
    public void getTicketTest() throws Exception{
    	//ARRANGE
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        
        // ACT
        parkingService.processIncomingVehicle();
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        
        // ASSERT
        assertEquals(ticket.getVehicleRegNumber(), "ABCDEF");
        assertEquals(ticket.getIsReturningUser(), false);
        assertEquals(ticket.getOutTime(), null);
        assertEquals(ticket.getPrice(), 0);
        assertNotNull(ticket.getInTime());
    }
    
    @Test
    @DisplayName("Tests updateTicket updates the values properly")
    public void updateTicketTest() throws Exception{
    	//ARRANGE
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        
        // ACT
        parkingService.processIncomingVehicle();
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        
        Date instant = new Date(System.currentTimeMillis());
        
        ticket.setPrice(1400);
        ticket.setOutTime(instant);
        
        ticketDAO.updateTicket(ticket);
        
        
        Ticket ticketUpdated = ticketDAO.getTicket("ABCDEF");
        // ASSERT
        assertNotNull(ticketUpdated.getOutTime());
        assertEquals(ticketUpdated.getPrice(), 1400);
    }
//    @Test
//    @DisplayName("Tests if the parking spot availability is set to false")
//    public void testParkingTableAvailability() throws Exception{
//    	//ARRANGE
//        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
//        
//        // ACT
//        parkingService.processIncomingVehicle();
//        Ticket ticket = ticketDAO.getTicket("ABCDEF");
//
//        // ASSERT
//        assertFalse(ticket.getParkingSpot().isAvailable());
//    }
//    
//    @Test
//    @DisplayName("Tests if a price is set in the DB")
//    public void testParkingLotExitPrice() throws ClassNotFoundException{
//    	//ARRANGE
//        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
//        
//        //ACT 
//        parkingService.processIncomingVehicle();
//        parkingService.processExitingVehicle();
//        
//        Ticket ticket = ticketDAO.getTicket("ABCDEF");
//        // ASSERT
//        
//        assertNotNull(ticket.getPrice());
//    }
//
//    @Test
//    @DisplayName("Tests if an OutTime is set in the DB")
//    public void testParkingLotExitOutTime() throws ClassNotFoundException{
//    	//ARRANGE
//        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
//        
//        //ACT 
//        parkingService.processIncomingVehicle();
//        
//        try {
//			Thread.sleep(200);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//        
//        parkingService.processExitingVehicle();
//
//        Ticket ticket = ticketDAO.getTicket("ABCDEF");
//        
//        // ASSERT
//        assertNotNull(ticket.getOutTime());
//        
//        //TODO: check if everything is ok, this test fails 30% of the time - This is a big To do This also affects the following tests
//    }
//    
//    @Test
//    @DisplayName("Tests #1 if the system recognizes a returning user")
//    public void testInputReaderUtilReturningUser() throws ClassNotFoundException{
//    	//ARRANGE
//        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
//        
//        //ACT 
//        parkingService.processIncomingVehicle();
//        parkingService.processExitingVehicle();
//        
//        parkingService.processIncomingVehicle();
//        parkingService.processExitingVehicle();
//        
//        parkingService.processIncomingVehicle();
//        
//        // ASSERT
//        assertTrue(parkingService.getIfReturningUser("ABCDEF"));
//    }
//
}
