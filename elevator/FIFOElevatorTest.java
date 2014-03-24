package simulation.elevator;
import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.awt.Shape;

import org.junit.Before;
import org.junit.Test;

import simulation.floor.Floor;
import simulation.persons.Person;
import simulation.persons.PersonException;

/**
 * 
 */

/**
 * @author adrien
 *
 */
public class FIFOElevatorTest extends ElevatorTest{
	
	@Before
	public  void SetUp(){
		e= new FIFOElevator(new Floor(3),new Floor(6));
	}
	

	@Test(expected=RuntimeException.class)
	public void bad_initial_value() {
		new FIFOElevator(new Floor(23),new Floor(20));
	}


	
	@Test
	public void clear_queue() throws Exception{
		FIFOElevator e1=new FIFOElevator(new Floor(4),new Floor(20));
		e1.CallFromFloor(new Floor(3));
		e1.Move();
		assertTrue(e1.ClearQueue()); //Remove something from the queue
		assertFalse(e1.ClearQueue()); //Nothing more
	}
	
	@Test
	public void clear_queue2() throws Exception{
		FIFOElevator e1=new FIFOElevator(new Floor(4),new Floor(20));
		e1.CallFromFloor(new Floor(4));
		e1.CallFromFloor(new Floor(4));
		e1.ClearQueue();
		assertTrue(e1.WorkDone());
	}

	@Test
	public void next_move() throws Exception{
		FIFOElevator e1=new FIFOElevator(new Floor(4),new Floor(20));
		e1.CallFromFloor(new Floor(2));
		assertEquals(e1.GetActualFloor(), new Floor(4));
		e1.NextMove();
		assertEquals(e1.GetActualFloor(), new Floor(3));
		assertFalse(e1.WorkDone());
		e1.NextMove();
		assertEquals(e1.GetActualFloor(), new Floor(2));
		assertFalse(e1.WorkDone());
		e1.NextMove();//open the doors and clear the work
		assertTrue(e1.WorkDone());
		e1.NextMove();
		assertEquals(e1.GetActualFloor(), new Floor(2));
	}
	
	@Test
	public void next_move_with_queue() throws Exception{
		FIFOElevator e1=new FIFOElevator(new Floor(10),new Floor(20));
		e1.CallFromFloor(new Floor(11));
		e1.CallFromFloor(new Floor(12));
		e1.NextMove();
		assertEquals(e1.GetActualFloor(), new Floor(11));
		e1.NextMove();e1.NextMove();//Open&CLose the doors
		e1.NextMove();
		assertEquals(e1.GetActualFloor(), new Floor(12));
	}
	
	@Test
	public void fifo_algorithm() throws Exception{
		FIFOElevator e1=new FIFOElevator(new Floor(7),new Floor(20));
		e1.CallFromFloor(new Floor(2));
		e1.CallFromFloor(new Floor(17));
		for (int i = 0; i < 7-2; i++) {
			e1.NextMove();
		}
		assertEquals(e1.GetActualFloor(), new Floor(2));
		e1.NextMove();e1.NextMove(); //Open&Close the doors
		for (int i = 0; i < 17-2; i++) {
			e1.NextMove();
		}		
		assertEquals(e1.GetActualFloor(), new Floor(17));
	}
	
	/*
	 * When somebody call the elevator while it's door are opened, it tried to reopen it.
	 */
	@Test
	public void Bug2() throws DoorException{
		FIFOElevator e1=new FIFOElevator(new Floor(2),new Floor(20));
		e1.CallFromFloor(new Floor(2));
		e1.NextMove();//Dors opening
		e1.CallFromFloor(new Floor(2));
		e1.NextMove();//Dors opening
	}

	@Test
	public void CabinShape(){
		FIFOElevator e1=new FIFOElevator(new Floor(1), new Floor(10));
		FIFOElevator e2=new FIFOElevator(new Floor(1), new Floor(10));
		Shape s1=e1.GetCabinShape(new Rectangle(10,20,100,200));
		Shape s2=e2.GetCabinShape(new Rectangle(10,20,100,200));
		assertEquals(s1,s2);
	}
	
	@Test
	public void CabinShape2(){
		FIFOElevator e1=new FIFOElevator(new Floor(1), new Floor(10));
		FIFOElevator e2=new FIFOElevator(new Floor(1), new Floor(11));
		Shape s1=e1.GetCabinShape(new Rectangle(10,20,100,200));
		Shape s2=e2.GetCabinShape(new Rectangle(10,20,100,200));
		assertNotSame(s1, s2);
	}
	
	@Test
	public void CabinShape3(){
		FIFOElevator e1=new FIFOElevator(new Floor(1), new Floor(10));
		FIFOElevator e2=new FIFOElevator(new Floor(2), new Floor(10));
		Shape s1=e1.GetCabinShape(new Rectangle(10,20,100,200));
		Shape s2=e2.GetCabinShape(new Rectangle(10,20,100,200));
		assertNotSame(s1, s2);
	}	

	@Test
	public void CabinShape4NoException(){
		FIFOElevator e1=new FIFOElevator(new Floor(0), new Floor(10));
		FIFOElevator e2=new FIFOElevator(new Floor(10), new Floor(10));
		e1.GetCabinShape(new Rectangle(10,20,100,200));
		e2.GetCabinShape(new Rectangle(10,20,100,200));
	}
	
	@Test
	public void CabinShape5(){
		FIFOElevator e=new FIFOElevator(new Floor(9), new Floor(9)); //10 different floors
		Shape s=e.GetCabinShape(new Rectangle(0,0,100,1000));
		assertEquals(new Rectangle(0, 0,100,100),s);
	}

	
	@Test
	public void CabinShape6(){
		FIFOElevator e=new FIFOElevator(new Floor(0), new Floor(9));
		Shape s=e.GetCabinShape(new Rectangle(0,0,100,1000));
		assertEquals(new Rectangle(0, 900,100,100),s);
	}
	@Test
	public void CabinShape7(){
		FIFOElevator e=new FIFOElevator(new Floor(0), new Floor(9));
		assertEquals(new Rectangle(0, 900,100,100),e.GetCabinShape(new Rectangle(0,0,100,1000)));
		e.move_up();
		assertEquals(new Rectangle(0, 800,100,100),e.GetCabinShape(new Rectangle(0,0,100,1000)));
	}		

}

