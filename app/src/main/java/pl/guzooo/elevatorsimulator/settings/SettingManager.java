package pl.guzooo.elevatorsimulator.settings;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import pl.guzooo.elevatorsimulation.system.SettingSystem;

public class SettingManager implements SettingSystem {

    Context context;

    public SettingSystem setContext(Context context){
        this.context = context;
        return this;
    }

    @Override
    public int getFirstFloor() {
        return getFirstFloor(context);
    }

    @Override
    public int getCountOfFloors() {
        return getCountOfFloors(context);
    }

    @Override
    public int getCountOfElevators() {
        return getInt("003", context);
    }

    public static int getFirstFloor(Context context){
        return getInt("001", context);
    }

    public static int getCountOfFloors(Context context){
        return getInt("002", context);
    }

    private static int getInt(String id, Context context){
        return getPref(context).getInt(id, 0);
    }

    private static SharedPreferences getPref(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
