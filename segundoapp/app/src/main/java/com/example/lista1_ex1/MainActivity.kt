package com.example.lista1_ex1

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
            var nome: EditText = findViewById(R.id.editText0)
            var nota1: EditText = findViewById(R.id.editText1)
            var nota2: EditText = findViewById(R.id.editText2)

            var media = (nota1.text.toString().toFloat() + nota2.text.toString().toFloat()) / 2

            var text: TextView = findViewById(R.id.texto)
            if(media >= 6)
                text.text = "${nome.text.toString()} foi aprovado! média: ${media.toString()}"
            else
                text.text = "${nome.text.toString()} foi reprovado! média: ${media.toString()}"

        }
    }

}