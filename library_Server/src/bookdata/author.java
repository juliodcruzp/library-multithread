package bookdata;
public class author {
	
	private String firstName, lastName;
	
	public author(String firstN, String lastN){
		firstName=firstN;
		lastName=lastN;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getCompleteName() {
		return (firstName + " " + lastName);
	}

	public void setLastName(String lastN) {
		lastName = lastN;
	}

	public void setFirstName(String firstN) {
		firstName = firstN;
	}
}
