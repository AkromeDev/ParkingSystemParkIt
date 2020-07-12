package com.parkit.parkingsystem.integration.config;

import com.parkit.parkingsystem.config.DataBaseConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DataBaseTestConfig extends DataBaseConfig {

    private static final Logger logger = LogManager.getLogger("DataBaseTestConfig");

    public Connection getConnection() {
        logger.info("Create DB connection");
        
        Connection myConn = null;
        
        try {
        	// load Properties file
        	Properties props = new Properties();
        	props.load(new FileInputStream("src/main/resources/config.properties"));
        	
        	// read the properties File
        	String url = props.getProperty("db.url");
        	String user = props.getProperty("db.user");
        	String password = props.getProperty("db.password");
        	
        	myConn = DriverManager.getConnection(url, user, password);
        	
        } catch (IOException ex){
        	logger.error("Could not create a connection with the database IOException");
        	System.err.println(ex);
        } catch (SQLException ex) {
        	logger.error("Could not create a connection with the database getConnection()");
        	System.err.println(ex);
        }
        
        return myConn;
    }

    public void closeConnection(Connection con){
        if(con!=null){
            try {
                con.close();
                logger.info("Closing DB connection");
            } catch (SQLException e) {
                logger.error("Error while closing connection",e);
            }
        }
    }

    public void closePreparedStatement(PreparedStatement ps) {
        if(ps!=null){
            try {
                ps.close();
                logger.info("Closing Prepared Statement");
            } catch (SQLException e) {
                logger.error("Error while closing prepared statement",e);
            }
        }
    }

    public void closeResultSet(ResultSet rs) {
        if(rs!=null){
            try {
                rs.close();
                logger.info("Closing Result Set");
            } catch (SQLException e) {
                logger.error("Error while closing result set",e);
            }
        }
    }
}
