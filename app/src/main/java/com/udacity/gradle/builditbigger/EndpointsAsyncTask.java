package com.udacity.gradle.builditbigger;


import android.content.Context;
import android.os.AsyncTask;

import com.example.yehia.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by yehia on 04/04/17.
 */

public class EndpointsAsyncTask extends AsyncTask<JokeListener, Void, String> {

    private static MyApi myApiService = null;
    private Context context;
    private JokeListener listener;

    public EndpointsAsyncTask(Context context) {
        this.context = context;
    }

//    public static Observable<String> getJoke(){
//
//        return Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                if (!subscriber.isUnsubscribed()){
//                    if(myApiService == null) {
//                        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
//                                .setRootUrl("http://192.168.1.6:8080/_ah/api/")
//                                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                                    @Override
//                                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                                        abstractGoogleClientRequest.setDisableGZipContent(true);
//                                    }
//                                });
//                        myApiService = builder.build();
//                    }
//
//                    try {
//                        subscriber.onNext(myApiService.getJokes().execute().getData());
//                        subscriber.onCompleted();
//                    } catch (IOException e) {
//                        subscriber.onError(e);
//                    }
//                }
//            }
//        });
//    }

    @Override
    protected String doInBackground(JokeListener... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://192.168.1.4:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            myApiService = builder.build();
        }
        listener = params[0];
        try {
            return myApiService.getJokes().execute().getData();

        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        listener.onJokeTold(joke);
    }
}
