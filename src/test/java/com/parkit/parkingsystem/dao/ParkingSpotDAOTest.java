package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.util.InputReaderUtil;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class ParkingSpotDAOTest {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static ParkingSpot parkingSpot;
    private static DataBasePrepareService dataBasePrepareService;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
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
    public void getNextAvailableSlotTest() {
    	//ARRANGE
        ParkingType car = ParkingType.CAR;
        
        // ACT
        int spot = parkingSpotDAO.getNextAvailableSlot(car);
        
        // ASSERT
        assertEquals(spot, 1);
    }
    
    @Test
    @DisplayName("tests if the the second parkingspot is attributed when one car is already in the parking")
    public void getNextAvailableSlotTest2() {
    	//ARRANGE
        ParkingType car = ParkingType.CAR;
        parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        
        // ACT
        parkingSpotDAO.updateParking(parkingSpot);
        int spot = parkingSpotDAO.getNextAvailableSlot(car);
        
        // ASSERT
        assertEquals(spot, 2);
    }
    
    @Test
    @DisplayName("tests if the the third parkingspot is attributed when two cars are already in the parking")
    public void getNextAvailableSlotTest3() {
    	//ARRANGE
        ParkingType car = ParkingType.CAR;
        parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ParkingSpot parkingSpot2 = new ParkingSpot(2, ParkingType.CAR, false);
        
        // ACT
        parkingSpotDAO.updateParking(parkingSpot);
        parkingSpotDAO.updateParking(parkingSpot2);
        int spot = parkingSpotDAO.getNextAvailableSlot(car);
        
        // ASSERT
        assertEquals(spot, 3);
    }
    
    @Test
    @DisplayName("tests if 0 is returned when no parkingspot is available")
    public void getNextAvailableSlotSadPathTest() {
    	//ARRANGE
        ParkingType car = ParkingType.CAR;
        parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ParkingSpot parkingSpot2 = new ParkingSpot(2, ParkingType.CAR, false);
        ParkingSpot parkingSpot3 = new ParkingSpot(3, ParkingType.CAR, false);
        
        // ACT
        parkingSpotDAO.updateParking(parkingSpot);
        parkingSpotDAO.updateParking(parkingSpot2);
        parkingSpotDAO.updateParking(parkingSpot3);
        
        int spot = parkingSpotDAO.getNextAvailableSlot(car);
        
        // ASSERT
        assertEquals(spot, 0);
    }
    
    @Test
    @DisplayName("Tests is update Ticket returns true when a valid parkingSpot is entered")
    public void updateParkingTest() {
    	//ARRANGE
        parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
        boolean updated = false;
        
        // ACT
        updated = parkingSpotDAO.updateParking(parkingSpot);
        
        // ASSERT
        assertTrue(updated);
    }
    
    @Test
    @DisplayName("Tests is update Ticket returns false when an invalid parkingSpot is entered")
    public void updateParkingTest2() {
    	//ARRANGE
        parkingSpot = new ParkingSpot(10, ParkingType.CAR, true);
        boolean updated = true;
        
        // ACT
        updated = parkingSpotDAO.updateParking(parkingSpot);
        
        // ASSERT
        assertFalse(updated);
    }
}
