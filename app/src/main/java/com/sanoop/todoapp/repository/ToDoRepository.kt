package com.sanoop.todoapp.repository

import androidx.annotation.WorkerThread
import com.sanoop.todoapp.database.ToDo
import com.sanoop.todoapp.database.ToDoDao
import kotlinx.coroutines.flow.Flow

class ToDoRepository(private val toDoDao: ToDoDao) {
    val allToDos: Flow<List<ToDo>> = toDoDao.getToDos()

    @WorkerThread
    suspend fun insert(toDo: ToDo) {
        toDoDao.insert(toDo)
    }

    @WorkerThread
    suspend fun delete(toDo: ToDo) {
        toDoDao.delete(toDo)
    }
}