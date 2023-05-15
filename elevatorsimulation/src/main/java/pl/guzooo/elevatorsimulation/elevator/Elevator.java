package pl.guzooo.elevatorsimulation.elevator;

import pl.guzooo.elevatorsimulation.stop.StopInfo;

public interface Elevator {

    void initialization(int id, int currentFloor);
    void update(int currentFloor, int destinationFloor);

    /**
     * @return (elevatorId, currentFloor, destinationFloor, countOfStop, openDoor);
     *          openDoor: 1 - when open; 0 - when close;
     */
    Integer[] getStatus();
    int getTimeToFloor(StopInfo stop);
    int getTimeToDestination();
    boolean pickup(StopInfo stop);
    void selectFloor(int floor);
    void doStep();

}
