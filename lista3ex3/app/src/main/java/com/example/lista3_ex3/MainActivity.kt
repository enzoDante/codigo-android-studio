package com.example.lista3_ex3

import android.speech.tts.TextToSpeech
import java.util.Locale
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlin.random.Random

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    lateinit var textToSpeech: TextToSpeech


    var frases = listOf<String>(
        "Acordar de bem com a vida é o primeiro passo para ter um bom dia.",
        "Por onde você for, seja seu próprio sol.",
        "Está na hora de acordar.",
        "Cada amanhecer é um recomeço.",
        "Que a frescura de cada manhã seja uma inspiração."
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
    }

    fun main(){
        textToSpeech = TextToSpeech(this, this)
        var btn:Button = findViewById(R.id.btn)

        btn.setOnClickListener {
            var randoInt = Random.nextInt(0, 4)
            lermsg(frases[randoInt])
        }
    }
    fun lermsg(falar:String){
        println(frases)
        textToSpeech!!.speak(falar, TextToSpeech.QUEUE_FLUSH, null,null)

    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val localeBR = Locale("pt", "BR")
            val result = textToSpeech!!.setLanguage(localeBR)
        }
    }
    public override fun onDestroy() {
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }
        super.onDestroy()
    }
}