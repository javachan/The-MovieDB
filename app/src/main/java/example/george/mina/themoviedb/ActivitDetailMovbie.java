package example.george.mina.themoviedb;

/**
 * Created by minageorge on 4/7/18.
 */

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ActivitDetailMovbie extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activit_detail_movbie);

        ActivitDetailMovbieFragment mainFragment = new ActivitDetailMovbieFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentt, mainFragment, "").commit();


    }

}
