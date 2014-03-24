package simulation.elevator;

public class DoorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isopen;
	
	public DoorException(boolean isopen){
		this.isopen=isopen;
	}
	

}
