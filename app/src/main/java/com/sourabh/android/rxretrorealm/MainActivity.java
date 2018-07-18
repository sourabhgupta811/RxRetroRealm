package com.sourabh.android.rxretrorealm;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sourabh.android.rxretrorealm.adapter.RepoAdapter;
import com.sourabh.android.rxretrorealm.modal.Repo;
import com.sourabh.android.rxretrorealm.rest.ApiClient;
import com.sourabh.android.rxretrorealm.rest.GithubApiClient;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Realm mRealm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRealm = Realm.getDefaultInstance();
        GithubApiClient githubApiClient = ApiClient.getInstance().getUserGithubRepoClient();
        Observable<List<Repo>> listObservable = Observable.create(e->getDbRepo());
        if(isNetworkAvailable()){
            githubApiClient.getUserRepo("sourabhgupta811")
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .map(
                            repos -> {
                                Realm realm = Realm.getDefaultInstance();
                                realm.executeTransaction(mRealm -> mRealm.copyToRealm(repos));
                                Log.e("database", realm.where(Repo.class).findAll().size() + "");
                                return repos;
                            }
                    ).mergeWith(listObservable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::setAdapterData);
        }
        else{
            setAdapterData(getDbRepo());
        }
    }

    private void setAdapterData(List<Repo> repos) {
        RepoAdapter adapter = new RepoAdapter(this,repos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    private List<Repo> getDbRepo(){
        return mRealm.copyFromRealm(mRealm.where(Repo.class).findAll());
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            return info!=null && info.isConnected();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        mRealm.close();
        super.onDestroy();
    }
}
