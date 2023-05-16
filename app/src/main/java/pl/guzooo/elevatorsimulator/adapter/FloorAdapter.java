package pl.guzooo.elevatorsimulator.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import pl.guzooo.elevatorsimulator.R;

public class FloorAdapter extends ListAdapter<Integer[], FloorViewHolder> {

    private FloorListener listener;
    private int firstFloor;
    private int countOfFloor;

    public interface FloorListener {
        void pickup(int floor, int direction);
        void elevator(int id);
    }

    public FloorAdapter(int firstFloor, int countOfFloor, FloorListener listener) {
        super(getDiffCallback());
        this.firstFloor = firstFloor;
        this.countOfFloor = countOfFloor;
        this.listener = listener;
    }

    @Override
    public FloorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_floor, parent, false);
        return new FloorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FloorViewHolder holder, int position) {
        int currentFloor = firstFloor + countOfFloor - position -1;
        holder.setTitle(currentFloor);
        int countOfElevators = super.getItemCount();
        for(int i = 0; i < countOfElevators; i++)
            holder.setElevators(i, getItem(i));
        for(int i = countOfElevators; i < 16; i++)
            holder.setElevators(i, null);
        holder.setFloorListener(currentFloor, listener);
    }

    @Override
    public int getItemCount() {
        return countOfFloor;
    }

    private static DiffUtil.ItemCallback<Integer[]> getDiffCallback() {
        return new DiffUtil.ItemCallback<Integer[]>() {

            @Override
            public boolean areItemsTheSame(Integer[] oldItem, Integer[] newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(Integer[] oldItem, Integer[] newItem) {
                return false;
            }
        };
    }
}
