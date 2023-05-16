package pl.guzooo.elevatorsimulation.system;

import java.util.ArrayList;

import pl.guzooo.elevatorsimulation.elevator.Elevator;
import pl.guzooo.elevatorsimulation.elevator.SimpleElevator;

public class FactoryElevatorSystem {

    public static ElevatorSystem getElevatorSystem(SettingSystem setting){
        InitializationByFactory initialization = new SimpleElevatorSystem();
        addElevators(initialization, setting);
        return initialization.getSystem();
    }

    private static void addElevators(InitializationByFactory initialization, SettingSystem setting){
        int count = setting.getCountOfElevators();
        int firstFloor = setting.getFirstFloor();
        ArrayList<Elevator> elevators = new ArrayList<>();
        for(int i = 0; i < count; i++){
            elevators.add(new SimpleElevator().initialization(300 + i*2 + 5, firstFloor));
        }
        initialization.addElevator(elevators);
    }
}
