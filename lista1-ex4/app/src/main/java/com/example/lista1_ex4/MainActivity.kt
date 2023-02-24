package com.example.lista1_ex4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var checbox1: CheckBox = findViewById(R.id.checkBox1)
        var checbox2: CheckBox = findViewById(R.id.checkBox2)
        var checbox3: CheckBox = findViewById(R.id.checkBox3)
        var checbox4: CheckBox = findViewById(R.id.checkBox4)
        var checbox5: CheckBox = findViewById(R.id.checkBox5)

        var texto: TextView = findViewById(R.id.texto)
        texto.text = ""
        var valor: Float = 0.0F

        checbox1.setOnCheckedChangeListener { buttonview, isChecked ->
            if(isChecked){
                valor += 20.00F
                texto.text = "R$ ${valor}"
            }
            else{
                valor -= 20.00F
                texto.text = "R$ ${valor}"
            }

        }

        checbox2.setOnCheckedChangeListener { buttonview, isChecked ->
            if(isChecked){
                valor += 5.95F
                texto.text = "R$ ${valor}"
            }
            else{
                valor -= 5.95F
                texto.text = "R$ ${valor}"
            }
        }

        checbox3.setOnCheckedChangeListener { buttonview, isChecked ->
            if(isChecked){
                valor += 76.78F
                texto.text = "R$ ${valor}"
            }
            else{
                valor -= 76.78F
                texto.text = "R$ ${valor}"
            }
        }

        checbox4.setOnCheckedChangeListener { buttonview, isChecked ->
            if(isChecked){
                valor += 9.38F
                texto.text = "R$ ${valor}"
            }
            else{
                valor -= 9.38F
                texto.text = "R$ ${valor}"
            }
        }

        checbox5.setOnCheckedChangeListener { buttonview, isChecked ->
            if(isChecked){
                valor += 12.00F
                texto.text = "R$ ${valor}"
            }
            else{
                valor -= 12.00F
                texto.text = "R$ ${valor}"
            }
        }
    }
}