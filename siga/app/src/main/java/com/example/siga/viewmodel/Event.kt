package com.example.siga.viewmodel

import com.google.firebase.Timestamp

data class Event(
    var userUrl: String ?= null,
    var username: String ?= null,
    var checked: Boolean ?= null,
    var mediaUrl: String ?= null,
    var description: String ?= null,
    var location: String ?= null,
    var timestamp: Timestamp ?= null,
    //var people: Array<String> ?= null,
    var lat: Double ?= 0.0,
    var lng: Double ?= 0.0,
)
