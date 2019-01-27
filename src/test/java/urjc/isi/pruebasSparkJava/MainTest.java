package urjc.isi.pruebasSparkJava;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainTest {
	
	private static Connection connection;

	private String s;
	@Before //Set up - called before every test method.
	public void setUp()
	{
		connection = null;
    		try {
      			connection = DriverManager.getConnection("jdbc:sqlite:Database/IMDb.db");
    		} catch(SQLException e) {
	      		System.err.println(e.getMessage());
		}

	}

	@After // Tear Down - Called after every test method.
	public void tearDown()
	{
		try{
			if(connection != null){
        		connection.close();
      			}
    		} catch(SQLException e) {
        		System.err.println(e);
   	 	}
	}

	/*
	//Test1: insert
	@Test (expected = NullPointerException.class)
	public void testInsertNull()
	{
		Main.insert(connection, null, null);
	}
	*/

	//Test1: insertFilm
	@Test (expected = NullPointerException.class)
	public void testInsertNullMovie()
	{
		Main.insertFilm(connection, null, "2019", null);
	}

	//Test1: insertActor
	@Test (expected = NullPointerException.class)
	public void testInsertNullActor() throws SQLException
	{
		Main.insertActor(connection, null);
	}

	//Test1: insertWorks_In
	@Test (expected = NullPointerException.class)
  	public void testInsertNullWorks() throws SQLException
	{
    		Main.insertWorks_In(connection, null, null);
	}	
}
