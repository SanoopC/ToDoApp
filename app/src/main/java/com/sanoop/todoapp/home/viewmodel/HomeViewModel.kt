package com.sanoop.todoapp.home.viewmodel

import androidx.lifecycle.*
import com.sanoop.todoapp.database.ToDo
import com.sanoop.todoapp.repository.ToDoRepository
import kotlinx.coroutines.launch

class ToDoViewModel(private val repository: ToDoRepository) : ViewModel() {
    var allToDos: LiveData<List<ToDo>> = repository.allToDos.asLiveData()

    fun insert(toDo: ToDo) = viewModelScope.launch {
        repository.insert(toDo)
    }

    fun delete(toDo: ToDo) = viewModelScope.launch {
        repository.delete(toDo)
    }
}

class ToDoViewModelFactory(private val repository: ToDoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}