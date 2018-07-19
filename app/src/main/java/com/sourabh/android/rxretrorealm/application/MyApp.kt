package com.sourabh.android.rxretrorealm.application

import android.app.Application

import io.realm.Realm

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}
