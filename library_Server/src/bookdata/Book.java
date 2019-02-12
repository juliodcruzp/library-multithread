package bookdata;
import lists.*;

public class Book{

	//Variable declaration
	String title, genre, plot, release_year, publisher;
	linkedList authors = new linkedList();

	//Constructor
	public Book (String titleR, String genreR, String plotR, String release_yearR, String publisherR, linkedList authorR){
		title = titleR;
		genre = genreR;
		plot = plotR;
		publisher = publisherR;
		release_year = release_yearR;
		authors = authorR;
	}

	public String getTitle(){
		return title;
	}

	public String getGenre(){
		return genre;
	}

	public String getPlot(){
		return plot;
	}

	public String getRelease_Year(){
		return release_year;
	}

	public linkedList getAuthors(){
		return authors;
	}

	public String getPublisher(){
		return publisher;
	}
	
	public void setTitle(String T){
		title = T;
	}
	
	public void setPublisher(String P){
		publisher = P;
	}
	
	public void setGenre(String G){
		genre = G;
	}

	public void setPlot(String P){
		plot = P;
	}

	public void setYear(String R_Y){
		release_year = R_Y;
	}
	
	public void setAuthor(String firstN, String lastN){
		authors.insertNewNode(firstN, lastN);
	}
}