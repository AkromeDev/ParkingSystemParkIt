//package com.parkit.parkingsystem;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.Scanner;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.parkit.parkingsystem.util.InputReaderUtil;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class InputReaderUtilTest {
//
//	private static InputReaderUtil inputReader;
//	private static Scanner scan;
//	
//	@BeforeAll
//	static void setUpBeforeClass() throws Exception {
//		
//	}
//
//	@AfterAll
//	static void tearDownAfterClass() throws Exception {
//		scan.close();
//	}
//
//	@BeforeEach
//	void setUp() throws Exception {
//		inputReader = new InputReaderUtil();
//		scan = new Scanner(System.in);
//	}
//
//	@AfterEach
//	void tearDown() throws Exception {
//		scan.close();
//	}
//
//	@Test
//	@DisplayName("Well let's try")
//	public void readSelectionTest() {
//		when(Integer.parseInt(scan.nextLine())).thenReturn(1);
//		
//		int test = inputReader.readSelection();
//		
//		assertEquals(test, 1);
//	}
//
//}
