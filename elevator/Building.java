package simulation.elevator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import simulation.IDrawable;
import simulation.floor.Floor;
import simulation.floor.WrongFloorException;
import simulation.persons.Person;
import simulation.persons.PersonException;

public final class Building{
	private final IElevator elevator;
	private final ArrayList<Floor> floors;
	private final int nb_etage;
	
	public Building(int nb_etage){
		this.floors = new ArrayList<Floor>();
		for (int i = 0; i <= nb_etage; i++) {
			this.floors.add(new Floor(i));
		}
		this.elevator=new FIFOElevator(this.floors.get(nb_etage/2), this.floors);
		this.nb_etage=nb_etage;
	}

	public int getNb_etage() {
		return nb_etage;
	}

	public void NextMove() throws DoorException, PersonException{
		this.elevator.NextMove();
	}
	

	
	public void DrawElevator(Graphics g) {
		g.setColor (Color.orange);
		this.elevator.Draw(g);
	}
	
	protected Shape GetBuildingShape(Shape s){
		Rectangle b= s.getBounds();
		return b;
	}
	
	protected Shape GetFloorShape(Shape s,Floor f){
		Rectangle r= s.getBounds();
		int floor_from_top=this.getNb_etage()-f.level;//10 floors, if elevator is in floor 0, there is 
		int h=r.height/(this.getNb_etage()+1);
		int y=r.y+floor_from_top*h;
		return new Rectangle(r.x,y,r.width,h);
	}
	
	public void DrawFloors(Graphics g){
		// TODO Auto-generated method stub
		g.setColor (Color.green);
		Shape s=g.getClip();
		for (Floor f : this.floors) {
			//System.out.println(f.level+" : "+g.getClip());
			g.setClip(GetFloorShape(s, f));
			f.Draw(g);
		}

	}

	public void DrawFixedArchitecture(Graphics g) {
		// TODO Auto-generated method stub
		
		//FIFOElevator.draw_clip(g);
	}

	public Person addPerson(int position, int destination) throws PersonException {
		Floor fposition,fdestination;
		if (0<=position && position<=this.getNb_etage()){
			fposition=this.floors.get(position);
		}
		else{
			throw new WrongFloorException();
		}
		if (0<=destination && destination<=this.getNb_etage()){
			fdestination=this.floors.get(destination);
		}
		else {
			throw new WrongFloorException();
		}
	

		Person p=new Person(this.elevator,fposition,fdestination);
		return p;
		// TODO Auto-generated method stub
		
	}
}
