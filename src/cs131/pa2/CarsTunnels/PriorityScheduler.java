package cs131.pa2.CarsTunnels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Log.Log;

public class PriorityScheduler extends Tunnel{
	private Collection<Tunnel> 	tunnels = new ArrayList<Tunnel>();
	private ArrayList<Vehicle> waiting = new ArrayList<Vehicle>();
	private HashMap<BasicTunnel, ArrayList<Vehicle>> tunnelToVehicle = new HashMap<BasicTunnel, ArrayList<Vehicle>>();
	private int tunnelNumber = 0;
	private Lock lock = new ReentrantLock();
	private Condition tunnelNotEmpty = lock.newCondition();
	//create priority queue for waiting vehicles
//	private int maxPriority = 0;
	
	
	
	public PriorityScheduler(String name,Collection<Tunnel> tunnels, Log log) {
		super(name);
		this.tunnels = tunnels;
		
	}

	@Override
	public boolean tryToEnterInner(Vehicle vehicle) {
//		lock.lock();
		int maxPriority=0;
		for(Vehicle v : waiting) {
			if(v.getPriority()>maxPriority) {
				maxPriority = v.getPriority();
			}
		}
//		while(vehicle.getPriority() < maxPriority ||  !canEnter(vehicle)) {
//			try {
//				tunnelNotEmpty.await();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		if(vehicle.getPriority()<maxPriority) {
			waiting.add(vehicle);
			return false;
		}else {
//			if(tunnels.size()<1) {
//				BasicTunnel tunnel = new BasicTunnel(""+tunnelNumber++);
//				tunnels.add(tunnel);
//				tunnelToVehicle.put(tunnel, new ArrayList<Vehicle>());
//			}
			for(Tunnel tunnel : tunnels) {
				if(tunnel.tryToEnter(vehicle)) {
					if(tunnelToVehicle.get(tunnel) != null) {
						tunnelToVehicle.get(tunnel).add(vehicle);
					}
					else{
						tunnelToVehicle.put((BasicTunnel)tunnel, new ArrayList<Vehicle>());
						tunnelToVehicle.get(tunnel).add(vehicle);
					}
					waiting.remove(vehicle);
					return true;
				}
				
			}
			
			waiting.add(vehicle);
			return false;
		}
//		for(Tunnel tunnel : tunnels) {
//			if(tunnel.tryToEnter(vehicle)) {
//				if(tunnelToVehicle.get(tunnel) != null) {
//					tunnelToVehicle.get(tunnel).add(vehicle);
//				}
//				else{
//					tunnelToVehicle.put((BasicTunnel)tunnel, new ArrayList<Vehicle>());
//					tunnelToVehicle.get(tunnel).add(vehicle);
//				}
//				lock.unlock();
//				return true;
//			}
//			
//		}
//		lock.unlock();
//		return false;
		

	}
	private boolean canEnter(Vehicle vehicle) {
		for(Tunnel tunnel : tunnels) {
			if(tunnel.tryToEnter(vehicle)) {
				if(tunnelToVehicle.get(tunnel) != null) {
					tunnelToVehicle.get(tunnel).add(vehicle);
				}
				else{
					tunnelToVehicle.put((BasicTunnel)tunnel, new ArrayList<Vehicle>());
					tunnelToVehicle.get(tunnel).add(vehicle);
				}
				return true;
			}
			
		}
		return false;
	}
	@Override
	public void exitTunnelInner(Vehicle vehicle) {
//		lock.lock();
		int maxPriority =0;
		Vehicle v =null;
		for(Tunnel t: tunnels) {
			int counter=0;
			for(Vehicle vArray: tunnelToVehicle.get(t)) {
				counter++;
				if(vArray.equals(vehicle)) {
					t.exitTunnel(vehicle);
					tunnelToVehicle.get(t).remove(counter);
					for(Vehicle waitingVehicle : waiting) {
						if(waitingVehicle.getPriority()>maxPriority) {
							v = waitingVehicle;
						}
					}
					t.tryToEnter(v);
//					tunnelNotEmpty.signalAll();	
				}
			}		
		}
//		lock.unlock();
		waiting.add(v);
	}
	
}
