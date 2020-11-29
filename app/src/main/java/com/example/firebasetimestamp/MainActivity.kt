package com.example.firebasetimestamp

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime

import java.util.*
import kotlin.collections.HashMap
import kotlin.time.minutes

class MainActivity : AppCompatActivity() {
    val fb = FirebaseFirestore.getInstance()
    @ServerTimestamp var Fdate = Date()
    var fyear:Int = 0
    var fmonth:Int = 0
    var fday:Int = 0
    var fhour:Int = 0
    var fminute:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                // Use the current time as the default values for the picker
                val c = Calendar.getInstance()
                val hour = c.get(Calendar.HOUR_OF_DAY)
                val minute = c.get(Calendar.MINUTE)

                // Create a new instance of TimePickerDialog and return it
                return TimePickerDialog(activity, this, hour, minute, is24HourFormat(activity))
            }

            override fun onTimeSet(view: TimePicker, hour: Int, minute: Int) {
                // Do something with the time chosen by the user
                fhour = hour
               fminute = minute
                Log.d("Hour", "$fhour")
                Log.d("Minute", "$fminute")
            }
        }

        class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                // Use the current date as the default date in the picker
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                // Create a new instance of DatePickerDialog and return it
                return DatePickerDialog(this@MainActivity, this, year, month, day)
            }

            override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
                // Do something with the date chosen by the user
                fyear = year
                fmonth = month
                fday = day
                Log.d("Year", "$fyear")
                Log.d("Month", "$fmonth")
                Log.d("Day", "$fday")
            }
        }

        openTime.setOnClickListener{
                TimePickerFragment()
                    .show(supportFragmentManager, "timePicker")
        }

        openCalendar.setOnClickListener {
            DatePickerFragment()
            .show(supportFragmentManager, "datePicker")
        }

        insertTime.setOnClickListener {
            Fdate = Date(fyear-1900,fmonth,fday,fhour,fminute)
            val docData = hashMapOf(
                "Date" to Timestamp(Fdate),
            )
            insertTimeStamp(docData)
        }

//        val listener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
//            Log.d("Year", "$year")
//            Log.d("Month", "$month")
//            Log.d("Day", "$day")
//            fyear = year
//            fmonth = month
//            fday = day
//            val calendarDate = Calendar.getInstance()
//            calendarDate.set(year, month, day)
//            val fullDate = SimpleDateFormat("dd-MM-yyyy,EEEE")
//            val date = fullDate.format(calendarDate.time)
//            val dayOfWeek = SimpleDateFormat("E")
//            val convertDate = dayOfWeek.format(calendarDate.time)
//            Log.d("Day of Week", "$convertDate")
//            Log.d("Full Date", "$date")
//
//            //var timestampDate = Timestamp(Fdate)
//
//        }
//
//        openCalendar.setOnClickListener {
//            val now = LocalDateTime.now()
//            val picker = DatePickerDialog(this, listener, now.year, now.month.value, now.dayOfMonth).show()
//        }
//
//        val timeListener = TimePickerDialog.OnTimeSetListener { timePicker: TimePicker?, hour: Int, minute: Int ->
//            Log.d("Hour", hour.toString())
//            Log.d("Minute", minute.toString())
//            fhour = hour
//            fminute = minute
//
//
//        }
//        openTime.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                val now = LocalDateTime.now()
//                val pick = TimePickerDialog(this@MainActivity,timeListener,now.hour,now.minute,true).show()
//            }
//        })
//
//
//
//    }
//

    }
    private fun insertTimeStamp(date:HashMap<String,Timestamp>){
        val ref = fb.collection("Date")
        ref.document("Date").set(date)

    }

}