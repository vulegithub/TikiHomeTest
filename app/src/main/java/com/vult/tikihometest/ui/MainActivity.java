package com.vult.tikihometest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vult.tikihometest.BuildConfig;
import com.vult.tikihometest.R;
import com.vult.tikihometest.presenter.MainContract;
import com.vult.tikihometest.presenter.MainPresenter;
import com.vult.tikihometest.remoteservice.WebApiServiceImp;
import com.vult.tikihometest.ui.adapter.KeywordAdapter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    // UI Components
    ProgressBar mProgressBar;

    // Recycle view
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mKeywordRecycleView;
    private KeywordAdapter mKeywordAdapter;

    // Presenter
    private MainPresenter mMainPresenter;

    // WebAPiService
    private WebApiServiceImp mWebApiServiceImp;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progress_circular);

        Toast.makeText(this, String.valueOf(BuildConfig.isallowed), Toast.LENGTH_SHORT).show();

        // Recycle view
        mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mKeywordRecycleView = findViewById(R.id.keyword_recycle_view);
        mKeywordAdapter = new KeywordAdapter();
        mKeywordRecycleView.setAdapter(mKeywordAdapter);
        mKeywordRecycleView.setLayoutManager(mLinearLayoutManager);

        mWebApiServiceImp = new WebApiServiceImp();
        mCompositeDisposable = new CompositeDisposable();
        mMainPresenter = new MainPresenter(this, mWebApiServiceImp,
                Schedulers.io(),
                AndroidSchedulers.mainThread(), mCompositeDisposable);
        mMainPresenter.loadData();
    }

    @Override
    public void onFetchDataStarted() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFetchDataCompleted() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFetchDataSuccess(List<String> keywordList) {
        mKeywordAdapter.setKeywordModelList(keywordList);
    }

    @Override
    public void onFetchDataError(Throwable e) {
        mProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

}
