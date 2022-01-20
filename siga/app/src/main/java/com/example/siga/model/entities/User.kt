package com.example.siga.model.entities

data class User(var id:String = "" , var bio: String = "", var displayName: String = "", var email:String = "", var photUrl: String = "",
                var timestamp: String?, var username: String = "") {

    override fun toString():String{
        return "User: $username, email: $email"

    }
}