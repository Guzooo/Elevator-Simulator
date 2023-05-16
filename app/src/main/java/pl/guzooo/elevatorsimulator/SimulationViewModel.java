package pl.guzooo.elevatorsimulator;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import pl.guzooo.elevatorsimulation.system.ElevatorSystem;
import pl.guzooo.elevatorsimulation.system.FactoryElevatorSystem;
import pl.guzooo.elevatorsimulator.settings.SettingManager;

public class SimulationViewModel extends AndroidViewModel {

    ElevatorSystem system;
    MutableLiveData<ArrayList<Integer[]>> status = new MutableLiveData<>();
    MutableLiveData<Boolean> devView = new MutableLiveData<>();

    public SimulationViewModel(Application application) {
        super(application);
        reset();
        devView.setValue(false);
    }

    public void reset(){
        system = FactoryElevatorSystem.getElevatorSystem(new SettingManager().setContext(getApplication()));
        status.setValue(system.status());
    }

    public void pickup(int floor, int direction){
        system.pickup(floor, direction);
    }

    public void selectFloor(int elevatorId, int floor){
        system.selectFloor(elevatorId, floor);
    }

    public void updateSystem(int elevatorId, int currentFloor, int destinationFloor){
        system.update(elevatorId, currentFloor, destinationFloor);
    }

    public void doSystemStep(){
        system.step();
        status.setValue(system.status());
    }

    public void switchDev(){
        devView.setValue(!devView.getValue());
    }

    public MutableLiveData<ArrayList<Integer[]>> getStatus(){
        return status;
    }

    public MutableLiveData<Boolean> getDevView(){
        return devView;
    }
}
