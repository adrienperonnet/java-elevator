package simulation.persons;

import simulation.IDrawable;
import simulation.elevator.DoorException;
import simulation.floor.Floor;

public interface IPerson  extends IDrawable {
	/**
	 * A person is at a certain floor, and can call an elevator from his floor.
	 */
	public void callElevator();
	
	/**
	 * When an elevator is at his floor, the person ca go inside the elevator.
	 * @throws DoorException The doors of the elevator are closed
	 * @throws PersonException The elevator is not at the same floor,or the person is already inside the elevator.
	 */
	public void GoIn() throws DoorException, PersonException;
	
	/**
	 * The person can go out an elevator whenever he wants.
	 * @throws DoorException The doors of the elevator are closed
	 * @throws PersonException The person is not inside the elevator
	 */
	public void GoOut() throws DoorException, PersonException;
	
	/**
	 * When the person is inside the elevator he can push a button to select a floor.
	 * @throws PersonException The person is not inside the elevator.
	 */
	public void PushFloor(Floor f) throws PersonException;
	

}
