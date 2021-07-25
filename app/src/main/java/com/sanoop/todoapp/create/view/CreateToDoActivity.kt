package com.sanoop.todoapp.create.view

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sanoop.todoapp.utils.Constants.EXTRA_TODO
import com.sanoop.todoapp.utils.Constants.TODO_DAILY
import com.sanoop.todoapp.utils.Constants.TODO_WEEKLY
import com.sanoop.todoapp.R
import com.sanoop.todoapp.create.viewmodel.CreateViewModel
import com.sanoop.todoapp.create.viewmodel.CreateViewModelFactory
import com.sanoop.todoapp.database.ToDo
import com.sanoop.todoapp.databinding.ActivityCreateToDoBinding
import com.sanoop.todoapp.login.view.afterTextChanged
import com.sanoop.todoapp.utils.Util.Companion.updateDateInView
import com.sanoop.todoapp.utils.Util.Companion.updateTimeInView
import kotlinx.android.synthetic.main.activity_create_to_do.*
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class CreateToDoActivity : AppCompatActivity() {
    private var selectedType: String = TODO_DAILY
    private lateinit var binding: ActivityCreateToDoBinding
    private lateinit var calendar: Calendar
    private lateinit var createViewModel: CreateViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calendar = Calendar.getInstance()
        text_date.text = updateDateInView(calendar.time)
        text_time.text = updateTimeInView(calendar.time)

        createViewModel = ViewModelProvider(this, CreateViewModelFactory())
            .get(CreateViewModel::class.java)

        createViewModel.createFormState.observe(this@CreateToDoActivity, Observer {
            val createState = it ?: return@Observer

            button_save.isEnabled = createState.isDataValid

            if (createState.titleError != null) {
                edit_title.error = getString(createState.titleError)
            }
        })
        edit_title.afterTextChanged {
            createViewModel.createDataChanged(
                edit_title.text.toString(),
                text_time.text.toString()
            )
        }

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                text_date.text = updateDateInView(calendar.time)
            }

        val timeSetListener =
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                text_time.text = updateTimeInView(calendar.time)
                createViewModel.createDataChanged(
                    edit_title.text.toString(),
                    text_time.text.toString()
                )
            }

        button_calendar.setOnClickListener {
            DatePickerDialog(
                this@CreateToDoActivity,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        button_time.setOnClickListener {
            TimePickerDialog(
                this@CreateToDoActivity,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        button_save.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(edit_title.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(
                    EXTRA_TODO, ToDo(
                        edit_title.text.toString(),
                        edit_description.text.toString(),
                        calendar.timeInMillis,
                        selectedType
                    )
                )
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.radio_daily ->
                    if (checked) {
                        selectedType = TODO_DAILY
                    }
                R.id.radio_weekly ->
                    if (checked) {
                        selectedType = TODO_WEEKLY
                    }
            }
        }
    }

}