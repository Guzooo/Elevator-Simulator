package pl.guzooo.elevatorsimulation.elevator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import pl.guzooo.elevatorsimulation.stop.ComparatorFloorDownDirection;
import pl.guzooo.elevatorsimulation.stop.ComparatorFloorUpDirection;
import pl.guzooo.elevatorsimulation.stop.StopInfo;

/**
 * A simple elevator that takes passengers traveling in one direction
 */
public class SimpleElevator implements Elevator {

    private final int STOPOVER = 3;

    private int id;
    private int currentFloor;
    private TreeSet<Integer> stops = new TreeSet<>();
    private TreeSet<Integer> otherStops = new TreeSet<>();  //when someone requested a stop in the opposite direction.
    private int direction = 0;
    private int stopover = 0;

    @Override
    public void initialization(int id, int currentFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
    }

    @Override
    public void update(int currentFloor, int destinationFloor) {
        this.currentFloor = currentFloor;
        otherStops.clear();
        stops.clear();
        stops.add(destinationFloor);
        direction = Integer.compare(destinationFloor, currentFloor);
        stopover = 0;
    }

    @Override
    public Integer[] getStatus() {
        int destinationFloor = getDestinationFloor();
        int countOfStops = stops.size();
        int doorIsOpen = stopover > 1 ? 1 : 0;
        return new Integer[] {id, currentFloor, destinationFloor, countOfStops, doorIsOpen};
    }

    @Override
    public int getTimeToFloor(StopInfo stop) {
        if(isOnTheWay(stop)){
            int timeTravel = getTimeBetweenFloors(currentFloor, stop.getFloor());
            int stopoverTime = stops.headSet(stop.getFloor()).size() * STOPOVER;
            return timeTravel + stopoverTime;
        }
        int timeToDestination = getTimeToDestination();
        int timeFromDestinationToOther = getTimeBetweenFloors(getDestinationFloor(), getOtherDestinationFloor());
        int otherStopoverTravel = otherStops.size() * STOPOVER;
        int timeFromOtherToFloor = getTimeBetweenFloors(getOtherDestinationFloor(), stop.getFloor());
        return timeToDestination + timeFromDestinationToOther
                + otherStopoverTravel + timeFromOtherToFloor;
    }

    @Override
    public int getTimeToDestination() {
        int timeTravel = getTimeBetweenFloors(currentFloor, getDestinationFloor());
        int stopoverTime = stops.size() * STOPOVER;
        return timeTravel + stopoverTime;
    }

    @Override
    public boolean pickup(StopInfo stop) {
        if(isOnTheWay(stop)) {
            stops.add(stop.getFloor());
            return true;
        } else if(stops.size() == 0){
            changeDirection(stop.getDirection());
            stops.add(stop.getFloor());
            return true;
        }
        return false;
    }

    @Override
    public void selectFloor(int floor) {
        if(isFloorOnTheWay(floor))
            stops.add(floor);
        else if(otherStops.size() != 0)
            otherStops.add(floor);
        else {
            Comparator<Integer> comparator = getComparator(direction *-1);
            otherStops = new TreeSet<>(comparator);
            otherStops.add(floor);
        }
    }

    @Override
    public void doStep() {
        if(stopover != 0){
            stopover--;
            return;
        }
        if(direction == 0)
            return;
        currentFloor += direction;
        if(stops.first() == currentFloor) {
            stopover = STOPOVER;
            stops.pollFirst();
            tryTakingBreak();
        }
    }

    private int getDestinationFloor(){
        if(stops.size() == 0 || direction == 0)
            return currentFloor;
        return stops.last();
    }

    private int getOtherDestinationFloor(){
        if(otherStops.size() == 0)
            return currentFloor;
        return stops.last();
    }

    private int getTimeBetweenFloors(int first, int second){
        int timeTravel = first - second;
        if(timeTravel < 0)
            timeTravel *= -1;
        return timeTravel;
    }

    private boolean isOnTheWay(StopInfo stop){
        if(stop.getDirection() != direction)
            return false;
        if(isFloorOnTheWay(stop.getFloor()))
            return false;
        return true;
    }

    private boolean isFloorOnTheWay(int floor){
        if((currentFloor - floor) * direction > 0)
            return false;
        return true;
    }

    private void tryTakingBreak(){
        if(stops.size() == 0 && otherStops.size() == 0){
            direction = 0;
        } else if(stops.size() == 0) {
            changeDirection(direction * -1);
            stops.addAll(otherStops);
            otherStops.clear();
        }
    }

    private void changeDirection(int direction){
        this.direction = direction;
        Comparator<Integer> comparator = getComparator(direction);
        stops = new TreeSet<>(comparator);
    }

    private Comparator<Integer> getComparator(int direction){
        if(direction == 1)
            return new ComparatorFloorUpDirection();
        else
            return new ComparatorFloorDownDirection();
    }
}
