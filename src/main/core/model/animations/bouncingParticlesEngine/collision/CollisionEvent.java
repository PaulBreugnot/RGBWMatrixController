package main.core.model.animations.bouncingBalls.collision;

import main.core.model.animations.bouncingBalls.utils.CollisionResult;

public class CollisionEvent implements Comparable<CollisionEvent> {
	
	protected double t;
	protected boolean valid = true;
	
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
	//Return the collided particle
	public CollisionResult trigger() {
		return null;
	}

	@Override
	public String toString() {
		return "CollisionEvent [t=" + t + ", valid=" + valid + "]";
	}

	@Override
	public int compareTo(CollisionEvent col) {
		if(t < col.getTime())
			return -1;
		if(t > col.getTime())
			return 1;
		return 0;
	}
	
}
