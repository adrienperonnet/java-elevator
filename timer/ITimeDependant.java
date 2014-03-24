package simulation.timer;

import simulation.elevator.DoorException;
import simulation.persons.PersonException;

public interface ITimeDependant {
	public void  NextMove() throws DoorException, PersonException;
}
