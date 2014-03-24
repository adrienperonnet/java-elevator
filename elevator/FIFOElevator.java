package simulation.elevator;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import javax.sound.sampled.Clip;

import simulation.floor.Floor;
import simulation.floor.WrongFloorException;
import simulation.persons.Person;


public final class FIFOElevator extends Elevator implements IElevator{

	
	protected Collection<Floor> queue;

	public FIFOElevator(Floor initialFloor,ArrayList<Floor> floors){
		this.actualFloor=initialFloor;
		this.floors=floors;
		this.queue=new LinkedList<Floor>();
		this.persons = new HashSet<Person>();
	}
	
	public FIFOElevator(Floor initialFloor,Floor lastFloor){
		if (initialFloor.level>lastFloor.level)
			throw new RuntimeException("Bad initialisation");
		this.floors = new ArrayList<Floor>();
		for (int i = 0; i <= lastFloor.level; i++) {
			this.floors.add(new Floor(i));
		}
		this.actualFloor=initialFloor;
		this.queue=new LinkedList<Floor>();
		this.persons = new HashSet<Person>();
	}

	public void GoToFloor(Floor floor) throws WrongFloorException{
		if (floor.level>this.GetLastFloor().level){
			throw new WrongFloorException();
		}
		this.queue.add(floor);
	}

	public void CallFromFloor(Floor floor) throws WrongFloorException {
		this.GoToFloor(floor);
	}
	
	protected boolean ClearQueue(){
		boolean contains=this.queue.contains(this.GetActualFloor());
		while (this.queue.contains(this.GetActualFloor())){
			this.queue.remove(this.GetActualFloor());
		}
		return contains;
	}
	
	public boolean WorkDone() {
		return this.queue.isEmpty();
	}	
	
	@Override
	public void Move() {
		if (this.WorkDone())
			return;
		
		if (((LinkedList<Floor>) this.queue).getFirst().level>this.GetActualFloor().level)
				this.move_up();
		else
				this.move_down();
	}
	
	/**
	 * Move the elevator with the timer.
	 * @throws Exception 
	 */
	public final void NextMove() throws DoorException{
		if (this.ClearQueue()){
			//The elevator is here to answer to a call, we open the door.
			if (!(this.doors.isOpen()))
				this.doors.open();
		}
		else if (this.doorsOpen())
		{
			this.doors.close();
		}
		else{
			this.Move();
		}
	}
		




}