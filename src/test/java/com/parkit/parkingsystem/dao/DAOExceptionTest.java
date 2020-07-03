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

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
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
import java.util.Date;

import javax.swing.JOptionPane;

@ExtendWith(MockitoExtension.class)
public class DAOExceptionTest {
	

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
	    
	    @Test(expected = NullPointerException.class)
	    public void saveTicketThrowTest() throws ClassNotFoundException, SQLException {
	    	//ARRANGE
	        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
	        
	        // ACT
	        parkingService.processIncomingVehicle();
	        ticketDAO.getTicket("ABCDEG");
	    }
	    
	    @Test
	    public void saveTicketThrowTest2() {
	    	//ARRANGE
	    	ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
	        
	        // ACT
	        parkingService.processIncomingVehicle();
	        Ticket ticket = ticketDAO.getTicket("ABCDEG");
	    	ticket.setId(1);
	    	
	     
	        //ACT & ASSERT
	        assertThrows(NullPointerException.class, () -> ticketDAO.saveTicket(ticket));
	    }
	    
	    @Test
	    public void saveTicketThrowTest3() {
	    	//ARRANGE
	    	ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
	        
	        // ACT
	        parkingService.processIncomingVehicle();
	        Ticket ticket = ticketDAO.getTicket("ABCDEG");
	        String exTest = "Error fetching next available slot 1";
	        // ACT
	        try {
	        	ticket.setId(-1);	
	        	ticketDAO.saveTicket(ticket);
	        	
	        	fail();
	        	
	        } catch (Exception ex) {
	        	ex.fillInStackTrace();
	        	assertSame(exTest, ex.getMessage());
	        }
	        
	    }
	}
