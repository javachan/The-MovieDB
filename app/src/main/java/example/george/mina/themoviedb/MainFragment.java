package example.george.mina.themoviedb;

/**
 * Created by minageorge on 4/7/18.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private ArrayList<MoviesItemModel> moviesItem = new ArrayList<>();
    private DetailItem[] detailItems;
    private String posterpath, title, vote, description, date, year;
    private RecyclerView recyclerView;
    private String parmarity = "";
    private StringRequest stringRequest;
    private Toolbar toolbar;


    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        parmarity = sharedPreferences.getString(getString(R.string.sort_key), "popular");
        getdata();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_id);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
                break;
            case R.id.action_rate:
                toolbar.setTitle(R.string.menu_rate_title);
                break;
            case R.id.action_favo:
                toolbar.setTitle(R.string.menu_fav_title);
                break;
            case R.id.action_span:

                break;
            default:
                toolbar.setTitle(R.string.app_name);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getdata() {
        {
            String url = "http://api.themoviedb.org/3/movie/" + parmarity + "?api_key=7826714bce33155200adb2a059306594";
            stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            try {
                                Log.i("data", response);

                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("results");
                                detailItems = new DetailItem[jsonArray.length()];
                                if (moviesItem != null) {
                                    moviesItem.clear();
                                }
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    moviesItem.add(new MoviesItemModel(jsonObject1.getString("poster_path")));

                                    title = jsonObject1.getString("original_title");

                                    vote = jsonObject1.getString("vote_average");

                                    description = jsonObject1.getString("overview");
                                    date = jsonObject1.getString("release_date");

                                    posterpath = jsonObject1.getString("poster_path");
                                    Log.i("mhmod", title + vote + description + date + posterpath);
                                    for (int j = 0; i < j; j++) {
                                        year += date.charAt(j);
                                    }
                                    detailItems[i] = new DetailItem(posterpath, title, vote, description, date);


                                }

                            } catch (Exception e) {

                            }

                            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                            HomeMoviesAdapter homeMoviesAdapter = new HomeMoviesAdapter(getActivity(), moviesItem, detailItems);
                            recyclerView.setAdapter(homeMoviesAdapter);
                            homeMoviesAdapter.notifyDataSetChanged();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    return map;
                }
            };
            Singleton.getInstance(getActivity()).addRequestQue(stringRequest);

        }
    }
}

