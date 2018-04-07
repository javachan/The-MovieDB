package example.george.mina.themoviedb;

/**
 * Created by minageorge on 4/7/18.
 */

class DetailItem {
    String posterpath;
    String title;
    String vote;
    String description;
    String date;


    public DetailItem(String posterpath, String title, String vote, String description, String date) {
        this.posterpath = posterpath;
        this.title = title;
        this.vote = vote;
        this.description = description;
        this.date = date;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public String getTitle() {
        return title;
    }

    public String getVote() {
        return vote;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
