package com.bayuokta.agecalculate

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bayuokta.agecalculate.databinding.ActivityMainBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDatePicker.setOnClickListener { view ->
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        /**
         * This Gets a calendar using the default time zone and locale.
         * The calender returned is based on the current time
         * in the default time zone with the default.
         */
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR) // Returns the value of the given calendar field. This indicates YEAR
        val month = calendar.get(Calendar.MONTH) // This indicates the Month
        val day = calendar.get(Calendar.DAY_OF_MONTH) // This indicates the Day

        /**
         * Creates a new date picker dialog for the specified date using the parent
         * context's default date picker dialog theme.
         */
        @Suppress("NAME_SHADOWING") val dpd = DatePickerDialog(this, { view, year, month, dayOfMonth ->
            /**
             * The listener used to indicate the user has finished selecting a date.
             */

            /**
             *Here the selected date is set into format i.e : day/Month/Year
             * And the month is counted in java is 0 to 11 so we need to add +1 so it can be as selected.
             * */
            val selectedDate = "$dayOfMonth/${month + 1}/$year"

            // Selected date it set to the TextView to make it visible to user.
            binding.tvSelectDate.text = selectedDate

            /**
             * Here we have taken an instance of Date Formatter as it will format our
             * selected date in the format which we pass it as an parameter and Locale.
             * Here I have passed the format as dd/MM/yyyy.
             */
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            // The formatter will parse the selected date in to Date object
            // so we can simply get date in to milliseconds.
            val theDate = sdf.parse(selectedDate)

            /** Here we have get the time in milliSeconds from Date object
             * And as we know the formula as milliseconds can be converted to second by dividing it by 1000.
             * And the seconds can be converted to minutes by dividing it by 60.
             * So now in the selected date into minutes.
             */
            val selectDateInMinutes = theDate?.time?.div(60000)

            // Here we have parsed the current date with the Date Formatter which is used above.
            val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
            // Current date in to minutes.
            val currentDateInMinutes = currentDate?.time?.div(60000)

            /**
             * Now to get the difference into minutes.
             * We will subtract the selectedDateToMinutes from currentDateToMinutes.
             * Which will provide the difference in minutes.
             */
            val differenceInMinutes = selectDateInMinutes?.let { currentDateInMinutes?.minus(it) }

            val hour = differenceInMinutes?.div(60)

            val day = hour?.div(24)

            val month = day?.div(30.417)

            val format = DecimalFormat("#.#####")

            val monthFormat = format.format(month)


            // Set the difference in minutes to textview to show the user.
            binding.tvSelectDateInMinutes.text = differenceInMinutes.toString()

            binding.tvSelectDateInHour.text = hour.toString()

            binding.tvSelectDateInDay.text = day.toString()

            binding.tvSelectDateInMonth.text = monthFormat.toString()

        }, year, month, day)

        dpd.datePicker.maxDate = Date().time - 86400
        dpd.show()

    }
}