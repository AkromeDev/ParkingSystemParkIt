package com.parkit.parkingsystem.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.config.DataBaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.function.BooleanSupplier;

public class InputReaderUtil {

    private static Scanner scan = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger("InputReaderUtil");

    public int readSelection() {
        try {
            int input = Integer.parseInt(scan.nextLine());
            return input;
        }catch(Exception e){
            logger.error("Error while reading user input from Shell", e);
            System.out.println("Error reading input. Please enter valid number for proceeding further");
            return -1;
        }
    }

    public String readVehicleRegistrationNumber() throws Exception {
        try {
            String vehicleRegNumber= scan.nextLine();
            if(vehicleRegNumber == null || vehicleRegNumber.trim().length()==0) {
                throw new IllegalArgumentException("Invalid input provided");
            }
            return vehicleRegNumber;
        }catch(Exception e){
            logger.error("Error while reading user input from Shell", e);
            System.out.println("Error reading input. Please enter a valid string for vehicle registration number");
            throw e;
        }
    }

	public Boolean readIfReturningUser(String vehicleRegNumber) throws ClassNotFoundException {
		
		Boolean isReturningUser = null;
		
		
		try {
				DataBaseConfig conn = new DataBaseConfig();
				Connection conn1 = conn.getConnection();
				Statement stmt = conn1.createStatement();
				String SQL = "SELECT VEHICLE_REG_NUMBER FROM ticket WHERE VEHICLE_REG_NUMBER ='"+vehicleRegNumber+"' AND OUT_TIME IS NOT NULL";
				
		        ResultSet r1= stmt.executeQuery(SQL);
		        
		        if (r1.next()) {
		        	isReturningUser = true;
		        	System.out.println("Welcome back! As a recurring user of our parking lot, you'll benefit from a 5% discount.");
					} else {
					isReturningUser = false;
				} 
	        
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		return isReturningUser; 
	}
	
	
	public Boolean readIfReturningUser2(String vehicleRegNumber) throws ClassNotFoundException {
		
		Boolean isReturningUser = null;

		try {
				DataBaseConfig conn = new DataBaseConfig();
				Connection conn1 = conn.getConnection();
				Statement stmt = conn1.createStatement();
				String SQL = "SELECT COUNT(VEHICLE_REG_NUMBER) FROM ticket WHERE VEHICLE_REG_NUMBER ='"+vehicleRegNumber+"' AND OUT_TIME IS NOT NULL";
				
		        ResultSet r1= stmt.executeQuery(SQL);
		        
		        int r2 = 0;
		        
		        while (r1.next())
		        {
		        	r2 = r1.getInt(1);
		        }
		        
		        
		        if (r2 > 0) {
						isReturningUser = true;
						System.out.println("Welcome back! As a recurring user of our parking lot, you'll benefit from a 5% discount.");
					} else {
						isReturningUser = false;
				}
	        
			} catch (SQLException ex) {
				ex.printStackTrace();
			} 
		return isReturningUser;
			
	}
			
}



