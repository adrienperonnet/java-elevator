package simulation.elevator;

import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.awt.Shape;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import simulation.floor.Floor;
import simulation.persons.Person;
import simulation.persons.PersonException;

public abstract class ElevatorTest{
	protected Elevator e; //(3,6)

	
	@Before
	public abstract void SetUp();//e= new FIFOElevator(new Floor(3),new Floor(6));
	
	@Test
	public void get_floor(){
		assertEquals(e.GetActualFloor(),new Floor(3));
	}
	
	@Test
	public void get_last_floor(){
		assertEquals(e.GetLastFloor(),new Floor(6));
	}
	
	@Test
	public void work_done() throws Exception{
		FIFOElevator e1=new FIFOElevator(new Floor(4),new Floor(20));
		assertTrue(e1.WorkDone());
		e1.CallFromFloor(new Floor(3));
		assertFalse(e1.WorkDone());
	}

	@Test
	public void work_done2() throws Exception{
		FIFOElevator e1=new FIFOElevator(new Floor(4),new Floor(20));
		assertTrue(e1.WorkDone());
		e1.CallFromFloor(new Floor(4));
		e1.NextMove();
		assertTrue(e1.WorkDone());
	}	
	@Test
	public void go_up(){
		e.move_up();
		assertEquals(e.GetActualFloor(), new Floor(4));
		e.move_up();
		assertEquals(e.GetActualFloor(), new Floor(5));
		e.move_up();
		assertEquals(e.GetActualFloor(), new Floor(6));
	}

	@Test
	public void go_down(){
		e.move_down();
		assertEquals(e.GetActualFloor(), new Floor(2));
		e.move_down();
		assertEquals(e.GetActualFloor(), new Floor(1));
		e.move_down();
		assertEquals(e.GetActualFloor(), new Floor(0));
	}
	
	
	@Test(expected=RuntimeException.class)
	public void go_too_down(){
		for (int i = 0; i < 4; i++) {
			e.move_down();
		}
	}
	
	@Test(expected=RuntimeException.class)
	public void go_too_up(){
		for (int i = 0; i < 4; i++) {
			e.move_up();
		}
	}

	@Test()
	public void CallFrom() throws Exception{
		e.CallFromFloor(new Floor(2));
	}
	
	@Test(expected=Exception.class)
	public void TryCallFrom1() throws Exception{
		e.CallFromFloor(new Floor(7));
	}
	
	@Test(expected=Exception.class)
	public void TryCallFrom2() throws Exception{
		e.CallFromFloor(new Floor(-1));
	}
	

	
	@Test
	public void addPerson() throws PersonException{
		e.GoIn(new Person(e,new Floor(14)));
	}
	
	@Test(expected=PersonException.class) // You are already on this floor
	public void TryAddPerson() throws PersonException{
		Floor f=new Floor(14);
		Person p=new Person(e,f);
		e.GoIn(p);
		e.GoIn(p);
	}
	
	@Test
	public void removePerson() throws PersonException{
		Person p=new Person(e,new Floor(2));
		e.GoIn(p);
		e.GoOut(p);
	}	
	
	@Test(expected=PersonException.class) // You are not in the elevator
	public void tryRemovePerson() throws PersonException{
		Person p=new Person(e,new Floor(2));
		e.GoOut(p);
	}


}
