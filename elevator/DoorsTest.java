package simulation.elevator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DoorsTest {
	private Doors d;
	
	@Before
	public void test() {
		d= new Doors();
	}
	
	@Test
	public void isOpen(){
		assertFalse(this.d.isOpen());
	}
	
	@Test
	public void open() throws DoorException{
		d.open();
		assertTrue(this.d.isOpen());
	}

	@Test
	public void close() throws DoorException{
		d.open();
		d.close();
		assertFalse(this.d.isOpen());
	}
	
	@Test(expected=DoorException.class)
	public void tryOpen() throws Exception{
		d.open();
		d.open();
	}

	@Test(expected=DoorException.class)
	public void tryClose() throws Exception{
		d.close();
	}
}
