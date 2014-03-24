package simulation.elevator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import simulation.floor.Floor;
import simulation.floor.WrongFloorException;
import simulation.persons.Person;

/**
 * Priority to the guys inside the elevator :)
 * @author adrien
 *
 */
public final class NaiveElevator extends Elevator implements IElevator{
	protected Collection<Floor> queue_outside;
	protected Collection<Floor> queue_inside;

	public NaiveElevator(Floor initialFloor,ArrayList<Floor> floors){
		this.actualFloor=initialFloor;
		this.floors=floors;
		this.queue_outside=new LinkedList<Floor>();
		this.queue_inside=new LinkedList<Floor>();
		this.persons = new HashSet<Person>();
	}
	
	public NaiveElevator(Floor initialFloor, Floor lastFloor) {
		if (initialFloor.level>lastFloor.level)
			throw new RuntimeException("Bad initialisation");
		this.floors = new ArrayList<Floor>();
		for (int i = 0; i <= lastFloor.level; i++) {
			this.floors.add(new Floor(i));
		}
		this.actualFloor=initialFloor;
		this.queue_outside=new LinkedList<Floor>();
		this.queue_inside=new LinkedList<Floor>();
		this.persons = new HashSet<Person>();
	}

	@Override
	public void GoToFloor(Floor floor) throws WrongFloorException {
		if (floor.level>this.GetLastFloor().level){
			throw new WrongFloorException();
		}
		this.queue_inside.add(floor);
		
	}

	@Override
	public void CallFromFloor(Floor floor) throws WrongFloorException {
		if (floor.level>this.GetLastFloor().level){
			throw new WrongFloorException();
		}
		this.queue_outside.add(floor);
	}

	@Override
	public void Move() {
		if (this.WorkDone())
			return;
		Floor next_floor;
		if (!(this.queue_inside.isEmpty()))
			next_floor=((LinkedList<Floor>) this.queue_inside).getFirst();
		else
			next_floor=((LinkedList<Floor>) this.queue_outside).getFirst();
		
		if (next_floor.level>this.GetActualFloor().level)
				this.move_up();
		else
				this.move_down();
	}

	@Override
	protected boolean ClearQueue() {
		boolean contains_outside=this.queue_outside.contains(this.GetActualFloor());
		while (this.queue_outside.contains(this.GetActualFloor())){
			this.queue_outside.remove(this.GetActualFloor());
		}
		boolean contains_inside=this.queue_inside.contains(this.GetActualFloor());
		while (this.queue_inside.contains(this.GetActualFloor())){
			this.queue_inside.remove(this.GetActualFloor());
		}
		return contains_outside || contains_inside;
	}

	@Override
	public boolean WorkDone() {
		return this.queue_outside.isEmpty() && this.queue_inside.isEmpty();
	}

	/**
	 * Move the elevator with the timer.
	 * @throws Exception 
	 */
	public final void NextMove() throws DoorException{
		if (this.doorsOpen()){
			this.doors.close();
			return;
		}
		
		if (!(this.queue_inside.isEmpty())){
			if (this.queue_inside.remove(this.GetActualFloor()))
				{
					this.doors.open();
					return;
				}
		}
		else if (!(this.queue_outside.isEmpty())){
			if (this.queue_outside.remove(this.GetActualFloor())){
				this.doors.open();
				return;
			}
		}
		this.Move();
	
	}
		


}
