package pl.guzooo.elevatorsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import pl.guzooo.elevatorsimulator.fullscreen.AllInsetsAgent;
import pl.guzooo.elevatorsimulator.fullscreen.FullScreenUtils;
import pl.guzooo.elevatorsimulator.readme.ReadmeActivity;
import pl.guzooo.elevatorsimulator.settings.SettingsActivity;
import pl.guzooo.elevatorsimulator.utils.TextUtils;

public class SimulationActivity extends AppCompatActivity {

    private SimulationViewModel viewModel;
    private RecyclerView recyclerView;
    private TextView devInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        initialization();
        setFullScreen();
        setVersion();
        setDev();
        setReset();
        setDevInfo();
        setRecyclerView();
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
            doStep(item);
        else if(id == R.id.info)
            openReadme();
        else if(id == R.id.settings)
            openSettings();
        else
            return super.onOptionsItemSelected(item);
        return true;
    }

    private void initialization(){
        viewModel = new ViewModelProvider(this).get(SimulationViewModel.class);
        devInfo = findViewById(R.id.dev_info);
        recyclerView = findViewById(R.id.recycler);
    }

    private void setFullScreen(){
        View main = findViewById(R.id.main);
        FullScreenUtils.setInsets(this, main, new AllInsetsAgent());
    }

    private void setVersion(){
        TextView tv = findViewById(R.id.version_text);
        String s = TextUtils.getVersion(this);
        tv.setText(s);

    }

    private void setDev(){
        //View dev = findViewById(R.id.dev_image);
    }

    private void setReset(){
        View reset = findViewById(R.id.reset_button);
        reset.setOnClickListener(view -> viewModel.reset());
    }

    private void setDevInfo(){
        viewModel.getStatus().observe(this, array -> {
            String s = getString(R.string.state_title);
            for(Integer[] i : array)
                s += getString(R.string.state_data, i[0], i[1], i[2], i[3], i[4]);
            devInfo.setText(s);
        });
    }

    private void setRecyclerView(){

    }

    private void doStep(MenuItem item){
        AnimatedVectorDrawable anim = (AnimatedVectorDrawable) item.getIcon();
        anim.start();
        viewModel.doSystemStep();
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