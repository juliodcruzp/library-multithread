package lists;
import bookdata.*;

public class DoublyLinkedList {

    int size;           //Variable que define el tamano de la lista.
    Node head, tail;    //Nodos que definen el Head y Tail en la lista.

    //Constructor
    public DoublyLinkedList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    //Insert a new book in alphabetic order.
    public void insert(Book nb){

        //Creamos uno nuevo nodo con el libro que queremos ingresar.
        Node newNode = new Node(nb);

        //Insertar si la lista esta vacia.
        if(size == 0){
            head = newNode;
            tail = head;
            size++;
            return;
        }
        //Revisar si el libro ya existe en la lista
        else{
            Node current = head;
            for(int i = 0; i < size; i++){
                if(nb.getTitle().compareTo(current.stdBook.getTitle()) == 0){
                    System.out.println("Already exist a book with that name.");
                    return;
                }
                current = current.next;
            }
        }
        //Revisar si seria el nuevo head.
        if (nb.getTitle().compareTo(head.stdBook.getTitle()) < 0){
            newNode.next = head;
            newNode.previous = tail;
            head.previous = newNode;
            tail.next = newNode;
            head = newNode;
            size++;
            return;
        }
        //Revisar si puede ser el nuevo Tail.
        else if (nb.getTitle().compareTo(tail.stdBook.getTitle()) > 0){
            newNode.previous = tail;
            newNode.next = head;
            head.previous = newNode;
            tail.next = newNode;
            tail = newNode;
            size++;
            return;
        }
        //Buscar el lugar correcto para ingresar.
        else{
            Node current = head.next;
            for(int i = 0; i < size; i++){
                if (nb.getTitle().compareTo(current.stdBook.getTitle()) < 0){
                    newNode.previous = current.previous;
                    current.previous.next = newNode;
                    current.previous = newNode;
                    newNode.next = current;
                    size++;
                    return;
                }
                current = current.next;
            }
        }
    }

    //Delete a book from the list.
    public void deleteBook(String bookTitle){
        Node current = head;

        //Revisar si la lista esta vacia primero.
        if (head == null && tail == null){
            System.out.println("There is no items in the list.");
            return;
        }

        //Realizar la busqueda del string que se desea eliminar.
        for(int i = 0; i < size; i++){
            if(current.stdBook.getTitle().compareTo(bookTitle) == 0){
                if(size == 1){
					head = null;
					tail = null;
				}
				else if(current == head){
                    head.previous.next = current.next;
                    current.next.previous = head.previous;
                    head = head.next;
                    current.next = null;
					current.previous = null;
				}
				else if(current == tail){
					tail.previous.next = current.next;
                    current.next.previous = tail.previous;
                    tail = tail.previous;
                    current.next = null;
					current.previous = null;
				}
				else{	
					current.previous.next = current.next;
					current.next.previous = current.previous;
					current.previous = null;
					current.next = null;
				}
                size--;
                return;
            }
            current = current.next;
        }


    }

    //Return the size of the list.
	public int getSize() {
		return size;
	}

    //Show the list of books.
    public void printDList(){
        Node current = head;
        for(int i = 0; i < size; i++){
			System.out.println(current.stdBook.getTitle());
			current = current.next;
		}
    }

    //Find a book in the list by title.
    public boolean findBook(String bookTitle){
        Node current = head;
        
        if(current == null){
            return false;
        }
        
        for(int i = 0; i < size; i++){
			if(current.stdBook.getTitle().compareTo(bookTitle) == 0){
				return true;
			}
			current = current.next;
		}
		return false;
    }
    
    //Return a book finding by index.
    public Book selectBook(int index){
        Node current = head;
        for(int i = 0; i < index; i++){
            current = current.next;
        }
        return current.stdBook;
    }
    
    //Get a book in the list by title.
    public Book getBook(String bookTitle){
        Node current = head;
        for(int i = 0; i < size; i++){
			if(current.stdBook.getTitle().compareTo(bookTitle) == 0){
				return current.stdBook;
			}
			current = current.next;
		}
		return null;
    }
    
}

class Node{
    Book stdBook;
    Node next;
    Node previous;

    //Constructor
    public Node(){
        stdBook = null;
        next = null;
        previous = null;
    }

    //Constructor con argumento
	public Node(Book bookR){
		this.stdBook = bookR;
        next = null;
        previous = null;
	}
}