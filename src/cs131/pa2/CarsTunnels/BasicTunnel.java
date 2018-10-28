package cs131.pa2.CarsTunnels;

import cs131.pa2.Abstract.Direction;
import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;

public class BasicTunnel extends Tunnel{
		int car = 0;
		int sled = 0;
		Direction direction = null;
		
		
	public BasicTunnel(String name) {
		super(name);
	}

	@Override
	public synchronized boolean tryToEnterInner(Vehicle vehicle) {
		if(car==0 && sled==0) {
			if(vehicle instanceof Car) {
				car++;
			}else {
				sled++;
			}
			direction = vehicle.getDirection();
			return true;
		}else if(vehicle instanceof Sled) {
			return false;
			
		}else if(car<3 && sled==0) {
			if(vehicle.getDirection().equals(direction)) {
				car++;
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
		
		

	@Override
	public synchronized void exitTunnelInner(Vehicle vehicle) {
		if(vehicle instanceof Car) {
			car--;
		}else {
			sled--;
		}
		if(car==0 && sled==0) {
			direction=null;
		}
	}
	
}
