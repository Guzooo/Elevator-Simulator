package pl.guzooo.elevatorsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import pl.guzooo.elevatorsimulator.readme.ReadmeActivity;
import pl.guzooo.elevatorsimulator.settings.SettingsActivity;

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

    private void togglePlayPause(MenuItem item){
        AnimatedVectorDrawable anim = (AnimatedVectorDrawable) item.getIcon();
        anim.start();
    }

    private void openSettings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void openReadme(){
        Intent intent = new Intent(this, ReadmeActivity.class);
        startActivity(intent);
    }
}