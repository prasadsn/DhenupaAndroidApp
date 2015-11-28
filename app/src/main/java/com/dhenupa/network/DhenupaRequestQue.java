package com.dhenupa.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by prsn0001 on 10/18/2015.
 */
public class DhenupaRequestQue {
    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;

    private static DhenupaRequestQue instance;

    private DhenupaRequestQue (Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    public static DhenupaRequestQue getInstance(Context context){
        if(instance == null)
            instance = new DhenupaRequestQue(context);
        return instance;
    }

    public ImageLoader getImageLoader(){
        return mImageLoader;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

    public void cancelRequests(){
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
        }
    }

}
