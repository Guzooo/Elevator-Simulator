package pl.guzooo.elevatorsimulator.adapter;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import pl.guzooo.elevatorsimulator.R;
import pl.guzooo.elevatorsimulator.views.ElevatorView;

public class FloorViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private ElevatorView[] elevators = new ElevatorView[16];
    private ImageView up;
    private ImageView down;

    public FloorViewHolder(View v){
        super(v);
        title = v.findViewById(R.id.title);
        initializationElevators(v);
        up = v.findViewById(R.id.up);
        down = v.findViewById(R.id.down);
    }

    public void setTitle(int floor) {
        String s = ((View) title).getResources().getString(R.string.floor, floor);
        title.setText(s);
    }

    public void setFloorListener(int floor, FloorAdapter.FloorListener listener){
        up.setOnClickListener(view -> {
            listener.pickup(floor, 1);
            startAnim(up);
        });
        down.setOnClickListener(view -> {
            listener.pickup(floor, -1);
            startAnim(down);
        });
        for(ElevatorView e : elevators)
            e.setOnClickListener(view -> {
                listener.elevator(e.getId());
            });
    }

    public void setElevators(int i, Integer[] tab){
        if(tab == null) {
            elevators[i].setVisibility(View.INVISIBLE);
            return;
        }
        elevators[i].setVisibility(View.VISIBLE);
        elevators[i].setId(tab[0]);
        //TODO: miejsce na animacje

    }

    private void initializationElevators(View main){
        ViewGroup viewGroup = main.findViewById(R.id.elevators);
        for(int i = 0; i < elevators.length; i++) {
            ElevatorView e = (ElevatorView) viewGroup.getChildAt(i);
            elevators[i] = e;
        }
    }

    private void startAnim(ImageView imageView){
        AnimatedVectorDrawable anim = (AnimatedVectorDrawable) imageView.getDrawable();
        anim.start();
    }
}
