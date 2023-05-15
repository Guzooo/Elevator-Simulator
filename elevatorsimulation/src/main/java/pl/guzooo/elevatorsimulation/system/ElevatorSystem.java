package pl.guzooo.elevatorsimulation.system;

import java.util.ArrayList;

public interface ElevatorSystem {

    /**
     * Calling elevator
     *
     * @param floor call floor
     * @param direction where we want to go;
     *                  > 0 - up;
     *                  < 0 - down
     */
    void pickup(int floor, int direction);

    /**
     * Select floor in elevator
     *
     * @param elevatorId the elevator we are in
     * @param floor selected floor
     */
    void selectFloor(int elevatorId, int floor);
    void update(int elevatorId, int currentFloor, int destinationFloor);
    void step();

    /**
     * @return (elevatorId, currentFloor, destinationFloor, countOfStop, openDoor);
     *          openDoor: 1 - when open; 0 - when close;
     */
    ArrayList<Integer[]> status();
}
