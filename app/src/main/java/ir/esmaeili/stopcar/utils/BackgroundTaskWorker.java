package ir.esmaeili.stopcar.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Baset
 * @version 1
 */

public abstract class BackgroundTaskWorker<Params, Result> {

    private static final String TAG = "AsyncTaskWorker";

    @Nullable
    @SuppressWarnings("unchecked")
    protected abstract Result doInBackground(Params... params);

    protected void onPostExecute(@NonNull Result result) {
    }

    protected void onError(@NonNull Throwable error) {
    }


    protected void onSubscribe(@NonNull Disposable disposable) {
    }

    @SafeVarargs
    public final void execute(final Params... params) {

        Single.fromCallable(() -> {
            Log.d(TAG, "doInBackground function is called ");
            return doInBackground(params);
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Result>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe function is called ");
                BackgroundTaskWorker.this.onSubscribe(d);
            }

            @Override
            public void onSuccess(Result result) {
                Log.d(TAG, "onSuccess function is called ");
                onPostExecute(result);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError function is called ");
                Log.e(TAG, "onError: ", e);
                BackgroundTaskWorker.this.onError(e);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public final void execute() {
        Single.fromCallable(() -> {
            Log.d(TAG, "doInBackground function is called ");
            return doInBackground();
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new SingleObserver<Result>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe function is called ");
                BackgroundTaskWorker.this.onSubscribe(d);
            }

            @Override
            public void onSuccess(Result result) {
                Log.d(TAG, "onSuccess function is called ");
                onPostExecute(result);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError function is called ");
                Log.e(TAG, "onError: ", e);
                BackgroundTaskWorker.this.onError(e);
            }
        });
    }
}