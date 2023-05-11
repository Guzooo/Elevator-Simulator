package pl.guzooo.elevatorsimulator.settings;

import android.os.Bundle;
import android.text.InputType;

import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

import pl.guzooo.elevatorsimulator.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey);

        //TODO: zabezpieczyć, zeby pokazywało tyle ile trzeba
    }



    private EditTextPreference.OnBindEditTextListener getBindEditTextToInt(){
        return editText -> {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setSelection(editText.length());
        };
    }
}
