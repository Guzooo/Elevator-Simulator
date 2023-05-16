package pl.guzooo.elevatorsimulation.system;

import java.util.ArrayList;

import pl.guzooo.elevatorsimulation.elevator.Elevator;
import pl.guzooo.elevatorsimulation.stop.StopInfo;

public class SimpleElevatorSystem implements ElevatorSystem {

    ArrayList<Elevator> elevators = new ArrayList<>();
    ArrayList<StopInfo> unallocatedStops = new ArrayList<>();
    ArrayList<Elevator> reservedElevators = new ArrayList<>();

    @Override
    public void pickup(int floor, int direction) {
        int cost = Integer.MAX_VALUE;
        int index = -1;
        StopInfo stop = new StopInfo(floor ,direction);
        for(int i = 0; i < elevators.size(); i++){
            Elevator e = elevators.get(i);
            int iCost = e.getTimeToDestination() * 3 + e.getTimeToFloor(stop);
            if(iCost < cost){
                cost = iCost;
                index = i;
            }
        }
        Elevator best = elevators.get(index);
        if(!best.pickup(stop)){
            unallocatedStops.add(stop);
            if(!reservedElevators.contains(best)) {
                reservedElevators.add(best);
                stop.setReservedElevator(reservedElevators.size()-1);
            }
        }
    }

    @Override
    public void selectFloor(int elevatorId, int floor) {
        findById(elevatorId).selectFloor(floor);
    }

    @Override
    public void update(int elevatorId, int currentFloor, int destinationFloor) {
        findById(elevatorId).update(currentFloor, destinationFloor);
    }

    @Override
    public void step() {
        for(Elevator e : elevators)
            e.doStep();
        int countOfReservedElevators = reservedElevators.size();
        int unreservedElevators = 0;
        for(int i = 0; i < countOfReservedElevators; i++){
            StopInfo stop = unallocatedStops.get(i);
            int reserved = stop.getReservedElevator() - unreservedElevators;
            if(reserved != -1){
                Elevator reservedElevator = reservedElevators.get(reserved);
                if(reservedElevator.pickup(stop)){
                    unallocatedStops.remove(i);
                    reservedElevators.remove(reservedElevator);
                    i--;
                    countOfReservedElevators--;
                    unreservedElevators++;
                } else {
                    stop.setReservedElevator(reserved);
                }
            } else if(unreservedElevators != 0){
                unallocatedStops.remove(i);
                i--;
                countOfReservedElevators--;
                pickup(stop.getFloor(), stop.getDirection());
            }
        }
    }

    @Override
    public ArrayList<Integer[]> status() {
        ArrayList<Integer[]> statuses = new ArrayList<>();
        for(Elevator e : elevators)
            statuses.add(e.getStatus());
        return statuses;

    }

    private Elevator findById(int id){
        for(Elevator e : elevators)
            if(e.getStatus()[0] == id)
                return e;
        return null;
    }
}
