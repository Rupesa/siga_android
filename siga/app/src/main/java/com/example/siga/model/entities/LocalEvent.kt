package com.example.siga.model.entities


data class LocalEvent(var ownerId: String = "", var eventId: String = "", var description: String = "", var lat: Double = 0.0, var lng: Double = 0.0, var location: String = "",
                      var mediaUrl: String = "", var timestamp: String?, var userUrl: String = "", var username :String = ""){

    override fun toString(): String {
        return "Event: $username location: $location"
    }
}
