package example.george.mina.themoviedb;

/**
 * Created by minageorge on 4/7/18.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeMoviesAdapter extends RecyclerView.Adapter<HomeMoviesAdapter.ViewHolder> {
    private ArrayList<MovieDetails> moviesItem = new ArrayList<>();
    private Context mContext;
    private GridLayoutManager gridLayoutManager = null;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public HomeMoviesAdapter(Context context) {
        this.mContext = context;
        this.preferences = mContext.getSharedPreferences("currentConfg",Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    @Override
    public HomeMoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, null);
        return new HomeMoviesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HomeMoviesAdapter.ViewHolder holder, int position) {
        int spanCount = gridLayoutManager.getSpanCount();
        if (spanCount > 1 ) {
            holder.recLayout.setVisibility(View.GONE);
            holder.squLayout.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/" + moviesItem.get(position).getMovieImag()).into(holder.imageViewSqu);
        } else {
            holder.squLayout.setVisibility(View.GONE);
            holder.recLayout.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/" + moviesItem.get(position).getMovieImag()).into(holder.imageViewRec);
            holder.movieTitle.setText(moviesItem.get(position).getMovieTitle());
            holder.movieTitle.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Merriweather-BlackItalic.ttf"));
        }

    }

    @Override
    public int getItemCount() {
        return moviesItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewSqu;
        ImageView imageViewRec;
        TextView movieTitle;
        CardView recLayout;
        CardView squLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageViewSqu = (ImageView) itemView.findViewById(R.id.img_squ);
            this.imageViewRec =(ImageView) itemView.findViewById(R.id.img_rec);
            this.movieTitle = (TextView) itemView.findViewById(R.id.movie_title);
            this.recLayout = (CardView)itemView.findViewById(R.id.rec_id);
            this.squLayout = (CardView) itemView.findViewById(R.id.squ_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putInt("span",gridLayoutManager.getSpanCount()).commit();
                   FragmentTransaction ft =((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
                   ft.addToBackStack(DetailsFragment.class.getSimpleName());
                            ft.replace(R.id.fragment_content, new DetailsFragment(),DetailsFragment.class.getSimpleName())
                            .commit();
                }
            });

        }
    }

    public void setLayoutManager(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    public void swapData(ArrayList<MovieDetails> movieDetails) {
        moviesItem.clear();
        moviesItem = movieDetails;
        notifyDataSetChanged();
    }

}
