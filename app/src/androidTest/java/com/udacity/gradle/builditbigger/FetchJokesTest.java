package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

/**
 * Created by yehia on 05/04/17.
 */

public class FetchJokesTest extends AndroidTestCase {

    public void testJoke(){

        EndpointsAsyncTask asyncTask = new EndpointsAsyncTask(getContext());
        asyncTask.execute(new JokeListener() {
            @Override
            public void onJokeTold(String joke) {
                assert joke != null;
                assert !joke.equals("");
            }
        });

//        EndpointsAsyncTask.getJoke()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String joke) {
//                        assert joke != null;
//                        assert !joke.equals("");
//                    }
//                });
    }

}
