package example.george.mina.themoviedb;

/**
 * Created by minageorge on 4/7/18.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import example.george.mina.themoviedb.customAdapters.FavoritesAdapter;
import example.george.mina.themoviedb.customAdapters.HomeAdapter;
import example.george.mina.themoviedb.data.MovieContract;
import example.george.mina.themoviedb.models.MovieDetailsModel;
import example.george.mina.themoviedb.tasks.VolleySingleton;


public class MainFragment extends Fragment {
    private HomeAdapter adapter;
    private GridLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private StringRequest stringRequest;
    private Toolbar toolbar, toolbar2;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private String url;
    private String TAG = MainFragment.class.getSimpleName();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private FavoritesAdapter favoritesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        preferences = getActivity().getSharedPreferences("currentConfg", Context.MODE_PRIVATE);
        editor = preferences.edit();
        adapter = new HomeAdapter(getActivity());
        favoritesAdapter = new FavoritesAdapter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.main_recycler_id);
        toolbar = getActivity().findViewById(R.id.toolbar_id);
        toolbar2 = getActivity().findViewById(R.id.toolbar_id2);
        collapsingToolbarLayout = getActivity().findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setVisibility(View.GONE);
        toolbar2.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (preferences.getInt("span", 0) == 1) {
            layoutManager = new GridLayoutManager(getActivity(), 1);
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        }
        adapter.setLayoutManager(layoutManager);
        favoritesAdapter.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (preferences.getString("listContent", "null").equals("null")) {
            url = "http://api.themoviedb.org/3/movie/popular?api_key=" + getActivity().getString(R.string.api_key);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_popular_title);
            getData();
        } else if (preferences.getString("listContent", "null").equals("fav")) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_fav_title);
            getFavorites();

        } else {
            url = "http://api.themoviedb.org/3/movie/" + preferences.getString("listContent", "null") + "?api_key=" + getActivity().getString(R.string.api_key);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_rate_title);
            getData();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        if (preferences.getInt("span", 0) == 1) {
            switchIcon(menu.findItem(R.id.action_span));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                toolbar.setTitle(R.string.menu_popular_title);
                url = "http://api.themoviedb.org/3/movie/popular?api_key=" + getActivity().getString(R.string.api_key);
                editor.putString("listContent", "popular").commit();
                getData();
                break;
            case R.id.action_rate:
                toolbar.setTitle(R.string.menu_rate_title);
                url = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + getActivity().getString(R.string.api_key);
                editor.putString("listContent", "top_rated").commit();
                getData();
                break;
            case R.id.action_favo:
                toolbar.setTitle(R.string.menu_fav_title);
                editor.putString("listContent", "fav").commit();
                getFavorites();
                break;
            case R.id.action_span:
                switchLayout();
                switchIcon(item);
                break;
            default:
                toolbar.setTitle(R.string.app_name);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchIcon(MenuItem item) {
        if (layoutManager.getSpanCount() >= 2) {
            item.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_view_headline_white_36dp));

        } else {
            item.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_view_module_white_36dp));
        }
    }

    private void switchLayout() {
        int currentState = layoutManager.getSpanCount();
        if (currentState == 1) {
            layoutManager.setSpanCount(2); ///->>>
        } else {
            layoutManager.setSpanCount(1);
        }
        adapter.notifyDataSetChanged();
        favoritesAdapter.notifyDataSetChanged();
    }

    public void getData() {
        {
            stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("results");
                                ArrayList<MovieDetailsModel> movies = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    MovieDetailsModel md = new MovieDetailsModel();
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    md.setMovieImag(jsonObject1.getString("poster_path"));
                                    md.setMovieTitle(jsonObject1.getString("original_title"));
                                    md.setMovieVote(jsonObject1.getString("vote_average"));
                                    md.setMovieOverView(jsonObject1.getString("overview"));
                                    md.setMovieDate(jsonObject1.getString("release_date"));
                                    md.setMovieBackdrop(jsonObject1.getString("backdrop_path"));
                                    md.setMovieLanguage(jsonObject1.getString("original_language"));
                                    md.setMovieId(jsonObject1.getString("id"));

                                    movies.add(md);
                                }
                                recyclerView.setAdapter(adapter);
                                adapter.swapData(movies);
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

    public void getFavorites() {
        recyclerView.setAdapter(favoritesAdapter);
        favoritesAdapter.setCursor(getActivity().getContentResolver().query(MovieContract.FavListEntry.CONTENT_URI
                , null, null, null
                , null));
    }
}

