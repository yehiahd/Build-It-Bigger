package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by yehia on 05/04/17.
 */

public class FetchJokesTest extends AndroidTestCase {

    public void testJoke(){
        RxEndPoint.getJoke()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String joke) {
                        assert joke != null;
                        assert !joke.equals("");
                    }
                });
    }

}
