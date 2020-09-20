package com.exchange.almulla;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ChangeLanguageActivity extends AppCompatActivity {

    private Button btn_save;
    private RadioButton radio_english;
    private RadioButton radio_arbic;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);
        initControlls();
    }


    private void initControlls() {
        btn_save = (Button) findViewById(R.id.btn_save);
        radio_english = (RadioButton) findViewById(R.id.radio_english);
        radio_arbic = (RadioButton) findViewById(R.id.radio_arbic);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLang();
            }
        });

    }

    private void changeLang() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radio_english) {
            setAppLocale(CommonEnvironmentValues.ENGLISH);
        } else if (selectedId == R.id.radio_arbic) {
            setAppLocale(CommonEnvironmentValues.ARABIC);
        }

    }

    private void setAppLocale(String localeCode) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
        CommonMethodes.saveStringPreferences(ChangeLanguageActivity.this, CommonEnvironmentValues.SELECTED_LANGUAGE, localeCode);

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
