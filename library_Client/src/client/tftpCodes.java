package client;

public class tftpCodes {

	//********** MENU CODES **********//
	
	public static final int OK = 0;

	public static final int ADDGENRE = 1;

	public static final int ADDBOOK = 2;

	public static final int MODIFYABOOK = 3;

	public static final int LISTALLGENRE = 4;

	public static final int LISTALLBOOKBGENRE = 5;

	public static final int LISTBOOKPARTGENRE = 6;

	public static final int SEARCHFORABOOK = 7;

	public static final int CLOSECONNECTION = 8;

	//********** COMUNNICATION CODES **********//

	//Found the item that was search
	public static final int FOUNDED = 10;
	
	//The item already exit
	public static final int AEXISTITEM = 11;
	
	//Not found
	public static final int NOTFOUND = 12;
	
	//No genres - Binary tree Empty
	public static final int ISEMPTY = 13;
	
	//********** ERROR MESSAGES **********//
	
	//No valid command
	public static final int WRONGCOMMAND=30;
	
}