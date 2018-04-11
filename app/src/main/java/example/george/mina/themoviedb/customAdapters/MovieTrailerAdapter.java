package example.george.mina.themoviedb.customAdapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import example.george.mina.themoviedb.R;

/**
 * Created by minageorge on 4/10/18.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder> {

    private ArrayList<String> trailers = new ArrayList<>();
    private Context context;

    public MovieTrailerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieTrailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, null);
        return new MovieTrailerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailerAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.trailerTitle.setText("Trailers " + String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public void swapData(ArrayList<String> movieTrailers) {
        trailers.clear();
        trailers = movieTrailers;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView trailerTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            this.trailerTitle = itemView.findViewById(R.id.textView_trailer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    Intent appIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("vnd.youtube:" + trailers.get(pos)));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + trailers.get(pos)));
                    try {
                        context.startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        context.startActivity(webIntent);
                    }
                }
            });

        }
    }

}
