package com.example.siga.viewmodel

import com.google.firebase.Timestamp

data class Post(
    var userUrl: String ?= null,
    var username: String ?= null,
    var mediaUrl: String ?= null,
    var description: String ?= null,
    var location: String ?= null,
    var timestamp: Timestamp ?= null
)