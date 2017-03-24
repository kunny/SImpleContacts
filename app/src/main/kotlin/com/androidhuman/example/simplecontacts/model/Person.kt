package com.androidhuman.example.simplecontacts.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

const val PRIMARY_KEY = "id"

open class Person : RealmObject() {

    @PrimaryKey var id: Long = 0

    lateinit @Required var name: String

    var address: String? = null
}
