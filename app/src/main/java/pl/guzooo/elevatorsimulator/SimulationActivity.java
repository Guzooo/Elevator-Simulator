package pl.guzooo.elevatorsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import pl.guzooo.elevatorsimulator.adapter.FloorAdapter;
import pl.guzooo.elevatorsimulator.fullscreen.AllInsetsAgent;
import pl.guzooo.elevatorsimulator.fullscreen.FullScreenUtils;
import pl.guzooo.elevatorsimulator.readme.ReadmeActivity;
import pl.guzooo.elevatorsimulator.settings.SettingManager;
import pl.guzooo.elevatorsimulator.settings.SettingsActivity;
import pl.guzooo.elevatorsimulator.utils.TextUtils;

public class SimulationActivity extends AppCompatActivity {

    private SimulationViewModel viewModel;
    private RecyclerView recyclerView;
    private FloorAdapter adapter;
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
        setButtons();
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
        View dev = findViewById(R.id.dev_image);
        dev.setOnClickListener(view -> viewModel.switchDev());
        viewModel.getDevView().observe(this, isDevView -> {
            if(isDevView){
                devInfo.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            } else {
                devInfo.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setReset(){
        View reset = findViewById(R.id.reset_button);
        reset.setOnClickListener(view -> {
            viewModel.reset();
            setRecyclerView();
        });
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
        FloorAdapter.FloorListener listener = getFloorListener();
        adapter = new FloorAdapter(SettingManager.getFirstFloor(this), SettingManager.getCountOfFloors(this), listener);
        viewModel.getStatus().observe(this, array -> {
            Integer[][] fake = new Integer[SettingManager.getCountOfFloors(this)][];
            ArrayList<Integer[]> fakeA = new ArrayList<>(Arrays.asList(fake));
            adapter.submitList(fakeA);
            adapter.refreshElevators(array);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setButtons(){
        findViewById(R.id.update).setOnClickListener(view -> {
            Integer[] arguments = getArgumentsFromEditText();
            if(arguments.length < 3)
                return;
            viewModel.updateSystem(arguments[0], arguments[1], arguments[2]);
        });
        findViewById(R.id.select).setOnClickListener(view -> {
            Integer[] arguments = getArgumentsFromEditText();
            if(arguments.length < 2)
                return;
            viewModel.selectFloor(arguments[0], arguments[1]);

        });
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

    private FloorAdapter.FloorListener getFloorListener(){
        return new FloorAdapter.FloorListener() {
            @Override
            public void pickup(int floor, int direction) {
                viewModel.pickup(floor, direction);
            }

            @Override
            public void elevator(int id) {
                //TODO: pokaż szczegoły windy
            }
        };
    }

    private Integer[] getArgumentsFromEditText(){
        EditText editText = findViewById(R.id.edit);
        String str = editText.getText().toString().trim();
        String[] strs = str.split(";");
        Integer[] integers = new Integer[strs.length];
        for(int i = 0; i < strs.length; i++)
            integers[i] = Integer.valueOf(strs[i]);
        return integers;
    }
}