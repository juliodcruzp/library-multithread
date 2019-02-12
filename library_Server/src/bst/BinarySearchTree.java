package bst;

import java.util.ArrayList;
import lists.*;
import bookdata.*;

public class BinarySearchTree {

	private Node root;
	ArrayList<String> allGenres;  //Variable to manage the print of the genres
	int size = 0;				  //Variable to manage the size of the BST
	Node interList = null;		  //Node to manage the book list
	boolean flag = false;
	Book bookFounded = null;

	//Constructor
	public BinarySearchTree(){
		this.root = null;
	}
	
	/*Use the compareTo method. s1.compareTo(s2) is:
		positive, if s1 > s2
		negative, if s1 < s2
		0, if s1.equals(s2)
	*/

	//Find if the genre exist (InOrder)
	public boolean find(String id){
		Node current = root;
		while(current != null){
			if(current.data.compareTo(id) == 0){
				return true;
			}
			else if(current.data.compareTo(id) > 0){
				current = current.left;
			}
			else{
				current = current.right;
			}
		}
		return false;
	}
	
	//Get the list of books of the one particular genre
	public DoublyLinkedList getListOfGenre(String genre){
		interList =  null;
		getTheGenreNode(genre);
		return interList.getBookList();
	}
	
	//Find the particular genre (InOrder)
	public void getTheGenreNode(String id){
		Node current = root;
		while(current != null){
			if(current.data.compareTo(id) == 0){
				interList = current;
				return;
			}
			else if(current.data.compareTo(id) > 0){
				current = current.left;
			}
			else{
				current = current.right;
			}
		}
	}

	//Function to delete an item from the binary tree.
	public boolean delete(String id){
		Node parent = root;
		Node current = root;
		boolean isLeftChild = false;

		//Encontrar el string a ser eliminado
		while(current.data != id){
			parent = current;
			if(current.data.compareTo(id) > 0){
				isLeftChild = true;
				current = current.left;
			}
			else{
				isLeftChild = false;
				current = current.right;
			}
			if(current == null){
				return false;
			}
		}

		//Si encontramos el string que quiero eliminar, llego aca.
		//Caso 1: Si el nodo que quiero eliminar no tiene hijos.
		if(current.left == null && current.right == null){
			if(current == root){
				root = null;
			}
			if(isLeftChild == true){
				parent.left = null;
			}
			else{
				parent.right = null;
			}
		}
		//Caso 2 : si el nodo que quiero eliminar solo tiene un hijo
		else if(current.right == null){
			if(current == root){
				root = current.left;
			}
			else if(isLeftChild){
				parent.left = current.left;
			}
			else{
				parent.right = current.left;
			}
		}
		else if(current.left == null){
			if(current == root){
				root = current.right;
			}else if(isLeftChild){
				parent.left = current.right;
			}else{
				parent.right = current.right;
			}
		}
		//Caso 3: cuando el nodo que quiero eliminar tiene dos hijos
		else if(current.left != null && current.right != null){
			
			//Necesitamos hallar el elemento minimo que remplazara el que vamos a eliminar.
			Node successor = getSuccessor(current);
			if(current == root){
				root = successor;
			}else if(isLeftChild){
				parent.left = successor;
			}else{
				parent.right = successor;
			}			
			successor.left = current.left;
		}		
		return true;		
	}
	
	public Node getSuccessor(Node deleteNode){
		Node successsor = null;
		Node successsorParent = null;
		Node current = deleteNode.right;
		while(current != null){
			successsorParent = successsor;
			successsor = current;
			current = current.left;
		}
		//Revisar si el sucesor tiene un hijo derecho. Ya por descarte no tiene uno izquierdo.
		//Si tiene, debemos agregarlo al izquierdo del padre del sucesor.
		if(successsor!=deleteNode.right){
			successsorParent.left = successsor.right;
			successsor.right = deleteNode.right;
		}
		return successsor;
	}

	public boolean insert(String id){
		Node newNode = new Node(id);
		
		//If the BST is empty insert has root.
		if(root == null){
			root = newNode;
			size++;
			return true;
		}
		
		Node current = root;
		Node parent = null;
		
		while(true){
			parent = current;
			
			//Check if the item already exist
			if(current.data.compareTo(id) == 0){
				return false;
			}
			
			//Insert in alphabetical order
			else if(current.data.compareTo(id) > 0){				
				current = current.left;
				if(current == null){
					parent.left = newNode;
					size++;
					return true;
				}
			}else{
				current = current.right;
				if(current == null){
					parent.right = newNode;
					size++;
					return true;
				}
			}
		}
	}
	
	//Returns an empty list if is empty, or return the list of the genres
	public ArrayList<String> listOfGenres(){
		allGenres = new ArrayList<>();
		
		if (root == null) {
			return allGenres;
		}
		
		printInOrder(root);
		return allGenres;
	}
	
	//Get data of each node of the tree InOrder and added to a Array list
	public void printInOrder(Node root){

		if(root!=null){
			printInOrder(root.left);
			allGenres.add(root.data);
			printInOrder(root.right);
		}
	}
	
	//Return the size of the list
	public int getSize(){
		return size;
	}
	
	//Find if title of a book exist
	public boolean findTitleExist(String title) {
		flag = false;
		findTheTitle(root, title);
		return flag;
	}
	
	//Find if the book exist in the binary tree (PreOrder)
	public void findTheTitle(Node currentNode, String title){
		if(currentNode.getBookList().findBook(title)){
			flag = true;
			return;
		}
		//Check left side of the binary tree
		if(currentNode.left != null)
			findTheTitle(currentNode.left, title);
		
		//Check right side of the binary tree
		if(currentNode.right != null)
			findTheTitle(currentNode.right, title);
	}
	
	//Function that return the book that I need to modify
	public Book findBookToModify(String title) {
		bookFounded = null;
		findTheBook(root, title);
		return bookFounded;
	}
		
	//Find if the book exist in the binary tree (PreOrder)
	public void findTheBook(Node currentNode, String title){
		if(currentNode.getBookList().findBook(title)){
			bookFounded = currentNode.getBookList().getBook(title);
		}
		//Check left side of the binary tree
		if(currentNode.left != null)
			findTheBook(currentNode.left, title);
			
		//Check right side of the binary tree
		if(currentNode.right != null)
			findTheBook(currentNode.right, title);
	}
	
	
}

class Node{

	String data;
	Node left;
	Node right;
	DoublyLinkedList bookList = new DoublyLinkedList();	

	public Node(String data){
		this.data = data;
		left = null;
		right = null;
	}
	
	public DoublyLinkedList getBookList(){
		return bookList;
	}
}