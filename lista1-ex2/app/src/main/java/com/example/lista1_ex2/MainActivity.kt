package com.example.lista1_ex2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            var num: EditText = findViewById(R.id.editText1)
            var tabuada: TextView = findViewById(R.id.texto)
            tabuada.text = ""

            var texts: String = ""

            for (i in 0..10){
                texts += "${num.text.toString()} * ${i} = ${(num.text.toString().toInt() * i).toString()}\n"
            }
            tabuada.text = texts
        }

    }
}