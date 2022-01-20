package com.example.siga

data class Event(
    var userImage: String ?= null,
    var userName: String ?= null,
    var checked: Boolean ?= null,
    var image: String ?= null,
    var title: String ?= null,
    var location: String ?= null,
    var date: String ?= null
)
