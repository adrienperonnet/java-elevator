package simulation.elevator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import simulation.floor.Floor;
import simulation.persons.Person;

public class NaiveElevatorTest  extends ElevatorTest{

	
	@Before
	public void SetUp(){
		e= new NaiveElevator(new Floor(3),new Floor(6));
	}
	

	@Test
	public void callFrom() throws Exception{
		e.CallFromFloor(new Floor(2));
		assertEquals(e.GetActualFloor(), new Floor(3));
		e.NextMove();
		assertEquals(e.GetActualFloor(), new Floor(2));
		assertFalse(e.WorkDone());
		e.NextMove();
		assertEquals(e.GetActualFloor(), new Floor(2));
		assertTrue(e.WorkDone());
	}
	

}
