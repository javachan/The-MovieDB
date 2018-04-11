package example.george.mina.themoviedb;

/**
 * Created by minageorge on 4/7/18.
 */
class MovieDetailsModel {
    private String movieImag;
    private String movieTitle;
    private String movieDescription;
    private String movieDate;
    private String movieVote;
    private String movieOverView;
    private String movieBackdrop;
    private String movieVoteCount;
    private String movieId;
    private String movieLanguage;


    public String getMovieLanguage() {
        return movieLanguage;
    }

    public void setMovieLanguage(String movieLanguage) {
        this.movieLanguage = movieLanguage;
    }

    public String getMovieBackdrop() {
        return movieBackdrop;
    }

    public void setMovieBackdrop(String movieBackdrop) {
        this.movieBackdrop = movieBackdrop;
    }

    public String getMovieVoteCount() {
        return movieVoteCount;
    }

    public void setMovieVoteCount(String movieVoteCount) {
        this.movieVoteCount = movieVoteCount;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieImag() {
        return movieImag;
    }

    public void setMovieImag(String movieImag) {
        this.movieImag = movieImag;
    }

    public String getMovieOverView() {
        return movieOverView;
    }

    public void setMovieOverView(String movieOverView) {
        this.movieOverView = movieOverView;
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
