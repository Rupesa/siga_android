package com.example.siga.model.entities

import java.sql.Timestamp

data class Card(var ownerId: String = "", var eventName: String = "", var eventId: String = "",var mediaUrl: String = "", var timestamp: String?, var userUrl: String = "", var username :String = ""){
    override fun toString(): String {
        return "Card: $eventName  Username: $username"
    }
}
