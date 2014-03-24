package simulation.floor;
import static org.junit.Assert.*;

import org.junit.Test;

import simulation.elevator.Elevator;
import simulation.elevator.FIFOElevator;
import simulation.persons.Person;
import simulation.persons.PersonException;


public class FloorTest {

	@Test(expected=WrongFloorException.class)
	public void BadFloor() throws Exception {
		new Floor(-1);
	}

	@Test
	public void compare() throws WrongFloorException{
		assertEquals(new Floor(1), new Floor(1));
		assertNotSame(new Floor(1), new Floor(2));
		assertEquals((new Floor(1)).hashCode(), (new Floor(1)).hashCode());
	}
	
	@Test
	public void addPerson() throws PersonException{
		Floor f=new Floor(10);
		Elevator e=new FIFOElevator(new Floor(1), new Floor(15));
		f.addPerson(new Person(e,new Floor(14)));
	}
	
	@Test(expected=PersonException.class) // You are already on this floor
	public void TryAddPerson() throws PersonException{
		Floor f=new Floor(10);
		Elevator e=new FIFOElevator(new Floor(1), new Floor(15));
		f.addPerson(new Person(e,f));
	}
	
	@Test
	public void removePerson() throws PersonException{
		Floor f=new Floor(10);
		Elevator e=new FIFOElevator(new Floor(1), new Floor(10));
		Person p=new Person(e,f);
		f.removePerson(p);
	}	
	
	@Test(expected=PersonException.class) // You are not in this floor
	public void tryRemovePerson() throws PersonException{
		Floor f=new Floor(10);
		Elevator e=new FIFOElevator(new Floor(1), new Floor(10));
		Person p=new Person(e,new Floor(2));
		f.removePerson(p);
	}	
}
