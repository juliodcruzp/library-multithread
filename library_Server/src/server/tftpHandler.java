package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import bst.BinarySearchTree;
import bookdata.*;
import lists.*;

public class tftpHandler extends Thread {
	
	// Data stream and output streams for data transfer
	private static 	BufferedReader 	clientInputStream;
    private static 	PrintWriter 	clientOutputStream;
    
    //Client socket for maintain connection with the client
    private 		Socket 			clientSocket;
    
    //Binary Tree that contains genres
  	static BinarySearchTree bSearchTree = new BinarySearchTree();

    /***************************************************************************
     * Constructor
     * @param clientSocket: client socket created when the client connects to
     * the server
     */
    public tftpHandler(Socket clientSocket)
    {
    	try{
    		//Assign to my local socket, the socket I receive.
    		this.clientSocket = clientSocket;
    		 
    		//Declaring reader
    	    clientInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //Equivalent a Data input stream
    	        
    	    //Declaring writer with autoflush
    	    clientOutputStream = new PrintWriter(clientSocket.getOutputStream(), true); //Equivalent a Data output stream
    	     
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}    
    }
    
    /***************************************************************************
     * Override Constructor
     * Task: overriding the run method to start the handler from the server.
     */
	@Override
	public void run(){
		dataTransfer();
	}
	
    /*****************************************************************************
     * Data transfer method
     * Tasks: Manage connection with the client.
     * 		Receive commands from the client
     * 		Invoke the appropriate method depending on the received command
     * 
     *****************************************************************************/
	public void dataTransfer() {
		try{
			//Useful variables
			int currentCommand = 0;
			String clientMessage = "";
			
			//Sending OK signal to client
			clientOutputStream.println(tftpCodes.OK);

			do{			
				//Reading client message
				clientMessage = clientInputStream.readLine();
	        	try{
	        		currentCommand = Integer.parseInt(clientMessage);
	        	} catch (Exception e){
	        		e.printStackTrace();
	        	}
				
	        	switch(currentCommand)
	        	{
	        		//Add genre command
	        		case tftpCodes.ADDGENRE: 			addGenre();	
	        											break;				
	        		//Get command
	        		case tftpCodes.ADDBOOK:				addBook();
	        											break;
	        		//Get command
	        		case tftpCodes.MODIFYABOOK:			modifyBook();
	        											break;
	        		//Get command
	        		case tftpCodes.LISTALLGENRE:		listAllGenre();
	        											break;	        			
	        		//Get command
	        		case tftpCodes.LISTALLBOOKBGENRE:	listAllBookBGenre();
														break;	        			
	        		//Get command
	        		case tftpCodes.LISTBOOKPARTGENRE:	listBookPartGenre();
														break;      			
	        		//Get command
	        		case tftpCodes.SEARCHFORABOOK:		SearchBook();
														break;	        													
					//Exit command
	        		case tftpCodes.CLOSECONNECTION:		closeConnection();
	        											break;
	        	}				
			} while(currentCommand != tftpCodes.CLOSECONNECTION);
			
		}catch (IOException e){
			e.printStackTrace();
		}

	}

