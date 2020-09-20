package com.exchange.almulla;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setApp language
        setAppLocale();
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getToolbarName());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        initSideNav();
        initBottomNavBar();
    }

    private String getToolbarName() {
        return String.format("%s %s", getString(R.string.title_welcome), CommonMethodes.getPreferences(HomeActivity.this, CommonEnvironmentValues.USER_NAME, CommonEnvironmentValues.PREFTYPE_STRING));
    }

    private void initSideNav() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.side_nav_view);
        TextView change_lang =navigationView.findViewById(R.id.change_lang);
        TextView txt_logout =navigationView.findViewById(R.id.txt_logout);
        change_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLanguagesetting();
            }
        });
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

    }

    private void logOut() {
        
        FirebaseAuth.getInstance().signOut();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        GoogleSignInClient mGoogleApiClient = GoogleSignIn.getClient(this, gso);
        mGoogleApiClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                CommonMethodes.saveStringPreferences(HomeActivity.this,CommonEnvironmentValues.USER_NAME,"");
                CommonMethodes.saveBooleanPreferences(HomeActivity.this,CommonEnvironmentValues.KEY_REMEMBER_ME,false);
                goToSplashScreen();
            }
        });

    }

    private void goToSplashScreen() {
        Intent intent =new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    private void openLanguagesetting() {
        Intent intent =new Intent(this,ChangeLanguageActivity.class);
        startActivity(intent);
    }

    private void initBottomNavBar() {

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_calculator, R.id.nav_encryption, R.id.nav_todo,R.id.nav_stopWatch)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    //Change language from store preference
    private void setAppLocale(){

        String selectedLanguage = (String) CommonMethodes.getPreferences(this, CommonEnvironmentValues.SELECTED_LANGUAGE, CommonEnvironmentValues.PREFTYPE_STRING);
        //Toast.makeText(this, selectedLanguage, Toast.LENGTH_SHORT).show();
        if(selectedLanguage!=null && !selectedLanguage.isEmpty()){
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration config = resources.getConfiguration();
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
                config.setLocale(new Locale(selectedLanguage.toLowerCase()));
            } else {
                config.locale = new Locale(selectedLanguage.toLowerCase());
            }
            resources.updateConfiguration(config, dm);
        }

    }
}
