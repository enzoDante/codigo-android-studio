package com.example.lista1_ex6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var total = 0
        var valores = 0.0F

        var button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            var texto: TextView = findViewById(R.id.texto)
            var num: EditText = findViewById(R.id.editText1)

            if(num.text.toString().toFloat() >= 0 && num.text.toString().toFloat() <= 10) {
                valores += num.text.toString().toFloat()
                total++

                var ratingbar: RatingBar = findViewById(R.id.ratingBar)
                ratingbar.rating = valores / total

                texto.text = "Você digitou ${total.toString()} vezes"
            }
            else
                texto.text = "Digite um valor entre 0 e 10!"

        }
    }
}