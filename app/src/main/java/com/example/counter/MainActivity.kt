package com.example.counter

import android.content.Context
import android.icu.number.Precision.increment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    //Use lateinit to initialize our views to be used throughout the class
    //Will define them in onCreate
    private lateinit var numberText: TextView
    private lateinit var numberInput: EditText
    private lateinit var interval: EditText
    private lateinit var summary: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get a reference to our buttons
        val submitButton: Button = findViewById(R.id.activity_main_bt_submit)
        val randomButton: Button = findViewById(R.id.activity_main_bt_random_number)
        val incrementButton: Button = findViewById(R.id.activity_main_bt_increment)
        val decrementButton: Button = findViewById(R.id.activity_main_bt_decrement)

        //Set clickListeners for each button
        submitButton.setOnClickListener { submitNumber() }
        randomButton.setOnClickListener { generateRandomNumber() }
        incrementButton.setOnClickListener { changeNumber("+") }
        decrementButton.setOnClickListener { changeNumber("-") }

        //Set the values to our views initialized with lateinit
        numberText = findViewById(R.id.activity_main_tv_number)
        numberInput = findViewById(R.id.activity_main_et_number_input)
        interval = findViewById(R.id.activity_main_et_interval)
        summary = findViewById(R.id.activity_main_tv_summary)

        if(savedInstanceState != null){
            numberText.text = savedInstanceState.getString("myNumber")
            interval.setText(savedInstanceState.getString("myInterval"))
            summary.text = savedInstanceState.getString("mySummary")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("myNumber", numberText.text.toString())
        outState.putString("myInterval", interval.text.toString())
        outState.putString("mySummary", summary.text.toString())
    }

    //Get a number from an editText and display it to the screen
    private fun submitNumber(){
        //Get the number in the editText the user wants to submit
        var startingNumber = numberInput.text.toString()

        //If user left editText blank, default value to 10
        if(startingNumber == "") {
            startingNumber = "10"
        }

        //Set our starting number in the textView
        numberText.text = startingNumber

        //Clear the editText after submitting the number
        numberInput.setText("")

        //Hide the keyboard
        hideKeyboard()
    }

    //Generate a random number and display it to the screen
    private fun generateRandomNumber(){
        //generate a random number between -100 to 100
        val randomNumber = (-11..100).random()

        //update numberText TextView
        numberText.text = randomNumber.toString()
    }

    //Increment or decrement a number by a given value
    private fun changeNumber(operation: String){
        //Get the current number
        val currentNumber = numberText.text.toString().toInt()
        var value = interval.text.toString() //don't .toInt() yet since we have to check if it is blank and assign a default value if so

        //Check to see if the increment/decrement value is blank, if so default to 1
        if (value == ""){
            value = "1"
        }

        //Either increment or decrement based on the value of operation
        if(operation == "+"){
            //Determine new number to display and display it
            val newNumber = currentNumber + value.toInt()
            numberText.text = newNumber.toString()

            //update the summary message
            summary.text = "$currentNumber + $value = $newNumber"

        }else{
            //Determine new number to display and display it
            val newNumber = currentNumber - value.toInt()
            numberText.text = newNumber.toString()

            //update the summary message
            summary.text = "$currentNumber - $value = $newNumber"

        }

        //Hide the keyboard
        hideKeyboard()
    }

    //Hide the keyboard
    private fun hideKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(numberInput.windowToken, 0)
    }
}