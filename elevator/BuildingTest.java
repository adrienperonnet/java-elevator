package simulation.elevator;

import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.awt.Shape;

import org.junit.Test;

import simulation.floor.Floor;
import simulation.floor.WrongFloorException;
import simulation.persons.PersonException;

public class BuildingTest {

	@Test
	public void test() {
		new Building(10);
		new Building(0);
	}

	@Test
	public void NbEtage(){
		Building b=new Building(10);
		assertEquals(10, b.getNb_etage());
	}
	
	@Test
	public void addPerson() throws PersonException{
		Building b=new Building(10);
		b.addPerson(2, 5);
	}

	@Test(expected=WrongFloorException.class)
	public void TryaddPerson() throws PersonException{
		Building b=new Building(10);
		b.addPerson(2, 20);
	}
	
	@Test
	public void GetFloorShape(){
		Building b=new Building(9); //9 floors = 0,1,2,..9 -> 10 different levels
		Shape s=b.GetFloorShape(new Rectangle(0,0,100,1000), new Floor(9));
		assertEquals(new Rectangle(0, 0,100,100),(Rectangle)s);
	}

	@Test
	public void GetFloorShape2(){
		Building b=new Building(9);
		Shape s=b.GetFloorShape(new Rectangle(0,0,100,1000), new Floor(0));
		assertEquals(new Rectangle(0, 900,100,100),(Rectangle)s);
	}

}
