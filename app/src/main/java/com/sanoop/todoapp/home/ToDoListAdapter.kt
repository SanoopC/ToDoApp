package com.sanoop.todoapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sanoop.todoapp.R
import com.sanoop.todoapp.database.ToDo
import com.sanoop.todoapp.utils.Util.Companion.updateDateInView
import com.sanoop.todoapp.utils.Util.Companion.updateTimeInView
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import java.util.*

class ToDoListAdapter(private var itemClickListener: ItemClickListener) :
    ListAdapter<ToDo, ToDoListAdapter.ToDoViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            itemClickListener.onItemDelete(getItem(position))
        }
    }

    class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(toDoItem: ToDo) {
            itemView.text_title.text = toDoItem.title
            itemView.text_description.text = toDoItem.description
            itemView.text_date.text = updateDateInView(Date(toDoItem.timestamp))
            itemView.text_time.text = updateTimeInView(Date(toDoItem.timestamp))
            itemView.text_type.text = toDoItem.type
        }

        companion object {
            fun create(parent: ViewGroup): ToDoViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return ToDoViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<ToDo>() {
        override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem.id == newItem.id
        }
    }
}