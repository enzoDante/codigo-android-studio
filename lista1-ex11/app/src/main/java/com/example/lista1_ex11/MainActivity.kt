package com.example.lista1_ex11

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
            var num: EditText = findViewById(R.id.edittext1)
            var text: TextView = findViewById(R.id.text)

            var numero = num.text.toString().toInt()
            var hex = numero.toString(16)
            text.text = hex.uppercase()
        }
    }
}