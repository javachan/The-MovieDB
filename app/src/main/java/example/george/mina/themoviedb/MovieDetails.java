package example.george.mina.themoviedb;

/**
 * Created by minageorge on 4/7/18.
 */
class MovieDetails {
    private String movieImag;
    private String movieTitle;
    private String movieDescription;
    private String movieDate;
    private String movieVote;
    private String movieOverView;

    public String getMovieImag() {
        return movieImag;
    }

    public String getMovieOverView() {
        return movieOverView;
    }

    public void setMovieOverView(String movieOverView) {
        this.movieOverView = movieOverView;
    }

    public void setMovieImag(String movieImag) {
        this.movieImag = movieImag;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    public String getMovieVote() {
        return movieVote;
    }

    public void setMovieVote(String movieVote) {
        this.movieVote = movieVote;
    }
}
