package simulation.elevator;

import simulation.IDrawable;
import simulation.floor.Floor;
import simulation.floor.WrongFloorException;
import simulation.persons.Person;
import simulation.persons.PersonException;

/**
 * @author adrien
 * Interface of an elevator from a user point of view.
 */
public interface IElevator extends IDrawable {
	
	/**
	 * One person inside the elevator tell it to go to a given floor.
	 * @param floor
	 * @throws Exception 
	 */
	public void GoToFloor(Floor floor) throws WrongFloorException;
	
	/**
	 * One person call the elevator from a given floor.
	 * @param floor
	 * @throws Exception 
	 */
	void CallFromFloor(Floor floor) throws WrongFloorException;	
	
	/**
	 * Where is the elevator ?
	 * @return the actual floor where the elevator is.
	 */
	Floor GetActualFloor();

	/**
	 * What is the last floor the elevator can go to.
	 * @return The last floor
	 */
	Floor GetLastFloor();
	
	/**
	 * Are the doors open?
	 * @return Boolean
	 */
	public boolean doorsOpen();
	
	/**
	 * A person go inside the elevator.
	 * No verification are done from the elevator point of view.
	 * @param p
	 * @throws PersonException
	 */
	public void GoIn(Person p) throws PersonException;
	public void GoOut(Person p) throws PersonException;
	
	public void NextMove() throws DoorException;
	
}
