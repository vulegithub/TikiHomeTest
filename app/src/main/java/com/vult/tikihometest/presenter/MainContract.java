package com.vult.tikihometest.presenter;

import java.util.List;

public interface MainContract {

    interface View {

        void onFetchDataStarted();

        void onFetchDataCompleted();

        void onFetchDataSuccess(List<String> keywordList);

        void onFetchDataError(Throwable e);
    }

    interface Presenter {

        void loadData();

        void unsubscribe();

        void onDestroy();

    }
}