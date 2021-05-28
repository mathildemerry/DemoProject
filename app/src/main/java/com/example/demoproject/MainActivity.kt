package com.example.demoproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import java.util.*
        // for the command line
        // adb emu avd hostmicon
class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    lateinit var button : Button
    lateinit var textView : TextView
    private val REQUEST_CODE_SPEECH_INPUT = 0

    private var tts: TextToSpeech? = null //variable for text to speech
            //nullable being initialized in onCreate


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.voice_btn)
//        button.setOnClickListener(this)
        textView = findViewById(R.id.textV)
        button.setOnClickListener{
            speak()
        }

        //for the TTS
        tts = TextToSpeech(this,this) //initializing TTS
        // main activity is both listener and context
        var button2 : Button
        button2 = findViewById(R.id.btnTTS)
        var etEnteredText : EditText
        etEnteredText = findViewById(R.id.editT)


        button2.setOnClickListener{
            if (etEnteredText.text.isEmpty()) {
                Toast.makeText(this@MainActivity,"Enter a text", Toast.LENGTH_SHORT).show()
            } else{
                readOut(etEnteredText.text.toString())
            }
        }
        //read text aloud extra
        var demoText : TextView
        demoText = findViewById(R.id.readDemoText)
        var speakerButton : ImageButton
        speakerButton = findViewById(R.id.speakBtn)
        speakerButton.setOnClickListener{
            readOut(demoText.text.toString())
        }



    }


    private fun speak() {
        // intent that will show the dialog
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
       // mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault()) //you can set your own language if not the default ex. en-US
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say something")

        startActivityForResult(mIntent,REQUEST_CODE_SPEECH_INPUT)





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

// text to speech
            //on init is a fun in interface onInitListener --> parameters success or error
    override fun onInit(status: Int) {
            //if textToSpeech can be used: (status ok)
         if (status == TextToSpeech.SUCCESS){
                    //language is set
                    val output = tts!!.setLanguage(Locale.US)

                    if (output == TextToSpeech.LANG_MISSING_DATA || output == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","The language is not supported")
                    }

                }

            }
    private fun readOut(text: String) {
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null, 1.toString())

    }

}