	public synchronized void addGenre() {
		String clientMessage = "";
		boolean status;
		
		//Sending OK to client to receive arguments.
		clientOutputStream.println(tftpCodes.ADDGENRE);
		
		//Reading client message
    	try{
    		clientMessage = clientInputStream.readLine();
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	
		//Inserting into binary tree the item
		status = bSearchTree.insert(clientMessage);
		
		//Sending result to the client
		if (status == true)
			clientOutputStream.println(tftpCodes.OK);
		else
			clientOutputStream.println(tftpCodes.AEXISTITEM);
	}
	
	private synchronized void addBook()
	{
		String read = ""; //Input from client
		Book bookToInsert;
		int iterAuthors = 0;
		linkedList authors = new linkedList(); //List to add the authors
		boolean flag = false; //Flag for insert in the binary tree
		String title = "", genre = "", plot = "", publisher = "", release_year = "";
		
		clientOutputStream.println(tftpCodes.ADDBOOK);
		
   		try {
       		//Inputs from the client
   			genre = clientInputStream.readLine();     			
       		title = clientInputStream.readLine();
       		plot = clientInputStream.readLine();
       		publisher = clientInputStream.readLine();
       		release_year = clientInputStream.readLine();
       		
       		//Receiving number of authors of a book
       		read = clientInputStream.readLine();
       		
			try{
				iterAuthors = Integer.parseInt(read);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//Receiving authors
			for(int i = 0; i < iterAuthors; i++){
				String firstName = clientInputStream.readLine();
				String lastName = clientInputStream.readLine();
				authors.insertNewNode(firstName, lastName);
			}
   		}catch(Exception e){
			e.printStackTrace();
		}
   		
   		bookToInsert = new Book(title, genre, plot, release_year, publisher, authors);
		flag = bSearchTree.insert(genre);
		
		if(flag == false) {
			//If the genre already exist
			bSearchTree.getListOfGenre(genre).insert(bookToInsert);
		}
		else {
			//If the genre does not exist
			bSearchTree.getListOfGenre(genre).insert(bookToInsert);
		}
		
	}
	
	public synchronized void modifyBook() {
		
		String clientMessage = "";
		boolean flag = false;
		String titleFromClient = "";
		String genreFromClient = "";
		
		//Sending OK to the client. (feedback)
		if (bSearchTree.getSize() == 0) {
			clientOutputStream.println(tftpCodes.ISEMPTY);
			return;
		}
		else
			clientOutputStream.println(tftpCodes.MODIFYABOOK);
		
		//Reading client message
    	try{
    		clientMessage = clientInputStream.readLine();
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	
    	flag = bSearchTree.findTitleExist(clientMessage);
    	
    	if (flag == true) {
    		clientOutputStream.println(tftpCodes.OK);
    		
    		Book bookFounded = bSearchTree.findBookToModify(clientMessage);
    		
    		titleFromClient = bookFounded.getTitle();
    		clientOutputStream.println(titleFromClient);
    		clientOutputStream.println(bookFounded.getPlot());
    		genreFromClient = bookFounded.getGenre();
    		clientOutputStream.println(genreFromClient);
    		clientOutputStream.println(bookFounded.getPublisher());
    		clientOutputStream.println(bookFounded.getRelease_Year());
    		
    		//Authors develop to print
    		linkedList authors = bookFounded.getAuthors();
    		
    		//Defining size of the list authors and sending to client
    		int iterAuthors = authors.getSize();
    		clientOutputStream.println(iterAuthors);
    		
    		//Send authors name's
    		for (int i = 0; i < iterAuthors; i++) {
    			clientOutputStream.println(authors.getAuthorName(i));
    		}
    	}
    	else
    		clientOutputStream.println(tftpCodes.NOTFOUND);
    	
    	//Reading client message
    	try{
    		clientMessage = clientInputStream.readLine();
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	if (clientMessage.compareTo("y") == 0) {
    		
    		//Delete the book selected
    		DoublyLinkedList findingList = bSearchTree.getListOfGenre(genreFromClient);
    		findingList.deleteBook(titleFromClient);
    		
    		// ---------- ENTER LIKE A NEW BOOK ------------- //
    		
    		String read = ""; //Input from client
    		Book bookToInsert;
    		int iterAuthors = 0;
    		linkedList authors = new linkedList(); //List to add the authors
    		boolean flag2 = false; //Flag for insert in the binary tree
    		String title = "", genre = "", plot = "", publisher = "", release_year = "";
    		
       		try {
           		//Inputs from the client
       			genre = clientInputStream.readLine();     			
           		title = clientInputStream.readLine();
           		plot = clientInputStream.readLine();
           		publisher = clientInputStream.readLine();
           		release_year = clientInputStream.readLine();
           		
           		//Receiving number of authors of a book
           		read = clientInputStream.readLine();
           		
    			try{
    				iterAuthors = Integer.parseInt(read);
    			}catch(Exception e){
    				e.printStackTrace();
    			}
    			
    			//Receiving authors
    			for(int i = 0; i < iterAuthors; i++){
    				String firstName = clientInputStream.readLine();
    				String lastName = clientInputStream.readLine();
    				authors.insertNewNode(firstName, lastName);
    			}
       		}catch(Exception e){
    			e.printStackTrace();
    		}
       		
       		bookToInsert = new Book(title, genre, plot, release_year, publisher, authors);
    		flag2 = bSearchTree.insert(genre);
    		
    		if(flag2 == false) {
    			//If the genre already exist
    			bSearchTree.getListOfGenre(genre).insert(bookToInsert);
    		}
    		else {
    			//If the genre does not exist
    			bSearchTree.getListOfGenre(genre).insert(bookToInsert);
    		}
		}
		else
			return;
	}
	
	public synchronized void listAllGenre() {
		
		ArrayList<String> listOfGenres = new ArrayList<String>();
		
		//Sending OK to the client. (feedback)
		if (bSearchTree.getSize() == 0) {
			clientOutputStream.println(tftpCodes.ISEMPTY);
			return;
		}
		else
			clientOutputStream.println(tftpCodes.LISTALLGENRE);
		
		//List of genres in the binary tree and the size
		listOfGenres = bSearchTree.listOfGenres();
		int iterGenres = listOfGenres.size();
		
		//Sending size of genres to the client
		clientOutputStream.println(iterGenres);
		
		//For loop to send the genre and book
		for (int i = 0; i < iterGenres; i++) {
			//Sending genre to the client
			clientOutputStream.println(listOfGenres.get(i));
		}
	}
	
	public synchronized void listAllBookBGenre() {
		
		ArrayList<String> listOfGenres = new ArrayList<String>();
		
		//Sending OK to the client. (feedback)
		if (bSearchTree.getSize() == 0) {
			clientOutputStream.println(tftpCodes.ISEMPTY);
			return;
		}
		else
			clientOutputStream.println(tftpCodes.LISTALLBOOKBGENRE);
		
		//List of genres in the binary tree and the size
		listOfGenres = bSearchTree.listOfGenres();
		int iterGenres = listOfGenres.size();
		
		//Sending size of genres to the client
		clientOutputStream.println(iterGenres);
		
		//For loop to send the genre and book
		for (int i = 0; i < iterGenres; i++) {
			
			DoublyLinkedList listOfBooks = bSearchTree.getListOfGenre(listOfGenres.get(i));
			
			//Sending genre to the client
			clientOutputStream.println(listOfGenres.get(i));
			
			//Size of the list of book
			int iterBooks = listOfBooks.getSize();
			
			//Sending size of the list of book to the client
			clientOutputStream.println(iterBooks);
			
			for (int j = 0; j < iterBooks; j++) {
				clientOutputStream.println(listOfBooks.selectBook(j).getTitle());
				clientOutputStream.println(listOfBooks.selectBook(j).getRelease_Year());
				clientOutputStream.println(listOfBooks.selectBook(j).getPublisher());
			}
		}
	}
	
	public synchronized void listBookPartGenre() {
		
		String clientMessage = "";
		ArrayList<String> listOfGenres = new ArrayList<String>();
		
		//Sending OK to the client. (feedback)
		if (bSearchTree.getSize() == 0) {
			clientOutputStream.println(tftpCodes.ISEMPTY);
			return;
		}
		else
			clientOutputStream.println(tftpCodes.LISTBOOKPARTGENRE);
		
		// -------- PRINT ALL GENRES TO THE CLIENT FOR SELECTION -------- //
		//List of genres in the binary tree and the size
		listOfGenres = bSearchTree.listOfGenres();
		int iterGenres = listOfGenres.size();
		
		//Sending size of genres to the client
		clientOutputStream.println(iterGenres);
		
		//For loop to send the genre and book
		for (int i = 0; i < iterGenres; i++) {
			//Sending genre to the client
			clientOutputStream.println(listOfGenres.get(i));
		}
		
		// -------- PRINTING THE BOOKS OF THE SELECTED GENRE --------- //
		//Reading client message
    	try{
    		clientMessage = clientInputStream.readLine();
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	
    	//Give me the list of the genre enter by the client
    	DoublyLinkedList listOfBooks = bSearchTree.getListOfGenre(clientMessage);
    	
    	//Size of the list of book
		int iterBooks = listOfBooks.getSize();
		
		//Sending size of the list of book to the client
		clientOutputStream.println(iterBooks);
	
		for (int i = 0; i < iterBooks; i++) {
			clientOutputStream.println(listOfBooks.selectBook(i).getTitle());
			clientOutputStream.println(listOfBooks.selectBook(i).getRelease_Year());
			clientOutputStream.println(listOfBooks.selectBook(i).getPublisher());
		}   	
	}
	
	public synchronized void SearchBook() {
		
		String clientMessage = "";
		boolean flag = false;
		//Sending OK to the client. (feedback)
		if (bSearchTree.getSize() == 0) {
			clientOutputStream.println(tftpCodes.ISEMPTY);
			return;
		}
		else
			clientOutputStream.println(tftpCodes.SEARCHFORABOOK);
		
		//Reading client message
    	try{
    		clientMessage = clientInputStream.readLine();
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	
    	flag = bSearchTree.findTitleExist(clientMessage);
    	
    	if (flag == true) {
    		clientOutputStream.println(tftpCodes.OK);
    		
    		Book bookFounded = bSearchTree.findBookToModify(clientMessage);
    		clientOutputStream.println(bookFounded.getTitle());
    		clientOutputStream.println(bookFounded.getPlot());
    		clientOutputStream.println(bookFounded.getGenre());
    		clientOutputStream.println(bookFounded.getPublisher());
    		clientOutputStream.println(bookFounded.getRelease_Year());
    		
    		//Authors develop to print
    		linkedList authors = bookFounded.getAuthors();
    		
    		//Defining size of the list authors and sending to client
    		int iterAuthors = authors.getSize();
    		clientOutputStream.println(iterAuthors);
    		
    		//Send authors name's
    		for (int i = 0; i < iterAuthors; i++) {
    			clientOutputStream.println(authors.getAuthorName(i));
    		}
    	}
    	else
    		clientOutputStream.println(tftpCodes.NOTFOUND);
    	
	}
	
	public void closeConnection() {
		try{
			//Sending OK to client
			clientOutputStream.println(tftpCodes.CLOSECONNECTION);
			
			//Closing all intercommunication elements
			clientOutputStream.close();
			clientInputStream.close();
			clientSocket.close();
	        
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
}
