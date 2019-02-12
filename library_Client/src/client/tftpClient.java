package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class tftpClient {

	public static void main(String args[]) throws Exception
	{
		//Server port
        int serverPort = 50008;
        
        //Useful Variables
        String serverReached = ""; 			//Variable for inform the reach of the server
        int currentCommand = 0;
        String response = "";				//Response from the server
        String arguments = "";     			//Arguments from user
        
        //Scanner for reading commands from the keyboard 
        Scanner reader = new Scanner(System.in);
        
        //Menu String
      	String menu = "\n-- LIBRARY MENU -- \n" +
      				  "(Select one option by enter the number) \n" +
      				  "1. Add a genre \n" +
      				  "2. Add a book \n" +
      				  "3. Modify a book \n" +
      				  "4. List all genres \n" +
      				  "5. List all book by genre \n" +
      				  "6. List all book for a particular genre \n" +
      				  "7. Search for a book \n" +
      				  "8. Exit \n" +
      				  "------->> ";
              
        //Create a client socket and connect to the Server
        System.out.println("Connecting to server through the port: " + serverPort);
        Socket socket = new Socket(InetAddress.getLocalHost(), serverPort);
        
        //Reader
        BufferedReader socketInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Equivalent a Data input stream
        
        //Writer with autoflush
        PrintWriter socketOutputStream = new PrintWriter(socket.getOutputStream(), true); //Equivalent a Data output stream
        
        //Reading server response
       	serverReached = socketInputStream.readLine();
    	try{
    		currentCommand = Integer.parseInt(serverReached);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
        
        while(currentCommand != tftpCodes.OK){
        	
        	System.out.println("Waiting for server response..... \n");        	
        }
        System.out.println("Connected to the server \n");
        //Reach the server connection!
           
		try {  
	        do{
	        	//Present the menu to the user
	        	System.out.println(menu);
	        	arguments = reader.nextLine();
	        	socketOutputStream.println(arguments);
	        	
	        	//Reading server response
	           	response = socketInputStream.readLine();
	        	try{
	        		currentCommand = Integer.parseInt(response);
	        	} catch (Exception e){
	        		e.printStackTrace();
	        	}
	        	
	        	switch(currentCommand)
	        	{
	        		//Add genre
	        		case tftpCodes.ADDGENRE:
        			{
        				//Asking genre from the user.
        				System.out.print("Enter the genre: ");
        				arguments = reader.nextLine();
        			
        				//Sending genre to server
        				socketOutputStream.println(arguments);
        			
        				//Wait for OK code - Reading server response
        				response = socketInputStream.readLine();
        				try{
        					currentCommand = Integer.parseInt(response);
        				} catch (Exception e){
        					e.printStackTrace();
        				}
    	        	
        				//Shown the response from the server.
        				if(currentCommand == tftpCodes.OK)
        					System.out.print(arguments + " was added sucesfully!. \n");
        				else if (currentCommand == tftpCodes.AEXISTITEM)
        					System.out.print("The item already exist!. \n");
        				else
        					System.out.print("Error trying adding this item. \n");
        				break;	  
        			}		
        			
	        		//Add book
	        		case tftpCodes.ADDBOOK:				
	        		{
	        			int iterAuthors = 0;
	        			
	        			//Asking genre
        				System.out.print("Enter the genre: ");
        				arguments = reader.nextLine();
        				socketOutputStream.println(arguments); //Sending genre to server
        				
        				//Asking title
        				System.out.print("Enter the title: ");
        				arguments = reader.nextLine();
        				socketOutputStream.println(arguments); //Sending genre to server
        				
        				//Asking plot
        				System.out.print("Enter the plot: ");
        				arguments = reader.nextLine();
        				socketOutputStream.println(arguments); //Sending genre to server
        				
        				//Asking publisher
        				System.out.print("Enter the publisher: ");
        				arguments = reader.nextLine();
        				socketOutputStream.println(arguments); //Sending genre to server
        				
        				//Asking release_year
        				System.out.print("Enter the year of release: ");
        				arguments = reader.nextLine();
        				socketOutputStream.println(arguments); //Sending genre to server
        			    
        				//Asking release_year
        				System.out.print("How many authors have the book?: ");
        				arguments = reader.nextLine();
        				socketOutputStream.println(arguments); //Sending genre to server
        				
        				try{
        					iterAuthors = Integer.parseInt(arguments);
        				}catch(Exception e){
        					e.printStackTrace();
        				}
        				
        				for(int i = 0; i < iterAuthors; i++) {
        					System.out.println("Author " + (i + 1));
        					
        					System.out.print("First Name: ");
            				arguments = reader.nextLine();
            				socketOutputStream.println(arguments); //Sending genre to server
            				
            				System.out.print("Last Name: ");
            				arguments = reader.nextLine();
            				socketOutputStream.println(arguments); //Sending genre to server
        				}
        				break;
	        		}
	        		//Modify book
	        		case tftpCodes.MODIFYABOOK:			
	        		{
	        			int command = 0;
	        			
	        			//Asking the book from the user
        				System.out.print("Enter the book you want to find: ");
        				arguments = reader.nextLine();
        			
        				//Sending genre to server
        				socketOutputStream.println(arguments);
        				
        				//Receiving size of the binary tree.
        				response = socketInputStream.readLine();
        				try{
        					command = Integer.parseInt(response);
        				} catch (Exception e){
        					e.printStackTrace();
        				}
        				if (command == tftpCodes.OK) {
        					
        					int iterAuthors = 0;
        					
        					//Print the title
        					System.out.println("\n\t\t Movie: " + socketInputStream.readLine());
        					
        					//Print the plot
        					System.out.println("\n\t\t\t Plot: " + socketInputStream.readLine());
        					
        					//Print the genre
        					System.out.println("\n\t\t\t Genre: " + socketInputStream.readLine());
        					
        					//Print the publisher
        					System.out.println("\n\t\t Publisher: " + socketInputStream.readLine());
        					
        					//Print the Release year
        					System.out.println("\n\t\t\t Year of Release: " + socketInputStream.readLine());
        					       					        					
        					//Receiving variable for iteration.
            				response = socketInputStream.readLine();
            				try{
            					iterAuthors = Integer.parseInt(response);
            				} catch (Exception e){
            					e.printStackTrace();
            				}
            				
            				for(int i = 0; i < iterAuthors; i++) {
            					//Print the authors
            					System.out.println("\n\t\t\t Authors: " + socketInputStream.readLine());
            				}
        				}
        				
        				//Asking the book from the user
        				System.out.print("Do you really want to modify that book? (y/n) ");       			
        				arguments = reader.nextLine();
        				if (arguments.compareTo("y") == 0) {
        					
        					socketOutputStream.println(arguments);
        					
        					int iterAuthors = 0;
            				
            				//Asking genre
            				System.out.print("Enter the genre: ");
            				arguments = reader.nextLine();
            				socketOutputStream.println(arguments); //Sending genre to server
            				
            				//Asking title
            				System.out.print("Enter the title: ");
            				arguments = reader.nextLine();
            				socketOutputStream.println(arguments); //Sending genre to server
            				
            				//Asking plot
            				System.out.print("Enter the plot: ");
            				arguments = reader.nextLine();
            				socketOutputStream.println(arguments); //Sending genre to server
            				
            				//Asking publisher
            				System.out.print("Enter the publisher: ");
            				arguments = reader.nextLine();
            				socketOutputStream.println(arguments); //Sending genre to server
            				
            				//Asking release_year
            				System.out.print("Enter the year of release: ");
            				arguments = reader.nextLine();
            				socketOutputStream.println(arguments); //Sending genre to server
            			    
            				//Asking release_year
            				System.out.print("How many authors have the book?: ");
            				arguments = reader.nextLine();
            				socketOutputStream.println(arguments); //Sending genre to server
            				
            				try{
            					iterAuthors = Integer.parseInt(arguments);
            				}catch(Exception e){
            					e.printStackTrace();
            				}
            				
            				for(int i = 0; i < iterAuthors; i++) {
            					System.out.println("Author " + (i + 1));
            					
            					System.out.print("First Name: ");
                				arguments = reader.nextLine();
                				socketOutputStream.println(arguments); //Sending genre to server
                				
                				System.out.print("Last Name: ");
                				arguments = reader.nextLine();
                				socketOutputStream.println(arguments); //Sending genre to server
            				}
            				
        				}
        				else {
        					socketOutputStream.println(arguments);
        					break;
        				}	
        				break;
	        		}
	        		//List all genre
	        		case tftpCodes.LISTALLGENRE:		
        			{	
        		        int iterGenres = 0;
        		        
        		        //Receiving variable for iteration.
        				response = socketInputStream.readLine();
        				try{
        					iterGenres = Integer.parseInt(response);
        				} catch (Exception e){
        					e.printStackTrace();
        				}
        				
        				//For loop to receive the genre and book
        				for (int i = 0; i < iterGenres; i++) {
        					//Print the genre
        					System.out.println("\n" + (i + 1) + "\t " + socketInputStream.readLine());				
        				}
        				break;
        			}
        			
	        		//List all books by genre
	        		case tftpCodes.LISTALLBOOKBGENRE:
        			{	
        		        int iterGenres = 0;
        		        int iterBooks = 0;
        		        
        		        //Receiving variable for iteration.
        				response = socketInputStream.readLine();
        				try{
        					iterGenres = Integer.parseInt(response);
        				} catch (Exception e){
        					e.printStackTrace();
        				}
        				
        				//For loop to receive the genre and book
        				for (int i = 0; i < iterGenres; i++) {
        					
        					//Print the genre
        					System.out.println("\n" + (i + 1) + "\t " + socketInputStream.readLine());
        					
        					//Receiving size of the list of books
	        				response = socketInputStream.readLine();
	        				try{
	        					iterBooks = Integer.parseInt(response);
	        				} catch (Exception e){
	        					e.printStackTrace();
	        				}   					
        					for (int j = 0; j < iterBooks; j++) {
        						
        						//Print the title
	        					System.out.println("\n\t Movie: " + socketInputStream.readLine());
	        					
	        					//Print the Release year
	        					System.out.println("\n\t\t Year of Release: " + socketInputStream.readLine());
	        					
	        					//Print the publisher
	        					System.out.println("\n\t\t Publisher: " + socketInputStream.readLine());
        					}
        				}
        				break;
        			}
        			
	        		//List all book for a particular genre
	        		case tftpCodes.LISTBOOKPARTGENRE:
	        		{
	        			int iterGenres = 0;
        		        int iterBooks = 0;
        		        
        		        //Receiving variable for iteration.
        				response = socketInputStream.readLine();
        				try{
        					iterGenres = Integer.parseInt(response);
        				} catch (Exception e){
        					e.printStackTrace();
        				}
        				
        				// -------- PRINT ALL GENRES FOR SELECTION -------- //
        				//For loop to receive the genre and book
        				for (int i = 0; i < iterGenres; i++) {
        					//Print the genre
        					System.out.println("\n" + (i + 1) + "\t " + socketInputStream.readLine());				
        				}
        				
        				// -------- PRINTING THE BOOKS OF THE SELECTED GENRE -------- //
        				//Asking genre from the user.
        				System.out.print("Enter the genre: ");
        				arguments = reader.nextLine();
        			
        				//Sending genre to server
        				socketOutputStream.println(arguments);
        				
        				//Receiving size of the list of books
        				response = socketInputStream.readLine();
        				try{
        					iterBooks = Integer.parseInt(response);
        				} catch (Exception e){
        					e.printStackTrace();
        				}   	
        				
        				for (int j = 0; j < iterBooks; j++) {
    						//Print the title
        					System.out.println("\n\t Movie: " + socketInputStream.readLine());
        					
        					//Print the Release year
        					System.out.println("\n\t\t Year of Release: " + socketInputStream.readLine());
        					
        					//Print the publisher
        					System.out.println("\n\t\t Publisher: " + socketInputStream.readLine());
    					}
        				break;
	        		}
	        		//Get command
	        		case tftpCodes.SEARCHFORABOOK: 
	        		{	
	        			int command = 0;
	        			
	        			//Asking the book from the user
        				System.out.print("Enter the book you want to find: ");
        				arguments = reader.nextLine();
        			
        				//Sending genre to server
        				socketOutputStream.println(arguments);
        				
        				//Receiving size of the binary tree.
        				response = socketInputStream.readLine();
        				try{
        					command = Integer.parseInt(response);
        				} catch (Exception e){
        					e.printStackTrace();
        				}
        				if (command == tftpCodes.OK) {
        					
        					int iterAuthors = 0;
        					
        					//Print the title
        					System.out.println("\n\t\t Movie: " + socketInputStream.readLine());
        					
        					//Print the plot
        					System.out.println("\n\t\t\t Plot: " + socketInputStream.readLine());
        					
        					//Print the genre
        					System.out.println("\n\t\t\t Genre: " + socketInputStream.readLine());
        					
        					//Print the publisher
        					System.out.println("\n\t\t Publisher: " + socketInputStream.readLine());
        					
        					//Print the Release year
        					System.out.println("\n\t\t\t Year of Release: " + socketInputStream.readLine());
        					       					        					
        					//Receiving variable for iteration.
            				response = socketInputStream.readLine();
            				try{
            					iterAuthors = Integer.parseInt(response);
            				} catch (Exception e){
            					e.printStackTrace();
            				}
            				
            				for(int i = 0; i < iterAuthors; i++) {
            					//Print the authors
            					System.out.println("\n\t\t\t Authors: " + socketInputStream.readLine());
            				}
        				}
        				else if (command == tftpCodes.NOTFOUND)
        					System.out.println("Cant found the book you are looking for.");
	        			break;	      
	        		}									
	        		case tftpCodes.ISEMPTY:
        			{
        				System.out.println("Error!, there is no data.");
						break;
        			}
										
					//Exit command
	        		case tftpCodes.CLOSECONNECTION:
	        		{
	        	        //Close scanner
	        	        reader.close();
	        			reader = null;
	        			
	        	        //Close socket and connections
	        	        socketOutputStream.close();
	        	        socketInputStream.close();
	        		    socket.close();
	        		} 
	        		
	        	    //Wrong command
	        		case tftpCodes.WRONGCOMMAND:
        			{
        				System.out.println("This is not a valid command");
        				break;
        			}						
	        	}
	        } while(currentCommand != tftpCodes.CLOSECONNECTION);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
