package example.george.mina.themoviedb;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by minageorge on 4/9/18.
 */

public class DetailsFragment extends Fragment {
    private Toolbar toolbar, toolbar2;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView drobackImage, posterImage;
    private RecyclerView trailersRecyclerView, reviewsRecyclerView;
    private TextView textViewName, textViewRate, textViewDate, textViewLanguage, textViewOverView;
    private String posterImageLink, backdropImageLink, name, rate, date, language, overview, id;
    private StringRequest stringRequest;
    private String TAG = DetailsFragment.class.getSimpleName();
    private MovieTrailerAdapter trailerAdapter;
    private MovieReviewAdapter reviewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        trailerAdapter = new MovieTrailerAdapter(getActivity());
        reviewAdapter = new MovieReviewAdapter();
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
        id = getArguments().getString("id");
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
        trailersRecyclerView = getActivity().findViewById(R.id.recycler_trailers);
        reviewsRecyclerView = getActivity().findViewById(R.id.recycler_reviews);
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
        trailersRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        trailersRecyclerView.setAdapter(trailerAdapter);
        reviewsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        reviewsRecyclerView.setAdapter(reviewAdapter);
        getMovieTrailers();
        getMovieReviews();
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
                ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setChooserTitle("Share with ..")
                        .setText("https://www.themoviedb.org/movie/" + id)
                        .startChooser();
                break;
            case R.id.action_favo:

                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void getMovieTrailers() {
        String url = "http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + getString(R.string.api_key);
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            ArrayList<String> trailer = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                trailer.add(jsonObject1.getString("key"));
                            }
                            trailerAdapter.swapData(trailer);
                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                });
        VolleySingleton.getInstance(getActivity()).addRequestQue(stringRequest);

    }

    private void getMovieReviews() {
        String url = "http://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=" + getString(R.string.api_key);
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            ArrayList<MovieReviewModel> reviews = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                MovieReviewModel mr = new MovieReviewModel();
                                mr.setAuthor(jsonObject1.getString("author"));
                                mr.setContent(jsonObject1.getString("content"));

                                reviews.add(mr);
                            }
                            reviewAdapter.swapData(reviews);
                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                });
        VolleySingleton.getInstance(getActivity()).addRequestQue(stringRequest);
    }

}
