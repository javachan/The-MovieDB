package example.george.mina.themoviedb;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    FragmentTransaction ft ;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = new MainFragment();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.addToBackStack(MainFragment.class.getSimpleName());
        ft.replace(R.id.fragment_content, mainFragment,
                MainFragment.class.getSimpleName()).commit();

    }

    @Override
    public void onBackPressed() {
        if (fm.findFragmentByTag(MainFragment.class.getSimpleName()).isAdded()){
            finish();
        }else {
            super.onBackPressed();
        }

    }
}

