package example.george.mina.themoviedb;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by minageorge on 4/9/18.
 */

public class DetailsFragment extends Fragment {
    private Toolbar toolbar, toolbar2;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView drobackImage, posterImage;
    private TextView textViewName, textViewRate, textViewDate, textViewLanguage, textViewOverView;
    private String posterImageLink, backdropImageLink, name, rate, date, language, overview;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        posterImageLink = getArguments().getString("poster");
        backdropImageLink = getArguments().getString("backdrop");
        name = getArguments().getString("title");
        rate = getArguments().getString("rate");
        date = getArguments().getString("date");
        overview = getArguments().getString("overview");
        language = getArguments().getString("lang");


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbar_id2);
        toolbar2 = getActivity().findViewById(R.id.toolbar_id);
        collapsingToolbarLayout = getActivity().findViewById(R.id.collapsing_toolbar);
        drobackImage = getActivity().findViewById(R.id.image_backdrop);
        posterImage = getActivity().findViewById(R.id.image_poster);
        textViewName = getActivity().findViewById(R.id.textview_name);
        textViewDate = getActivity().findViewById(R.id.textview_date);
        textViewRate = getActivity().findViewById(R.id.textview_rate);
        textViewOverView = getActivity().findViewById(R.id.textview_overview);
        textViewLanguage = getActivity().findViewById(R.id.textview_language);
        collapsingToolbarLayout.setVisibility(View.VISIBLE);
        toolbar2.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w342/" + posterImageLink).into(posterImage);
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w342/" + backdropImageLink).into(drobackImage);
        textViewName.setText(name);
        textViewName.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Merriweather-BlackItalic.ttf"));
        textViewRate.setText(rate + " / 10");
        textViewDate.setText(date);
        textViewLanguage.setText(language);
        textViewOverView.setText(overview);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.action_share:
                Toast.makeText(getContext(), "share", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
