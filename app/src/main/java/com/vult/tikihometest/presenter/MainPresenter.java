package com.vult.tikihometest.presenter;

import com.vult.tikihometest.remoteservice.WebApiServiceImp;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private WebApiServiceImp mWebApiServiceImp;
    private Scheduler mBackgroundScheduler;
    private Scheduler mMainScheduler;
    private CompositeDisposable mCompositeDisposable;

    public MainPresenter(MainContract.View view, WebApiServiceImp webApiServiceImp,
                         Scheduler backgroundScheduler, Scheduler mainScheduler,
                         CompositeDisposable compositeDisposable) {
        mView = view;
        mWebApiServiceImp = webApiServiceImp;
        mBackgroundScheduler = backgroundScheduler;
        mMainScheduler = mainScheduler;
        mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void loadData() {

        mCompositeDisposable.clear();

        Disposable disposable = mWebApiServiceImp
                .getKeywords()
                .subscribeOn(mBackgroundScheduler)
                .observeOn(mMainScheduler)
                .doOnSubscribe(disposable1 -> mView.onFetchDataStarted())
                .subscribe(strings -> {
                    mView.onFetchDataSuccess(strings);
                    mView.onFetchDataCompleted();

                }, throwable -> {
                    mView.onFetchDataError(throwable);
                    mView.onFetchDataCompleted();
                });


        mCompositeDisposable.add(disposable);

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public MainContract.View getView() {
        return mView;
    }

    @Override
    public void onDestroy() {
        mView = null;
        mCompositeDisposable = null;
    }
}
