package simulation.persons;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import simulation.elevator.DoorException;
import simulation.elevator.Elevator;
import simulation.elevator.FIFOElevator;
import simulation.floor.Floor;
import simulation.floor.WrongFloorException;


public class PersonTest {
	private Elevator e;
	
	@Before
	public void SetUp(){
		e= new FIFOElevator(new Floor(10),new Floor(20));
	}
	
	@Test
	public void create() {
		new Person(e,new Floor(2));
	}
	
	@Test
	public void CallElevator() throws Exception{
		Person p=new Person(e,new Floor(2));
		p.callElevator();
		assertFalse(e.WorkDone());
	}
	
	@Test
	public void InElevator(){
		Person p=new Person(e,new Floor(2));
		assertFalse(p.InElevator());
	}
	
	@Test
	public void GetPosition(){
		Person p=new Person(e,new Floor(2));
		assertEquals(new Floor(2), p.GetPosition());
	}
	
	@Test
	public void GoInElevator() throws Exception{
		Person p=new Person(e,new Floor(10));
		p.callElevator();
		e.NextMove(); // Doors are opening
		p.GoIn();
		assertTrue(p.InElevator());
	}
	
	@Test(expected=PersonException.class)
	public void TryGoInElevator() throws DoorException, PersonException{
		Person p=new Person(e,new Floor(9));
		p.GoIn();
	}	

	@Test(expected=DoorException.class)
	public void TryGoInElevator1() throws WrongFloorException, PersonException, DoorException{
		Person p=new Person(e,new Floor(11));;
		p.GoToFloor(new Floor(12));
		e.NextMove(); //Elevator go from 10 to 11
		p.GoIn(); // The person go in BUT THE DOORS ARE CLOSED
	}
	
	@Test(expected=DoorException.class)
	public void TryGoOut1() throws WrongFloorException, DoorException, PersonException{
		Person p=new Person(e,new Floor(10));;
		p.GoIn(); // The person go in BUT THE DOORS ARE CLOSED
	}	
	@Test
	public void PushFloor() throws WrongFloorException, PersonException, DoorException{
		Person p=new Person(e,new Floor(10));
		p.GoToFloor(new Floor(9));
		e.NextMove();//open doors
		p.GoIn();
		p.PushFloor();
		assertFalse(e.WorkDone());
	}
	
	@Test(expected=Exception.class)
	public void TryPushFloor() throws DoorException, PersonException {
		Person p=new Person(e,new Floor(9));
		p.GoIn();
		assertTrue(e.WorkDone());
	}
	
	@Test
	public void GoToFloor() throws Exception{
		Elevator e1= new FIFOElevator(new Floor(11),new Floor(20));
		Person p=new Person(e1,new Floor(10));
		p.GoToFloor(new Floor(10));
		assertTrue(e1.WorkDone());
		p.GoToFloor(new Floor(11));
		assertFalse(e1.WorkDone());
	}

	
	@Test
	public void GoToFloor3() throws Exception{
		Elevator e1= new FIFOElevator(new Floor(10),new Floor(20));
		Person p=new Person(e1,new Floor(10));
		p.GoToFloor(new Floor(21));//Everything is alright since the person don't know how much level there is (doors are closed)

	}

	@Test(expected=WrongFloorException.class)
	public void TryGoToFloor() throws WrongFloorException, PersonException, DoorException{
		Elevator e1= new FIFOElevator(new Floor(10),new Floor(20));
		Person p=new Person(e1,new Floor(10));
		p.GoToFloor(new Floor(21));
		e1.NextMove();//The elevator open the doors. 
		p.NextMove();//The person try to push the button 21 :/
	}
	@Test
	public void GoToFloor2() throws Exception{;
		Person p=new Person(e,new Floor(11));
		p.GoToFloor(new Floor(12));
		e.NextMove(); //elevator go from 10 to 11.
		e.NextMove(); //Doors open
		p.GoIn();
		p.PushFloor();
		e.NextMove(); //Doors close
		e.NextMove(); // elevator go from 11 to 12.
		e.NextMove(); //Doors are opening
		p.GoOut();
		assertEquals(new Floor(12), p.GetPosition());
	}
	
	@Test
	public void GoOut() throws Exception{
		Person p=new Person(e,new Floor(10));
		p.callElevator();
		e.NextMove();//Elevator open the doors.
		p.GoIn();
		assertTrue(p.InElevator());
		p.GoOut();
		assertFalse(p.InElevator());
	}

	@Test(expected=PersonException.class) // You are not in
	public void TryGoOut() throws DoorException, PersonException {
		Person p=new Person(e,new Floor(10));
		p.GoOut();
	}

	@Test(expected=DoorException.class) // Doors are closed
	public void TryGoOut5() throws DoorException, PersonException {
		Person p=new Person(e,new Floor(10));
		p.callElevator();
		e.NextMove();
		p.GoIn();
		e.NextMove();//Close the doors
		p.GoOut();
	}
	
	@Test(expected=PersonException.class) // The elevator is not here
	public void TryGoOut3() throws DoorException, PersonException {
		Person p=new Person(e,new Floor(9));
		p.GoOut();
	}
	@Test(expected=PersonException.class) //Doors are open but you are not in
	public void TryGoOut2() throws DoorException, PersonException{
		Person p=new Person(e,new Floor(10));
		p.callElevator();//Open doors
		e.NextMove();//Open doors
		p.GoOut();
	}
	@Test
	public void Move1() throws DoorException, WrongFloorException, PersonException{
		Person p=new Person(e,new Floor(11));;
		p.GoToFloor(new Floor(12));
		
		e.NextMove();//Elevator go from 10 to 11
		e.NextMove(); // Dors are opening
		p.NextMove();
		assertTrue(p.InElevator());
	}
	
	@Test
	public void Move2() throws Exception{
		Person p=new Person(e,new Floor(11));;
		p.GoToFloor(new Floor(12));
		
		e.NextMove(); //Elevator go from 10 to 11
		e.NextMove(); // Doors are opening
		p.NextMove(); // The person go in
		
		e.NextMove(); // The Doors are closing
		e.NextMove(); //Elevator go from 11 to 12
		e.NextMove(); // The Doors are opening
		p.NextMove(); // The person go out
		assertFalse(p.InElevator());
	}
	
	@Test
	public void Bug1() throws Exception{
		FIFOElevator e1=new FIFOElevator(new Floor(1), new Floor(5));
		Person p=new Person(e1,new Floor(2));
		Person p1=new Person(e1, new Floor(5));
		p.GoToFloor(new Floor(4));
		p1.GoToFloor(new Floor(3));
		for (int i = 0; i < 10; i++) {
			e1.NextMove();
			p1.NextMove();
			p.NextMove();			
		}
	}
}
