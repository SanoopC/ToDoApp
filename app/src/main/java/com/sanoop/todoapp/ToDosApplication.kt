package com.sanoop.todoapp

import android.app.Application
import com.sanoop.todoapp.database.ToDoDatabase
import com.sanoop.todoapp.repository.ToDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ToDosApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { ToDoDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ToDoRepository(database.toDoDao()) }
}