package simulation.persons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import simulation.IDrawable;
import simulation.elevator.DoorException;
import simulation.elevator.Elevator;
import simulation.elevator.FIFOElevator;
import simulation.elevator.IElevator;
import simulation.floor.Floor;
import simulation.floor.WrongFloorException;
import simulation.timer.ITimeDependant;



public final class Person implements IPerson,ITimeDependant,IDrawable {
	private Floor floor;//position of the person
	private final IElevator elevator; //Elevator used by this person.
	protected Floor waitingFloor; // The person wait the elevator to go to this floor
	private Color colour;
	private final static Collection<Person> persons= new ArrayList<Person>(0);

	public Person(IElevator elevator2,Floor f){
		persons.add(this);
		this.elevator=elevator2;
		this.floor=f; //position of the person
		try {
			this.floor.addPerson(this);
		} catch (PersonException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final Random generator = new Random();
		this.colour=new Color(generator.nextInt(255), generator.nextInt(255),generator.nextInt(255));
	}

	public Person(IElevator elevator2,Floor position,Floor destination) throws PersonException{
		this(elevator2,position);
		persons.add(this);
		this.GoToFloor(destination);
	}
	
	public void callElevator() throws WrongFloorException{
		this.elevator.CallFromFloor(this.floor);
	}

	public boolean InElevator(){
		return (this.floor==null);
	}
	
	public Floor GetPosition(){
		return this.floor;
	}
	/*
	 * The person go inside the elevator
	 */
	public void GoIn() throws DoorException, PersonException{
		if (this.InElevator())
			throw new PersonException("You are already inside the elevator.");
		if (this.floor.equals(this.elevator.GetActualFloor())){
			if (this.elevator.doorsOpen()){
				this.floor.removePerson(this);
				this.elevator.GoIn(this);
				this.floor=null;
			}
			else
				throw new DoorException(false);
		}
		else{
			throw new PersonException("The elevator is not here (floor "+this.elevator.GetActualFloor().level+"), you can't go in because you are on floor" + this.floor.level+".");
		}
	}	

	public void GoOut() throws DoorException, PersonException{
		if (this.InElevator())
			if (this.elevator.doorsOpen())
			{
				this.floor=this.elevator.GetActualFloor();
				this.floor.addPerson(this);
				this.elevator.GoOut(this);
				this.waitingFloor=null;
			}
			else
				throw new DoorException(false);
		else
			throw new PersonException("You can't go out if you are not inside the elevator.");
	}
	
	public void PushFloor(Floor f) throws PersonException{
		if (this.InElevator()){
			if (this.waitingFloor==null)
				throw new PersonException("You should have a waitingFloor to call this method. You are on floor "+this.elevator.GetActualFloor());
			this.elevator.GoToFloor(this.waitingFloor);
		}
		else{
			throw new PersonException("You should be inside an elevator to push the floor button");
		}
	}
	
	public void PushFloor() throws PersonException{
		PushFloor(this.waitingFloor);
	}
	
	public void GoToFloor(Floor f) throws PersonException {
		
		if (this.InElevator())
			throw new PersonException("You can't call an Elevator if you are inside it.");
		if (f.equals(this.floor))
			return;
		this.waitingFloor=f;
		callElevator();
	}
	
	
	public void NextMove() throws DoorException, PersonException{
		if (this.InElevator()){
			if (this.elevator.GetActualFloor().equals(this.waitingFloor))
				if (this.elevator.doorsOpen())
					this.GoOut();
		}
		
		else if (this.waitingFloor != null){
			if (this.GetPosition().equals(this.elevator.GetActualFloor())){
				if (this.elevator.doorsOpen()){
					this.GoIn();
					this.PushFloor();
				}
			}
			
		}
				
				

	}

	@Override
	public void Draw(Graphics g) {
		// TODO Auto-generated method stub
        Graphics2D g2d = (Graphics2D) g;
        Color initial_colour=g2d.getColor();
		int x=g2d.getClipBounds().x;
		int y=g2d.getClipBounds().y;
		int h=g2d.getClipBounds().height;
		int w=g2d.getClipBounds().width;
		//FIFOElevator.draw_clip(g2d);
		
		g.setColor(this.colour);
		g2d.drawLine(x+w/2, y+h/2, x+w/2, y+h);
		
		if (this.waitingFloor != null){
			g.setColor(this.waitingFloor.color);
			g2d.drawString(Integer.toString(this.waitingFloor.level), x, y+h/2);
			g.setColor(initial_colour);
			//FIFOElevator.draw_clip(g2d);
		}
		
		
		
	}

}
