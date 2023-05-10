package pl.guzooo.elevatorsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SimulationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_simulation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.play_pause)
            togglePlayPause(item);
        else if(id == R.id.info)
            openReadme();
        else if(id == R.id.settings)
            openSettings();
        else
            return super.onOptionsItemSelected(item);
        return true;
    }

    boolean p = false;
    private void togglePlayPause(MenuItem item){
        AnimatedVectorDrawable anim;
        if(p)
            anim = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.anim_pause_to_play, null);
        else
            anim = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.anim_play_to_pause, null);
        item.setIcon(anim);
        anim.start();
        p = !p;
    }

    private void openSettings(){

    }

    private void openReadme(){

    }
}