package simulation.persons;

public class PersonException extends Exception{
	String message;
	public PersonException(String message)
	{
		this.message=message;
		
	}
	
	public String toString(){
		System.out.println(this.message);
		return message;
	}
}
