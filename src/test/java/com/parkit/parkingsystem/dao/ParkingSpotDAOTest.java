package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Date;


@ExtendWith(MockitoExtension.class)
public class ParkingSpotDAOTest {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static ParkingSpot parkingSpot;
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
        dataBasePrepareService.clearDataBaseEntries();
        
        parkingSpotDAO = new ParkingSpotDAO();
    }

    @AfterAll
    private static void tearDown(){
    	dataBasePrepareService.clearDataBaseEntries();
    }
    
    @Test
    @DisplayName("tests if the the first parkingspot is attributed when no car is in the parking")
    public void getNextAvailableSlotTest() throws Exception{
    	//ARRANGE
        ParkingType car = ParkingType.CAR;
        
        // ACT
        int num = parkingSpotDAO.getNextAvailableSlot(car);
        
        // ASSERT
        assertEquals(num, 1);
    }
    
    @Test
    @DisplayName("tests if the the second parkingspot is attributed when one car is already in the parking")
    public void getNextAvailableSlotTest2() throws Exception{
    	//ARRANGE
        ParkingType car = ParkingType.CAR;
        parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        
        // ACT
        parkingSpotDAO.updateParking(parkingSpot);
        int num = parkingSpotDAO.getNextAvailableSlot(car);
        
        // ASSERT
        assertEquals(num, 2);
    }
}
