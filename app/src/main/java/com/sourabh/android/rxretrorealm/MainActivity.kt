package com.sourabh.android.rxretrorealm

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log

import com.sourabh.android.rxretrorealm.adapter.RepoAdapter
import com.sourabh.android.rxretrorealm.modal.Repo
import com.sourabh.android.rxretrorealm.rest.ApiClient

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mRealm: Realm = Realm.getDefaultInstance()

    private fun getDbRepo(): List<Repo> = mRealm.copyFromRealm(mRealm.where(Repo::class.java).findAll())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val githubApiClient = ApiClient.getInstance().userGithubRepoClient
        val listObservable = Observable.create<List<Repo>> { getDbRepo() }
        if (isNetworkAvailable()) {
            githubApiClient.getUserRepo("sourabhgupta811")
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .map { repos ->
                        val realm = Realm.getDefaultInstance()
                        realm.executeTransaction { mRealm -> mRealm.copyToRealm(repos) }
                        Log.e("database", realm.where(Repo::class.java).findAll().size.toString() + "")
                        repos
                    }.mergeWith(listObservable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ this.setAdapterData(it) })
        } else {
            setAdapterData(getDbRepo())
        }
    }

    private fun setAdapterData(repos: List<Repo>) {
        val adapter = RepoAdapter(this, repos)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = adapter
    }

    override fun onDestroy() {
        mRealm.close()
        super.onDestroy()
    }
}

private fun MainActivity.isNetworkAvailable(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = manager.activeNetworkInfo
    return info != null && info.isConnected
}
