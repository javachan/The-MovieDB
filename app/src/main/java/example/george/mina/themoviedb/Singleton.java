package example.george.mina.themoviedb;

/**
 * Created by minageorge on 4/7/18.
 */

public class Singleton {
    private static Singleton mInstance;
    private RequestQueue mRequestQueue;
    private Context mContext;

    private Singleton(Context mContext) {
        this.mContext = mContext;
        mRequestQueue = getRequestQueue();
        System.out.println("singleton constructor");
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static synchronized Singleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Singleton(context);
        }
        return mInstance;
    }

    public <T> void addRequestQue(Request<T> request) {
        System.out.println("add queue mehtod in singleton ");
        mRequestQueue.add(request);
    }

}