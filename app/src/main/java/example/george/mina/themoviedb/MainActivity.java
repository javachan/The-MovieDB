package example.george.mina.themoviedb;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
    private FragmentTransaction ft;
    private FragmentManager fm;
    private String mainFragmentTAG, detailsFargmentTAG;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        mainFragmentTAG = MainFragment.class.getSimpleName();
        detailsFargmentTAG = DetailsFragment.class.getSimpleName();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.addToBackStack(mainFragmentTAG);
        ft.replace(R.id.fragment_content, new MainFragment(), mainFragmentTAG).commit();

    }

    @Override
    public void onBackPressed() {
        if (fm.findFragmentByTag(mainFragmentTAG).isAdded()) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}

