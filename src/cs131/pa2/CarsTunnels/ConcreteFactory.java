package cs131.pa2.CarsTunnels;

import java.util.Collection;

import cs131.pa2.Abstract.Direction;
import cs131.pa2.Abstract.Factory;
import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Log.Log;

public class ConcreteFactory implements Factory {

    @Override
    public Tunnel createNewBasicTunnel(String label){
    		Tunnel tunnel = new BasicTunnel(label);
    		return tunnel;
    }

    @Override
    public Vehicle createNewCar(String label, Direction direction){
    		Car car = new Car(label,direction);
    		return car;
    }

    @Override
    public Vehicle createNewSled(String label, Direction direction){
    		Sled sled = new Sled(label,direction);
    		return sled;
    }

    @Override
    public Tunnel createNewPriorityScheduler(String label, Collection<Tunnel> tunnels, Log log){
    		throw new UnsupportedOperationException("Not supported yet.");
    }

	@Override
	public Vehicle createNewAmbulance(String label, Direction direction) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Tunnel createNewPreemptivePriorityScheduler(String label, Collection<Tunnel> tunnels, Log log) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
