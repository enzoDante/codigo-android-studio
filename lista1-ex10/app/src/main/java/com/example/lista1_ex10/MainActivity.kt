package com.example.lista1_ex10

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
            var texto: TextView = findViewById(R.id.text)
            var x = bin(num.text.toString())
            texto.text = x.toString()
        }

    }
    fun bin(n: String): String{
        var num = n.toInt()
        var binario = ""
        var binarioreverso = ""

        while (num != 0){
            binario += "${(num%2).toString()}"
            num = num/2
        }
        for(i in binario.length - 1 downTo 0){
            binarioreverso += binario[i]
        }

        return binarioreverso
    }
}