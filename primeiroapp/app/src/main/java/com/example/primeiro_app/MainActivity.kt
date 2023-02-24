package com.example.primeiro_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn: Button = findViewById(R.id.btn)
        btn.setOnClickListener{
            somar()
        }
    }

    //funções aq
    fun somar(){
        var num1: EditText = findViewById(R.id.editex)
        var num2: EditText = findViewById(R.id.editex2)
        var txt: TextView = findViewById(R.id.text)

        txt.text = (num1.text.toString().toInt() + num2.text.toString().toInt()).toString()
    }
}