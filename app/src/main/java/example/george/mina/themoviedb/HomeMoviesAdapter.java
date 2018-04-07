package example.george.mina.themoviedb;

/**
 * Created by minageorge on 4/7/18.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Google       Company on 26/11/2017.
 */

public class HomeMoviesAdapter extends RecyclerView.Adapter <HomeMoviesAdapter.FilmItemRowHolder> {
    ArrayList<MoviesItemModel> moviesItem;
    Context mcontext;
    DetailItem[] detailItems;
    public HomeMoviesAdapter(Context mcontext, ArrayList<MoviesItemModel> moviesItem, DetailItem[] detailItems) {
        this.mcontext=mcontext;
        this.moviesItem=moviesItem;
        this.detailItems=detailItems;
    }

    @Override
    public HomeMoviesAdapter.FilmItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, null);
        HomeMoviesAdapter.FilmItemRowHolder ALRH = new HomeMoviesAdapter.FilmItemRowHolder(v);
        return ALRH;

    }

    @Override
    public void onBindViewHolder(HomeMoviesAdapter.FilmItemRowHolder holder, int position) {
        Picasso.with(mcontext).load("http://image.tmdb.org/t/p/w342/"+moviesItem.get(position).getImgMovie()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        Log.i("size",moviesItem.size()+"");
        return moviesItem.size();
    }

    public class FilmItemRowHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public FilmItemRowHolder(View itemView) {
            super(itemView);
            this.imageView =(ImageView) itemView.findViewById(R.id.img);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent detail = new Intent(mcontext, ActivitDetailMovbie.class);
                    detail.putExtra("original_title", detailItems[getAdapterPosition()].getTitle());
                    detail.putExtra("overview", detailItems[getAdapterPosition()].getDescription());
                    detail.putExtra("release_date", detailItems[getAdapterPosition()].getDate());
                    detail.putExtra("vote_average", detailItems[getAdapterPosition()].getVote());
                    detail.putExtra("poster_path", detailItems[getAdapterPosition()].getPosterpath());

                    mcontext.startActivity(detail);


                }
            });

        }
    }

}
