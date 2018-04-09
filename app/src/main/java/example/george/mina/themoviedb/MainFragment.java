package example.george.mina.themoviedb;

/**
 * Created by minageorge on 4/7/18.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainFragment extends Fragment {
    private HomeMoviesAdapter adapter;
    private GridLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private StringRequest stringRequest;
    private Toolbar toolbar;
    private String url;
    private String TAG = MainFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        recyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_id);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_id);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        adapter = new HomeMoviesAdapter(getActivity());
        adapter.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        url = "http://api.themoviedb.org/3/movie/popular?api_key=" + getActivity().getString(R.string.api_key);
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                toolbar.setTitle(R.string.menu_popular_title);
                url = "http://api.themoviedb.org/3/movie/popular?api_key=" + getActivity().getString(R.string.api_key);
                getData();
                break;
            case R.id.action_rate:
                toolbar.setTitle(R.string.menu_rate_title);
                url = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + getActivity().getString(R.string.api_key);
                getData();
                break;
            case R.id.action_favo:
                toolbar.setTitle(R.string.menu_fav_title);
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
            Log.d("dddd", "1111");
        }
    }

    private void switchLayout() {
        int currentState = layoutManager.getSpanCount();
        if (currentState == 1) {
            layoutManager.setSpanCount(2); ///->>>
        } else {
            layoutManager.setSpanCount(1);
            Log.d("dddd", "1222");
        }
        adapter.notifyDataSetChanged();
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
                                ArrayList<MovieDetails> movies = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    MovieDetails md = new MovieDetails();
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    md.setMovieImag(jsonObject1.getString("poster_path"));
                                    md.setMovieTitle(jsonObject1.getString("original_title"));
                                    md.setMovieVote(jsonObject1.getString("vote_average"));
                                    md.setMovieOverView(jsonObject1.getString("overview"));
                                    md.setMovieDate(jsonObject1.getString("release_date"));
                                    movies.add(md);
                                }
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
}

