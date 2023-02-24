package com.example.lista1_ex3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var checkbox: CheckBox = findViewById(R.id.checkBox)

        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            var texto: TextView = findViewById(R.id.texto)
            if(isChecked){
                var msg: EditText = findViewById(R.id.editText1)
                texto.text = msg.text.toString()
            }
            else
                texto.text = ""
        }
    }
}