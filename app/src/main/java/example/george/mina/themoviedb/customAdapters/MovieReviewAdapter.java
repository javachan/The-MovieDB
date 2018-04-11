package example.george.mina.themoviedb.customAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import example.george.mina.themoviedb.R;
import example.george.mina.themoviedb.models.MovieReviewModel;

/**
 * Created by minageorge on 4/11/18.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder> {

    private ArrayList<MovieReviewModel> reviews = new ArrayList<>();


    @NonNull
    @Override
    public MovieReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, null);
        return new MovieReviewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewAdapter.ViewHolder holder, int position) {
        holder.author.setText("- " + reviews.get(position).getAuthor());
        holder.content.setText(reviews.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void swapData(ArrayList<MovieReviewModel> movieReviews) {
        reviews.clear();
        reviews = movieReviews;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView author, content;

        public ViewHolder(View itemView) {
            super(itemView);
            this.author = itemView.findViewById(R.id.textView_review_author);
            this.content = itemView.findViewById(R.id.textView_review_content);
        }
    }

}
