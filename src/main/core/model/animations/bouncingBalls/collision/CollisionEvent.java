package main.core.model.animations.bouncingBalls.collision;

public class CollisionEvent implements Comparable<CollisionEvent> {
	
	private double t;
	private boolean valid = true;
	
	public CollisionEvent(double t) {
		this.t = t;
	}
	
	public double getTime() {
		return t;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	protected void setValid(boolean valid) {
		this.valid = valid;
	}
	
	//Method that need to be overidden according to collision type
	public void trigger() {
		
	}
	
	

	@Override
	public int compareTo(CollisionEvent col) {
		if(t < col.getTime())
			return 1;
		if(t > col.getTime())
			return -1;
		return 0;
	}
	
}
