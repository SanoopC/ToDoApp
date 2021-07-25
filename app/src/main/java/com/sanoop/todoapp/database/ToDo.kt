package com.sanoop.todoapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todo_table")
data class ToDo(val title: String, val description: String, val timestamp: Long, val type: String) :
    Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
