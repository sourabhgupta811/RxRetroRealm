package com.sourabh.android.rxretrorealm.modal

import com.google.gson.annotations.SerializedName

import io.realm.RealmObject

public open class Repo:RealmObject(){
    @SerializedName("id")
    public open var id: String? = null
    @SerializedName("name")
    public open var name: String? = null
}
