package com.sanoop.todoapp.utils

import kotlinx.android.synthetic.main.activity_create_to_do.*
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        fun updateDateInView(calendar: Date): String? {
            val myFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            return sdf.format(calendar.time)
        }

        fun updateTimeInView(calendar: Date): String {
            val myFormat = "HH:mm" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            return sdf.format(calendar.time)
        }
    }
}