package com.example.demoproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import java.util.*
        // for the command line
        // adb emu avd hostmicon
class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var button : Button
    lateinit var textView : TextView
    private val REQUEST_CODE_SPEECH_INPUT = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.voice_btn)
        button.setOnClickListener(this)
        textView = findViewById(R.id.textV)
    }

    override fun onClick (view: View?){
        when(view?.id){
            R.id.voice_btn->{
                speak()
            }
        }
    }

    private fun speak() {
        // intent that will show the dialog
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say something")



        try {
           // if no error show the dialog
           //registerForActivityResult(ActivityResultContracts.GetContent(spe))

            startActivityForResult(mIntent,REQUEST_CODE_SPEECH_INPUT)
        }
        catch (e: Exception){
            // if error: get error message and show in a toast
            Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)     {
            REQUEST_CODE_SPEECH_INPUT ->{
                if (resultCode == Activity.RESULT_OK && null != data) {
                    // get text from result
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    // set the text in the TextView
                    textView.text = result?.get(0)

                }
            }
        }
    }
}

