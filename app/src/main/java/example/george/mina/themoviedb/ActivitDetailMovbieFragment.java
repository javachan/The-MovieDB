package example.george.mina.themoviedb;

/**
 * Created by minageorge on 4/7/18.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class ActivitDetailMovbieFragment extends Fragment {
    TextView    title,releaseDate,voteAverage,dsccription;
    String       titletxt,releaseDatetxt,voteAveragetxt,dsccriptiontxt,imgtxt;
    ImageView   imageView;
    public ActivitDetailMovbieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_activit_detail_movbie, container, false);

        title = (TextView) view.findViewById(R.id.movieMame);
        releaseDate = (TextView) view.findViewById(R.id.date);
        imageView = (ImageView) view.findViewById(R.id.imag);
        voteAverage = (TextView) view.findViewById(R.id.vote);
        dsccription = (TextView) view.findViewById(R.id.description);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("MOVIE DETAIL");

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent n = getActivity().getIntent();
        titletxt = n.getExtras().getString("original_title");
        releaseDatetxt = n.getExtras().getString("release_date");
        voteAveragetxt = n.getExtras().getString("vote_average");
        dsccriptiontxt = n.getStringExtra("overview");
        imgtxt = n.getStringExtra("poster_path");

        title.setText(titletxt);
        voteAverage.setText(voteAveragetxt + "/10");
        releaseDate.setText(releaseDatetxt);
        dsccription.setText(dsccriptiontxt);

        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" + imgtxt).into(imageView);

        return view;

    }
}
