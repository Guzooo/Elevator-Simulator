package pl.guzooo.elevatorsimulation.stop;

public class StopInfo {

    /**
     * floor where elevator must stop
     */
    private final int floor;

    /**
     * the direction in which the elevator must travel after the stop.
     */
    private final int direction;

    /**
     * id of elevator that has lower cost; -1 if uninitialized
     */
    private int reservedElevator = -1;

    public StopInfo(int floor, int direction){
        this.floor = floor;
        this.direction = direction;
    }

    public int getFloor() {
        return floor;
    }

    public int getDirection() {
        return direction;
    }

    public void setReservedElevator(int reservedElevator) {
        this.reservedElevator = reservedElevator;
    }

    public int getReservedElevator(){
        return reservedElevator;
    }
}
