package pl.guzooo.elevatorsimulation.system;

import java.util.ArrayList;

import pl.guzooo.elevatorsimulation.elevator.Elevator;

public interface InitializationByFactory {

    void addElevator(ArrayList<Elevator> elevators);
    ElevatorSystem getSystem();
}
