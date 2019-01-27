package urjc.isi.pruebasSparkJava;

import java.net.URISyntaxException;

import spark.Request;
import spark.Response;

public class Comment {
	//Comentarios
	
	public String newComment(String text, int user, String film) {
		if (text.equals(null)) {
			throw new IllegalArgumentException("Comentario invalido");
		}else if (user<0) {
			throw new IllegalArgumentException("Usuario invalido");
		}else if (film.equals(null)) {
			throw new IllegalArgumentException("Pelicula invalida");
		}else {
		//Obtengo id de la pelicula
		//Almaceno el nuevo comentario
			try {
				Injector connector = new Injector("jdbc:sqlite:Database/IMDb.db");
				connector.insertUser(user);
				connector.insertComments(4, user, text);
				return "Comentario almacenado";
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				return e.getMessage();
			}
		}
	}
	
	//Devuelvo todos los comentarios que han hecho sobre una pelicula, con su usuario, para mostrar User: comment
	public String commentsFilm(String film){
		//Obtener el id con la función basica
		//Una funcion que me devuelva Un array de dos por dos con la info user, comen. 
		if (film.equals(null)) {
			throw new IllegalArgumentException("Pelicula invalida");
		}else{
			try {
				Injector connector = new Injector("jdbc:sqlite:Database/IMDb.db");
				
				String comments[][]=connector.userandcomments(film);
				
				String commentString=commentToString(comments);
				
				return commentString;
			} catch (URISyntaxException e) {
				return e.getMessage();
			}catch(Exception e) {
				return e.getMessage();
			}
				//DEvuelvo el error de la base de datos
		}

	}
	
	public String commentToString(String matrix_coment[][])
	{
		String text = "<u><b>Comentarios:</b></u><br>";
		for (int x = 0; x < matrix_coment.length; x++){
			String coments = " ";
			text += coments + matrix_coment[x][0] + ":" + matrix_coment[x][1]+"<br>";
		}
		text +=" ";
		return text;
	}
	
	//Guardo un nuevo comentario de un usuario hacia una pelicula
	public String postComment(Request request) {
		String comment=request.queryParams("comment");
		
		String user_string=request.queryParams("user");
		int user=Integer.parseInt(user_string);
		
		String film=request.queryParams("film");
		try {
			String result=newComment(comment, user, film);
			return result;
		}catch (IllegalArgumentException e) {
			return e.getMessage();
		}
	}
}

