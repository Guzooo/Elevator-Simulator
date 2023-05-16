package pl.guzooo.elevatorsimulator.views;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import pl.guzooo.elevatorsimulator.R;

public class ElevatorView extends FrameLayout {

    ImageView image;
    TextView text;

    int direction = 0;

    public ElevatorView (Context context, AttributeSet attrs){
        super(context, attrs);
        initialization();
    }

    private void initialization(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.elevator, this, true);

        image = findViewById(R.id.images);
        text = findViewById(R.id.text);
    }

    public void setId(int id){
        String idStr = String.valueOf(id);
        text.setText(idStr);
    }

    public void animCome(int direction){
        image.setVisibility(VISIBLE);
        if(direction > 0)
            anim(R.drawable.anim_elevator_come_up);
        else if (direction < 0)
            anim(R.drawable.anim_elevator_come_down);
        else
            image.setImageResource(R.drawable.ic_elevator);
    }

    public void animLeave() {
        animLeave(direction);
    }

    public void animLeave(int direction){
        if(direction > 0)
            anim(R.drawable.anim_elevator_leave_up);
        else if (direction < 0)
            anim(R.drawable.anim_elevator_leave_down);
        else
            image.setVisibility(INVISIBLE);
    }

    public void animOpen() {
        if (direction > 0)
            anim(R.drawable.anim_elevator_open_up);
        else if (direction < 0)
            anim(R.drawable.anim_elevator_open_down);
        else
            anim(R.drawable.anim_elevator_open);

    }

    public void animClose(){
        anim(R.drawable.anim_elevator_close);
    }

    private void anim(int resourceId){
        AnimatedVectorDrawable anim = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), resourceId, null);
        image.setImageDrawable(anim);
        anim.start();
    }
}
