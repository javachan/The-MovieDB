package example.george.mina.themoviedb;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import example.george.mina.themoviedb.customAdapters.MovieReviewAdapter;
import example.george.mina.themoviedb.customAdapters.MovieTrailerAdapter;
import example.george.mina.themoviedb.data.MovieContract;
import example.george.mina.themoviedb.models.MovieReviewModel;
import example.george.mina.themoviedb.tasks.VolleySingleton;

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
    private View vieww;
    private ContentResolver resolver = null;
    private boolean isInFavoriteList = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        trailerAdapter = new MovieTrailerAdapter(getActivity());
        reviewAdapter = new MovieReviewAdapter();
        resolver = getActivity().getContentResolver();
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
        init();
        if (checkFavList()) {
            isInFavoriteList = true;
        }
        getMovieTrailers();
        getMovieReviews();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_details, menu);
        MenuItem menuItem = menu.findItem(R.id.action_favo);
        if (isInFavoriteList) {
            menuItem.setIcon(getResources().getDrawable(R.drawable.ic_favorite));
        }
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
                if (isInFavoriteList) {
                    removeFromFavList(item);
                } else {
                    addToFavList(item);
                }
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
                        Toast.makeText(getActivity(), R.string.check_your_device_connection, Toast.LENGTH_SHORT).show();
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

    private void addToFavList(MenuItem item) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.FavListEntry.COL_ID, id);
        values.put(MovieContract.FavListEntry.COL_POSTER, posterImageLink);
        values.put(MovieContract.FavListEntry.COL_BACKDROP, backdropImageLink);
        values.put(MovieContract.FavListEntry.COL_DATE, date);
        values.put(MovieContract.FavListEntry.COL_RATE, rate);
        values.put(MovieContract.FavListEntry.COL_OVERVIEW, overview);
        values.put(MovieContract.FavListEntry.COL_TITLE, name);
        values.put(MovieContract.FavListEntry.COL_LANGUAGE, language);
        Uri uri = resolver.insert(MovieContract.BASE_CONTENT_URI, values);
        if (uri != null) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_favorite));
            Snackbar.make(vieww, "Added To Favorites List", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(vieww, "Added To Your Favorites List", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void removeFromFavList(final MenuItem item) {
        int x = resolver.delete(MovieContract.FavListEntry.CONTENT_URI.buildUpon()
                .appendPath(id).build(), null, null);
        Snackbar snackbar = Snackbar.make(vieww, "Removed From Favorites List", Snackbar.LENGTH_SHORT);
        if (x != 0) {

            snackbar.setDuration(3000);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addToFavList(item);
                }
            });
            snackbar.show();
            item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_border_white_36dp));
            isInFavoriteList = false;
        }
    }

    private boolean checkFavList() {
        Cursor cursor = resolver.query(MovieContract.FavListEntry.CONTENT_URI.buildUpon()
                        .appendPath(id).build(), null, null, null
                , null);
        return cursor.getCount() == 1;
    }

    private void init() {
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
        vieww = getActivity().findViewById(R.id.fragment_details);
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
    }

}
