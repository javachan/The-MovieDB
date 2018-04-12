package example.george.mina.themoviedb.customAdapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import example.george.mina.themoviedb.DetailsFragment;
import example.george.mina.themoviedb.MainActivity;
import example.george.mina.themoviedb.R;
import example.george.mina.themoviedb.data.MovieContract;

/**
 * Created by minageorge on 4/12/18.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private GridLayoutManager gridLayoutManager = null;
    private Context mContext;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Cursor myCursor = null;


    public FavoritesAdapter(Context context) {
        this.mContext = context;
        this.preferences = mContext.getSharedPreferences("currentConfg", Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new FavoritesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, int position) {
        int spanCount = gridLayoutManager.getSpanCount();
        if (myCursor != null) {
            myCursor.moveToPosition(position);
            if (spanCount > 1) {
                holder.recLayout.setVisibility(View.GONE);
                holder.squLayout.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/" +
                        myCursor.getString(myCursor.getColumnIndexOrThrow(MovieContract.FavListEntry.COL_POSTER))).into(holder.imageViewSqu);
            } else {
                holder.squLayout.setVisibility(View.GONE);
                holder.recLayout.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/" +
                        myCursor.getString(myCursor.getColumnIndexOrThrow(MovieContract.FavListEntry.COL_POSTER))).into(holder.imageViewRec);
                holder.movieTitle.setText(myCursor.getString(myCursor.getColumnIndexOrThrow(MovieContract.FavListEntry.COL_TITLE)));
                holder.movieTitle.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Merriweather-BlackItalic.ttf"));
                holder.movieRate.setText(myCursor.getString(myCursor.getColumnIndexOrThrow(MovieContract.FavListEntry.COL_RATE)) + " / 10");
            }
            holder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        if (myCursor == null) {
            return 0;
        } else {
            return myCursor.getCount();
        }
    }

    public void setLayoutManager(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    public void setCursor(Cursor cursor) {
        myCursor = null;
        myCursor = cursor;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewSqu;
        ImageView imageViewRec;
        TextView movieTitle, movieRate;
        CardView recLayout;
        CardView squLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageViewSqu = itemView.findViewById(R.id.img_squ);
            this.imageViewRec = itemView.findViewById(R.id.img_rec);
            this.movieTitle = itemView.findViewById(R.id.movie_title);
            this.recLayout = itemView.findViewById(R.id.rec_id);
            this.squLayout = itemView.findViewById(R.id.squ_id);
            this.movieRate = itemView.findViewById(R.id.movie_ratee);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    myCursor.moveToPosition(pos);

                    Bundle b = new Bundle();
                    b.putString("poster", myCursor.getString(myCursor.getColumnIndexOrThrow(MovieContract.FavListEntry.COL_POSTER)));
                    b.putString("backdrop", myCursor.getString(myCursor.getColumnIndexOrThrow(MovieContract.FavListEntry.COL_BACKDROP)));
                    b.putString("title", myCursor.getString(myCursor.getColumnIndexOrThrow(MovieContract.FavListEntry.COL_TITLE)));
                    b.putString("rate", myCursor.getString(myCursor.getColumnIndexOrThrow(MovieContract.FavListEntry.COL_RATE)));
                    b.putString("date", myCursor.getString(myCursor.getColumnIndexOrThrow(MovieContract.FavListEntry.COL_DATE)));
                    b.putString("lang", myCursor.getString(myCursor.getColumnIndexOrThrow(MovieContract.FavListEntry.COL_LANGUAGE)));
                    b.putString("overview", myCursor.getString(myCursor.getColumnIndexOrThrow(MovieContract.FavListEntry.COL_OVERVIEW)));
                    b.putString("id", myCursor.getString(myCursor.getColumnIndexOrThrow(MovieContract.FavListEntry.COL_ID)));

                    DetailsFragment df = new DetailsFragment();
                    df.setArguments(b);
                    editor.putInt("span", gridLayoutManager.getSpanCount()).commit();
                    FragmentTransaction ft = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.addToBackStack(DetailsFragment.class.getSimpleName());
                    ft.replace(R.id.fragment_content, df, DetailsFragment.class.getSimpleName())
                            .commit();
                }
            });

        }
    }
}
