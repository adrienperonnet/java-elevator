package simulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulation.elevator.Building;
import simulation.elevator.FIFOElevator;
import simulation.persons.Person;


class Surface extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int xmax=400;
    static int ymax=700;
    

    public Surface(Building b) {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
	}

}

class FloorSurface extends JComponent{
	private Building building;
	public FloorSurface(Building b){
		this.building=b;
	}
    @Override
    public void paint(Graphics g) {
    	g.setClip(0,0,200,500);
        super.paintComponent(g);
        this.building.DrawFloors(g);
    }    
}

class ElevatorSurface extends JComponent{
	private Building building;
	public ElevatorSurface(Building b){
		this.building=b;
	}
	
    @Override
    public void paint(Graphics g) {
    	g.setClip(0,0,200,500);
        super.paintComponent(g);
        this.building.DrawElevator(g);
    }    
}

public class Window extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Building building;

	private Window(Building b) {
        this.building=b;
    }
    

    public static Window create(Building b) {
    	Window window = new Window(b);
        window.setTitle("Lines");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = new Surface(b);
        FloorSurface fs=new FloorSurface(b);
        ElevatorSurface es=new ElevatorSurface(b);
        content.add(fs, BorderLayout.WEST);
        content.add(es, BorderLayout.WEST);
        window.setContentPane(content);
        window.setSize(Surface.xmax, Surface.ymax);
        return window;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
        	
            @Override
            public void run() {
            	Building b=new Building(10);
            	final Window lines = Window.create(b);
                lines.setVisible(true);
               
                
                Simulation s=new Simulation(b,new ArrayList<Person>());
                s.execute();
              
                s.addPropertyChangeListener(
                	     new PropertyChangeListener() {
                	         public  void propertyChange(PropertyChangeEvent evt) {
                	             if ("progress".equals(evt.getPropertyName())) {
                	                 lines.repaint();
                	             }
                	         }
                	     });
            }
        });
    }
}
