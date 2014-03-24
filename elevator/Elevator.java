package simulation.elevator;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

import java.util.HashSet;

import simulation.IDrawable;
import simulation.floor.Floor;
import simulation.persons.Person;
import simulation.persons.PersonException;
import simulation.timer.ITimeDependant;


public abstract class Elevator implements IElevator,ITimeDependant,IDrawable{


	protected Floor actualFloor;
	protected final Doors doors=new Doors();
	protected HashSet<Person> persons;
	protected ArrayList<Floor> floors;
	

	
	public Floor GetLastFloor(){
		return this.floors.get(this.floors.size()-1);
	}

	public Floor GetActualFloor() {
		return this.actualFloor;
	}

	/**
	 * Return the array index of the actual floor.
	 * @return
	 */
	protected int floor_index(){
		return this.floors.lastIndexOf(this.actualFloor);
	}
	
	/**
	 * Makes the elevator move one floor up.
	 */
	protected void move_up(){
		if (this.actualFloor==this.GetLastFloor())
			throw new RuntimeException("Too up");
		this.actualFloor=this.floors.get(this.floor_index()+1);
	}
	/**
	 * Makes the elevator move one floor down.
	 */	
	protected void move_down(){
		if (this.actualFloor.level==0)
			throw new RuntimeException("Too down");
		this.actualFloor=this.floors.get(this.floor_index()-1);
	}
	

	protected abstract void Move();
	protected abstract boolean ClearQueue();
	
	/**
	 * Is there work to do for the elevator at the moment ?
	 * @return
	 */
	public abstract boolean WorkDone();
	

	public boolean doorsOpen(){
		return this.doors.isOpen();
	}

	
	@Override
	public final void GoIn(Person p) throws PersonException {
		if (!(this.persons.add(p))){
			throw new PersonException("You are already on the elevator");
		}		
	}


	@Override
	public final void GoOut(Person p) throws PersonException {
		if (!(this.persons.remove(p)))
			throw new PersonException("You are not on the elevator.");		
	}	

	/**
	 * Return a shape representing the elevator room dimension
	 * @param r The whole building size size.
	 * @return
	 */
	protected Shape GetCabinShape(Shape s){
		//Height of the room of the elevator.
		Rectangle r=s.getBounds();
		int h=r.height/(this.GetLastFloor().level+1);
		int floor_from_top=this.GetLastFloor().level-this.GetActualFloor().level;
		int y=r.y+h*floor_from_top;
		return new Rectangle(r.x,y,r.width,h);
	}
	
	
	protected void Draw_cabin_detail(Graphics g){
		g.setColor (Color.yellow);

		Graphics gcabin=g.create();
		gcabin.setClip(GetCabinShape(g.getClipBounds()));
        Graphics gdoors=gcabin.create();
        Graphics gpersons=gcabin.create();
		//this.Draw_persons(g);
		//this.Draw_doors(g);
		int x=gcabin.getClipBounds().x;
		int y=gcabin.getClipBounds().y;
		int h=gcabin.getClipBounds().height-1;
		int w=gcabin.getClipBounds().width-1;
		//2 verticals
		gcabin.drawLine(x, y, x, y+h);
		gcabin.drawLine(x+w, y, x+w, y+h);
        //2 horizontals
		gcabin.drawLine(x, y, x+w, y);
		gcabin.drawLine(x, y+h, x+w, y+h);
        

        this.Draw_doors(gdoors);
        this.Draw_persons(gpersons);
        
	}

	protected void Draw_doors(Graphics g){
		int x=g.getClipBounds().x;
		int y=g.getClipBounds().y;
		int h=g.getClipBounds().height-1;
		int w=g.getClipBounds().width-1;
		Rectangle r=new Rectangle(x, y,w/20,h);
		g.setClip(r);
		this.doors.Draw(g);

	}
	
	public static void draw_clip(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		int x=g.getClipBounds().x;
		int y=g.getClipBounds().y;
		int h=g.getClipBounds().height-1;
		int w=g.getClipBounds().width-1;
		//2 verticals
        g2d.drawLine(x, y, x, y+h);
        g2d.drawLine(x+w, y, x+w, y+h);
        //2 horizontals
        g2d.drawLine(x, y, x+w, y);
        g2d.drawLine(x, y+h, x+w, y+h);
        //2 diagonales
        g2d.drawLine(x, y, x+w, y+h);
        g2d.drawLine(x+w, y, x, y+h);
	}


	@Override
	public void Draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		this.Draw_cabin_detail(g);

	}


	private void Draw_persons(Graphics g) {
		g.setColor (Color.red);
		// TODO Auto-generated method stub
		int x=g.getClipBounds().x;
		int y=g.getClipBounds().y;
		int h=g.getClipBounds().height-1;
		int w=g.getClipBounds().width-1;
		int espacement=w/10;
		Rectangle r=new Rectangle(x, y+h/2,espacement,h/2);
		for (Person p : this.persons) {
			r.translate(espacement, 0);
			g.setClip(r);
			p.Draw(g);
		}
	}

}
