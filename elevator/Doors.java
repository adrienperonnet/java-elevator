package simulation.elevator;

import java.awt.Color;
import java.awt.Graphics;

import simulation.IDrawable;

public final class Doors implements IDrawable{
	private boolean isopen=false;
	
	public Doors(){	
	}
	
	public void open() throws DoorException{
		if (this.isOpen())
			throw new DoorException(this.isOpen());
		else
			this.isopen=true;
	}
	
	public void close() throws DoorException{
		if (this.isOpen()){
			this.isopen=false;
		}
		else
			throw new DoorException(this.isOpen());
	}
	
	public boolean isOpen(){
		return this.isopen;
	}
	@Override
	public void Draw(Graphics g) {
		// TODO Auto-generated method stub
		Color initial_colour=g.getColor();
		if (this.isOpen()){
			g.setColor (Color.GREEN);
		}
		else{
			g.setColor (Color.RED);
		}
		int x=g.getClipBounds().x;
		int y=g.getClipBounds().y;
		int h=g.getClipBounds().height;
		int w=g.getClipBounds().width;
		g.drawLine(x+w/2, y, x+w/2, y+h);
		g.setColor(initial_colour);
	}

}
