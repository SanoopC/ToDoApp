package com.sanoop.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sanoop.todoapp.utils.Constants.TODO_DAILY
import com.sanoop.todoapp.utils.Constants.TODO_WEEKLY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [ToDo::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao

    private class ToDoDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val toDoDao = database.toDoDao()

                    toDoDao.deleteAll()

                    var toDo = ToDo(
                        title = "Updates",
                        description = "Daily updates",
                        timestamp = 1627219800000,
                        type = TODO_DAILY
                    )
                    toDoDao.insert(toDo)
                    toDo = ToDo(
                        title = "Feedback",
                        description = "Meeting overview",
                        timestamp = 1627219800000,
                        type = TODO_WEEKLY
                    )
                    toDoDao.insert(toDo)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ToDoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, ToDoDatabase::class.java, "todo_database"
                ).addCallback(ToDoDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }
}