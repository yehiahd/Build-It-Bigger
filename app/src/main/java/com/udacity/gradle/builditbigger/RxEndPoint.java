package com.udacity.gradle.builditbigger;


import com.example.yehia.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by yehia on 04/04/17.
 */

public class RxEndPoint {

    private static MyApi myApiService = null;

    public static Observable<String> getJoke(){

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()){
                    if(myApiService == null) {
                        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                                .setRootUrl("http://192.168.1.6:8080/_ah/api/")
                                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                    @Override
                                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                        abstractGoogleClientRequest.setDisableGZipContent(true);
                                    }
                                });
                        myApiService = builder.build();
                    }

                    try {
                        subscriber.onNext(myApiService.getJokes().execute().getData());
                        subscriber.onCompleted();
                    } catch (IOException e) {
                        subscriber.onError(e);
                    }
                }
            }
        });
    }
}
