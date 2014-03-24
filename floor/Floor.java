package simulation.floor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import simulation.IDrawable;
import simulation.elevator.FIFOElevator;
import simulation.persons.Person;
import simulation.persons.PersonException;

public final class Floor implements IDrawable{

	public int level;
	private HashSet<Person> persons;
	public Color color;
	
	public Floor(int level) throws WrongFloorException{
		if (level<0)
			throw new WrongFloorException();
		this.level=level;
		
		this.persons = new HashSet<Person>();
		this.color=Color.BLACK;
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Floor)) return false;
	    return this.level== ((Floor)other).level;
	}
	
	@Override
	public int hashCode(){
		return this.level;
	}
	
	public void addPerson(Person p) throws PersonException{
		if (!(this.persons.add(p))){
			throw new PersonException("You are already on this floor");
		}
		
	}
	
	public void removePerson(Person p) throws PersonException{
		if (!(this.persons.remove(p)))
			throw new PersonException("You are not on this floor.");
	}

	private void DrawPersons(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		int x=g2d.getClipBounds().x;
		int y=g2d.getClipBounds().y;
		int h=g2d.getClipBounds().height;
		int w=g2d.getClipBounds().width;
		int espacement=w/10;
		
		Rectangle r=new Rectangle(x, y+h/2,espacement,h/2);
		for (Person p : this.persons) {
			//
			r.translate(espacement, 0);
			g.setClip(r);
			p.Draw(g);
		}

	}
	
	public void Draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int x=g2d.getClipBounds().x;
		int y=g2d.getClipBounds().y;
		int h=g2d.getClipBounds().height;
		int w=g2d.getClipBounds().width;
		g2d.setColor(this.color);
        g2d.drawLine(x, y+h-1, x+w-1, y+h-1);
        g2d.drawString("LEVEL "+Integer.toString(this.level), 0, y+h/2-10);
        this.DrawPersons(g2d);
	}


	
}
