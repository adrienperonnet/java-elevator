package simulation.timer;

import simulation.Logger;
import simulation.elevator.DoorException;
import simulation.elevator.Elevator;
import simulation.persons.PersonException;

public class Timer implements Runnable{
	private int time;
	private Logger logger;
	private Elevator elevator;
	
	public Timer(Elevator e){
		this.time=0;
		this.elevator=e;
	}

	public int getTime(){
		return time;
	}
	
	protected void increaseTime(){
		time++;
	}

	protected void oneAction() throws DoorException, PersonException{
		if (this.getTime()%2==0)
			this.elevator.NextMove();		
		this.increaseTime();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true)
			try {
				this.oneAction();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
