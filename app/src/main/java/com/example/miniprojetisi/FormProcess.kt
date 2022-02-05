package com.example.miniprojetisi

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.forEach
import androidx.core.view.marginTop
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.miniprojetisi.model.InputX
import com.example.miniprojetisi.repository.Repository
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class FormProcess : AppCompatActivity() {
    var sharedPrefer: SharedPreferences? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModel1: MainViewModel

    var date: String=""
    var time: String = ""
    var formate = SimpleDateFormat("YYYY-MM-dd",Locale.US)
    var timeFormat = SimpleDateFormat("hh:mm:ss", Locale.US)
    var myObject=JSONObject()
    var globalObject = JSONObject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_process)

        val titrelayout = findViewById<LinearLayout>(R.id.titre)

        sharedPrefer= this.getSharedPreferences("sharedPreferFile", Context.MODE_PRIVATE)
        val dd:String = intent.getStringExtra("ProcessId").toString()
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider (this, viewModelFactory).get(MainViewModel::class.java)

        val sessionid:String?=sharedPrefer!!.getString("JSESSIONID",null)
        val token:String?=sharedPrefer!!.getString("TOKEN",null)
        viewModel.getFormProcess(sessionid+";"+token, dd.toString())

        viewModel.myFormList.observe(this, Observer { response ->
            if(response.isSuccessful) {
                Log.d("res", response.body().toString())
                val inputs=response.body()!!.inputs[0].inputs
                val titre = TextView(this)
                titre.layoutParams= LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                titre.setText(response.body()!!.inputs[0].name)
                titre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
                titre.setTextColor(Color.parseColor("#0099ff"))
                titre.setTypeface(null, Typeface.BOLD);
                titrelayout ?.addView(titre)


                val textlayout = findViewById<LinearLayout>(R.id.textViewLinearLayout)
                Log.d("name",response.body()!!.inputs[0].name)
               inputs.forEach { elem:InputX ->
                   val textView = TextView(this)
                   textView.layoutParams= LinearLayout.LayoutParams(
                           ViewGroup.LayoutParams.MATCH_PARENT,
                           ViewGroup.LayoutParams.WRAP_CONTENT)
                   textView.setText(elem.name)
                   textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                   textView.setTextColor(Color.parseColor("#0099ff"))
                   textView.setPadding(20, 0, 20, 0)
                   textView.setTypeface(null, Typeface.BOLD);
                   textlayout?.addView(textView)

                   when (elem.type) {
                       "TEXT" -> {
                           val editText = EditText(this)
                           editText.setHint("Entrer "+elem.name)
                           editText.setTag(elem.name)
                           editText.layoutParams = LinearLayout.LayoutParams(
                                   ViewGroup.LayoutParams.MATCH_PARENT,
                                   ViewGroup.LayoutParams.WRAP_CONTENT)
                           editText.setPadding(20, 20, 20, 20)
                           textlayout?.addView(editText)

                       }
                      "BOOLEAN" -> {
                          val checkBox = CheckBox(this)
                          checkBox.setText("Check it")
                          checkBox.setTag(elem.name)
                          checkBox.layoutParams = LinearLayout.LayoutParams(
                                  ViewGroup.LayoutParams.MATCH_PARENT,
                                  ViewGroup.LayoutParams.WRAP_CONTENT)
                          checkBox.setPadding(20, 20, 20, 0)

                          checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                              val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
                              Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                          }
                          textlayout?.addView(checkBox)

                      }
                       "LOCALDATE" -> {
                           /*  val datePicker = DatePicker(this)
                           val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                           layoutParams.setMargins(10, 10, 10, 10)
                           datePicker.layoutParams = layoutParams
                           datePicker.set

                           textlayout?.addView(datePicker)

                           val today = Calendar.getInstance()
                           datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)

                           ) { view, year, monthOfYear, dayOfMonth ->
                               val month = monthOfYear + 1
                               val msg = "Selected Date is $dayOfMonth/$month/$year"
                               Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

                           }*/
                           val textview1 = EditText(this)
                           textview1.setHint("Entrer " + elem.name)
                           textview1.inputType = InputType.TYPE_CLASS_DATETIME
                           textview1.setTag(elem.name)
                           textview1.layoutParams = LinearLayout.LayoutParams(
                                   ViewGroup.LayoutParams.MATCH_PARENT,
                                   ViewGroup.LayoutParams.WRAP_CONTENT)
                           textview1.setPadding(20, 20, 20, 20)
                           textlayout?.addView(textview1)

                           textview1.setFocusable(false)

                           textview1.setOnClickListener {
                               val now = Calendar.getInstance()
                               val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                                   val selectedDate = Calendar.getInstance()
                                   selectedDate.set(Calendar.YEAR, year)
                                   selectedDate.set(Calendar.MONTH, month)
                                   selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                                   val date = formate.format(selectedDate.time)

                                   textview1.setText(date)

                                   Toast.makeText(this, "date : " + date, Toast.LENGTH_SHORT).show()
                               },
                                       now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
                               datePicker.show()
                           }
                       }

                           "OFFSETDATETIME" -> {
                               val textview1 = EditText(this)
                               textview1.setHint("Entrer " + elem.name)
                               textview1.inputType = InputType.TYPE_CLASS_DATETIME
                               textview1.setTag(elem.name)

                               textview1.layoutParams = LinearLayout.LayoutParams(
                                       ViewGroup.LayoutParams.MATCH_PARENT,
                                       ViewGroup.LayoutParams.WRAP_CONTENT)
                               textview1.setPadding(20, 20, 20, 20)
                               textlayout?.addView(textview1)
                               textview1.setFocusable(false)
                               textview1.setOnClickListener {
                                   val now1 = Calendar.getInstance()
                                   val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                                       val selectedDate = Calendar.getInstance()
                                       selectedDate.set(Calendar.YEAR, year)
                                       selectedDate.set(Calendar.MONTH, month)
                                       selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                                       date = formate.format(selectedDate.time)
                                       //textview1.setText(date)
                                       Toast.makeText(this, "date : " + date, Toast.LENGTH_SHORT).show()

                                   val now2 = Calendar.getInstance()
                                   val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                                       val selectedTime = Calendar.getInstance()
                                       selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                       selectedTime.set(Calendar.MINUTE, minute)

                                       time = timeFormat.format(selectedTime.time)

                                       Log.d("date", date)
                                      Log.d("time",time)
                                       var dateTime=date+"T"+time+"Z"
                                      Log.d("date time", dateTime)

                                      // textview1.text = timeFormat.format(selectedTime.time)

                                       textview1.setText(dateTime)


                                   },
                                           now2.get(Calendar.HOUR_OF_DAY), now2.get(Calendar.MINUTE), true)
                                   timePicker.show()
                                   },
                                           now1.get(Calendar.YEAR), now1.get(Calendar.MONTH), now1.get(Calendar.DAY_OF_MONTH))
                                   datePicker.show()

                               }
                           }
                       else -> { // Note the block
                           print(" pas text")
                       }
                   }

               }
                val btnSubmit = Button(this)
                btnSubmit.layoutParams= LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)

                btnSubmit.setText("add")
                btnSubmit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                btnSubmit.setTextColor(Color.parseColor("#ffffff"))
                btnSubmit.setBackgroundColor(Color.parseColor("#0099ff"))
                btnSubmit.setPadding(20, 0, 20, 0)
                btnSubmit.setTag("button")
                textlayout?.addView(btnSubmit)

                btnSubmit.setOnClickListener {

                textlayout.forEach {
                        element:View ->
                    if (element is EditText) {
                        myObject.put(element.getTag().toString(), element.getText())
                    }
                    else if (element is CheckBox) {
                        myObject.put(element.getTag().toString(), element.isChecked())
                    }

                }
                    // Log.d("object json",myObject.toString())
                    globalObject.put(response.body()!!.inputs[0].name,myObject)
                   Log.d("object global json",globalObject.toString())
                   // Log.d("tookeeeeeeeen",token.toString())
                   // Log.d("session",sessionid.toString()+";"+token.toString())
                    Log.d("token",token.toString())
                    val toto=TextUtils.substring(token.toString(),19,55).toString()
                    Log.d("tooto",toto.toString())

                    viewModel.postFormProcess(sessionid+";"+token,  toto,dd,globalObject.toString())
                    val repository1 = Repository()
                    val viewModelFactory1 = MainViewModelFactory(repository1)
                    viewModel1 = ViewModelProvider (this, viewModelFactory1).get(MainViewModel::class.java)
                    viewModel1.myForm.observe(this, Observer { response ->
                        if(response.isSuccessful){
                          //  Log.d(" succes post form",response.body().toString())
                            val mToast9 = Toast.makeText(applicationContext,"succes post form"+response.body().toString(), Toast.LENGTH_SHORT)
                            mToast9.show()
                        }else{
                            Log.d(" post form",response.code().toString())
                        }
                    })
                }

            } else {
                val mToast3 = Toast.makeText(applicationContext,"Erreur"+response.code(), Toast.LENGTH_SHORT)
                mToast3.show()
            }
        })


    }

}