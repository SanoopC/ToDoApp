package com.sanoop.todoapp.home

import com.sanoop.todoapp.database.ToDo

interface ItemClickListener {
    fun onItemDelete(toDo: ToDo)
}