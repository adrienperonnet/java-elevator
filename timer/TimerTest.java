package simulation.timer;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import simulation.elevator.Elevator;
import simulation.elevator.FIFOElevator;
import simulation.floor.Floor;



public class TimerTest {
	private Elevator e;
	private Timer t;
	@Before
	public void SetUp(){
		e=new FIFOElevator(new Floor(10),new Floor(20));
		t=new Timer(e);
	}
	
	@Test
	public void getTime() {
		assertEquals(t.getTime(), 0);
	}

	@Test
	public void incrementtTime() {
		t.increaseTime();
		assertEquals(t.getTime(), 1);
	}
	
	@Test
	public void OneActionIncreaseTime() throws Exception{
		t.oneAction();
		assertEquals(t.getTime(), 1);		
	}

	//Check that increasing the time make the elevator move
	@Test
	public void OneActionMoveElevator() throws Exception{
		FIFOElevator e1=new FIFOElevator(new Floor(4),new Floor(20));
		Timer t1=new Timer(e1);
		e1.CallFromFloor(new Floor(2));
		for (int i = 0; i < 10; i++) {
			t1.oneAction();
		};
		t1.oneAction();
		assertEquals( new Floor(2),e1.GetActualFloor());
	}
}
