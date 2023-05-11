package pl.guzooo.elevatorsimulator.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import pl.guzooo.elevatorsimulator.R;
import pl.guzooo.elevatorsimulator.fullscreen.AllInsetsAgent;
import pl.guzooo.elevatorsimulator.fullscreen.FullScreenUtils;
import pl.guzooo.elevatorsimulator.fullscreen.InsetsAgent;

public class SettingsActivity extends AppCompatActivity {

    View content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialization();
        setFullScreen();
        setFragment();
    }

    private void initialization(){
        content = findViewById(R.id.content);
    }

    private void setFullScreen(){
        View main = findViewById(R.id.main);
        InsetsAgent agent = new AllInsetsAgent();
        FullScreenUtils.setInsets(this, main, agent);
    }

    private void setFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(content.getId(),  new SettingsFragment());
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.commit();
    }
}