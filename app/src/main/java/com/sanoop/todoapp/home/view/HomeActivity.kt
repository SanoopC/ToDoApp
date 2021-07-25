package com.sanoop.todoapp.home.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanoop.todoapp.R
import com.sanoop.todoapp.ToDosApplication
import com.sanoop.todoapp.create.view.CreateToDoActivity
import com.sanoop.todoapp.database.ToDo
import com.sanoop.todoapp.databinding.ActivityHomeBinding
import com.sanoop.todoapp.home.ItemClickListener
import com.sanoop.todoapp.home.ToDoListAdapter
import com.sanoop.todoapp.home.viewmodel.ToDoViewModel
import com.sanoop.todoapp.home.viewmodel.ToDoViewModelFactory
import com.sanoop.todoapp.utils.Constants.EXTRA_TODO
import java.util.*

class HomeActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var toDoViewModel: ToDoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        toDoViewModel = ViewModelProvider(
            this,
            ToDoViewModelFactory((application as ToDosApplication).repository)
        )
            .get(ToDoViewModel::class.java)

        val fab = binding.fab
        val recyclerview = binding.recyclerview
        val adapter = ToDoListAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
        toDoViewModel.allToDos.observe(this) { todos ->
            todos.let { adapter.submitList(it) }
        }
        fab.setOnClickListener {
            val intent = Intent(this@HomeActivity, CreateToDoActivity::class.java)
            startForResult.launch(intent)
        }
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                toDoViewModel.insert(result.data?.getSerializableExtra(EXTRA_TODO) as ToDo)
            } else {
                Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun onItemDelete(toDo: ToDo) {
        val alert = AlertDialog.Builder(this)
        alert.setTitle(getString(R.string.warning))
        alert.setMessage(getString(R.string.confirm_delete))
        alert.setPositiveButton(getString(R.string.proceed)) { dialog, _ ->
            toDoViewModel.delete(toDo)
            dialog.dismiss()
        }
        alert.show()
    }
}