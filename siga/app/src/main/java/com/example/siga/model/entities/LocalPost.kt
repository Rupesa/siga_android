package com.example.siga.model.entities
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="post")
data class LocalPost(var description: String = "", var location: String = "", @PrimaryKey var postId: String = "", var ownerId: String = "",
                     var mediaUrl: String = "", var timestamp: String?, var userUrl: String = "", var username :String = ""){

    override fun toString(): String {
        return "Post: $username location: $location"
    }
}
