package simulation;

import java.util.ArrayList;
import java.util.Random;


import simulation.elevator.Building;
import simulation.floor.WrongFloorException;
import simulation.persons.Person;
import simulation.persons.PersonException;
import javax.swing.SwingWorker;

public class Simulation extends SwingWorker{
	private Building building;
	private ArrayList<Person> person_list;
	public Simulation(Building b,ArrayList<Person> person_list){
		this.building=b;
		this.person_list=person_list;
	}

	@Override
	protected Object doInBackground() throws Exception {
		int progress=0;
		while (true) {
			System.out.println(progress);
			setProgress(progress++);
			Thread.sleep(800);
			if (progress%6==1)
				this.new_person();
			try {
				building.NextMove();
				for (Person person : this.person_list) {
					person.NextMove();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private ArrayList<Person> new_person() throws WrongFloorException, PersonException {
		// TODO Auto-generated method stub
		Random r=new Random();
		int position=r.nextInt(building.getNb_etage()+1);
		int destination=r.nextInt(building.getNb_etage()+1);
		Person p=building.addPerson(position,destination);
		this.person_list.add(p);
		return person_list;
	}
	
}
