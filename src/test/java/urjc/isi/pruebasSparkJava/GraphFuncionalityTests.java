package urjc.isi.pruebasSparkJava;

import static org.junit.Assert.*;
import org.junit.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class GraphFuncionalityTests {

        private String name1,name2;
        private Graph g;
        private Connection connection;

        @Before      // Set up - Called before every test method.
        public void setUp()
        {
        	g = new Graph("Database/film_actors.txt", "/");
        	name1 = "Hugh Jackman";
        	name2 = "Scarlett Johansson";
    		try {
      			connection = DriverManager.getConnection("jdbc:sqlite:Database/IMDb.db");
    		} catch(SQLException e) {
    			e.printStackTrace();
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
        			e.printStackTrace();
       	 	}
    	}

        //Test para comprobar que al crear Graph con filename=null se eleva excepción
    	@Test (expected = IllegalArgumentException.class) 
    	public void testForEmptyGraph()
    	{
    		String filename = null;
    		g = new Graph(filename, "/");
    	}
    	
    	//Test para comprobar que al crear Graph con delimiter=null se eleva excepción
    	@Test (expected = IllegalArgumentException.class)
    	public void testForNoDelimiter()
    	{
    		g = new Graph("Database/film_actors.txt", null);
    	}
    	
    	//Test para comprobar que al tener Graph con vértices = 0 (fichero vacío) se eleva la excepción
    	@Test (expected = NullPointerException.class) 
    	public void testForSizeZeroGraph() 
    	{
    		g = new Graph ("Database/empty_test.txt", "/");
    		GraphFuncionality.doDistance(g, "Mario Bross", "Pacman");
    	}
    	
    	//Test para comprobar que al no existir fichero se eleva la excepción
    	@Test (expected = IllegalArgumentException.class) 
    	public void testForNoFile() 
    	{
    		g = new Graph ("Database/invalid_file.txt", "/");
    	}
    	
    	
    	//Test para comprobar que al intentar añadir el String "" en "ST<String, Integer> dist" --> exception
    	//Es casi imposible que ocurra, ya que comprobamos al inicio del metodo doDistance si name1 es "".
    	//Solo podría ocurrir si nos "cargamos" name1 en mitad del método, sea la razón que sea.
    	//Aquí name1 hace referencia a PathFinder(g,name1).
    	@Test (expected = IllegalArgumentException.class)
    	public void testForIncompatibleTypes()
    	{
    	PathFinder pf = new PathFinder(g, "");
    	}
    	
    	//Test para comprobar que salta la excepcion si no introducimos connection correcto (cerrado)
    	@Test (expected = IllegalArgumentException.class) 
    	public void testForCloseConnection()
    	{
    		try{
    			connection.close();
        	} catch(SQLException e) {
        		e.printStackTrace();
       	 	}
    		GraphFuncionality.nameChecker(connection, "");
    	}

    	//Test para comprobar que salta la excepcion si por lo que sea a namechecker le llega 
    	//un name="" (inválido)
    	@Test (expected = IllegalArgumentException.class) 
    	public void testForInvalidName()
    	{
    		GraphFuncionality.nameChecker(connection, "");
    	}
    	
    	
    	//HAPPY PATHS --------------------------------------
    	
    	//Test para comprobar que se crea un grafo correctamente si filename y delimiter son correctos
    	@Test
    	public void validGraph()
    	{
    	assertTrue ("Not valid graph", g.V() >= 1);
    	}

    	//Test para comprobar que se crea un pathfinder correcto (con ruta) dado dos nombres relacionados
    	@Test
    	public void validPathFinder()
    	{
    	PathFinder pf = new PathFinder(g, name1);
    	
    	assertTrue ("Not valid pathfinder (names)", pf.hasPathTo(name2));
    	}
    	
    	//Test para comprobar si namechecker retorna un ArrayList válido dado un nombre valido
    	@Test
    	public void validNameChecker()
    	{
    	ArrayList<String> result = new ArrayList<String>();
    	result = GraphFuncionality.nameChecker(connection,name1);
    	assertTrue ("Not valid name", result.size() >= 1);
    	}
    	
    	
    	
    	//TEST PARA NO NAMES NO HACEN FALTA CREO, YA QUE SON CHECKED EXCEPTIONS (SE CAZAN CON CATCH)
    	//Test para comprobar que salta la excepcion si no introducimos uno de los dos nombres.
    	//Válido también para cuando no se introducen los dos nombres --> doDistance
//    	@Test (expected = IllegalArgumentException.class) 
//    	public void testForNoNames()
//    	{
//    		String aux = GraphFuncionality.doDistance(g, "Mario Bross", "");
//    	}
//
//    	//Test para comprobar que salta la excepcion si no introducimos nombre  --> doGraphFilter
//    	@Test (expected = IllegalArgumentException.class) 
//    	public void testForNoNames2()
//    	{
//    		GraphFuncionality.doGraphFilter(g,"");
//    	}
//    	
//    	//Test para comprobar que salta la excepcion si no introducimos número --> doRanking 
//    	@Test (expected = IllegalArgumentException.class) 
//    	public void testForNoNames3()
//    	{
//    		GraphFuncionality.doRanking(g, "");
//    	}
}

