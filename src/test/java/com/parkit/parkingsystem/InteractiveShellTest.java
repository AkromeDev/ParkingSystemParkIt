package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;


import static org.hamcrest.CoreMatchers.containsString;

public class InteractiveShellTest {

	InteractiveShell interactiveShell;
	
    @BeforeEach
    private void setUpPerTest() {
    	interactiveShell = new InteractiveShell();
    }
    
    @Test 
    @DisplayName("tests if the right message is displayed when enteing the app and leaving it right away")
    public void loadInterfaceTest() throws IOException{
    	//ARRANGE
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        //ACT 
        String input = "3\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        
        InteractiveShell.loadInterface();
        
        String out = null;
		try {
			out = outContent.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        // ASSERT
        Assert.assertThat(out, containsString("Please select an option. Simply enter the number to choose an action"));
        Assert.assertThat(out, containsString("3 Shutdown System"));
    }
}