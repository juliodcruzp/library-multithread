package lists;

import bookdata.*;

public class linkedList {
  
  nodeList header;
  int size = 0;
	
	public class nodeList{
		author a;
		nodeList next;
	
		public nodeList(String firstN, String lastN) {
		  a = new author(firstN, lastN);//
		  next = null;//
		}
	}
	
	public linkedList() {
	  header =null;
	}
	
  public void insertNewNode(String firstN, String lastN) {
	  nodeList newNode = new nodeList(firstN, lastN);
	
	  if (header == null) {
		  header = newNode;
		  size++;
	  }
	  else if (lastN.compareTo(header.a.getLastName()) < 0) {
		  newNode.next = header;
		  header = newNode;
		  size++;
	  }
	  else if (lastN.compareTo(header.a.getLastName()) > 0) {
		  header.next = newNode;
		  size++;
	  }
	  else if(lastN.compareTo(header.a.getLastName()) == 0){
		  if(firstN.compareTo(header.a.getFirstName()) == 0){
			  return; //System.out.println("Author already on list.");
		  }
		  else if(firstN.compareTo(header.a.getFirstName()) < 0) {
			  newNode.next = header;
			  header = newNode;
			  size++;
		  }
		  else if(firstN.compareTo(header.a.getFirstName()) > 0)
			  header.next = newNode;
		  	  size++;
	  }
  }

  //Modify()
  public void modifyLinkList(author oldAuthor, author newAuthor) {
	  nodeList current = new nodeList("","");
	  current = header;
	  while (current != null) {
		  if(oldAuthor.getLastName().compareTo(current.a.getLastName()) == 0){
			  if (oldAuthor.getFirstName().compareTo(current.a.getFirstName()) == 0) {
				  current.a.setLastName(newAuthor.getLastName());
				  current.a.setFirstName(newAuthor.getFirstName());
			  }
		  }
		  current = current.next;
	  }
  }

  //Search author by index
  public String getAuthorName(int index) {
	  
	  nodeList current = header;
	
	  for(int i = 0; i < index; i++) {
		  current = current.next;
	  }
	  return current.a.getCompleteName();
  }
  
  //Return the size of the list
  public int getSize() {
	  return size;
  }
}


