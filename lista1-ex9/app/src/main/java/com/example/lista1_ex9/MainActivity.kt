package com.example.lista1_ex9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var texto: TextView = findViewById(R.id.textview)
        var xvez = true

        var btn1: Button = findViewById(R.id.btn1)
        var btn2: Button = findViewById(R.id.btn2)
        var btn3: Button = findViewById(R.id.btn3)
        var btn4: Button = findViewById(R.id.btn4)
        var btn5: Button = findViewById(R.id.btn5)
        var btn6: Button = findViewById(R.id.btn6)
        var btn7: Button = findViewById(R.id.btn7)
        var btn8: Button = findViewById(R.id.btn8)
        var btn9: Button = findViewById(R.id.btn9)

        btn1.setOnClickListener {
            if(xvez){
                btn1.text = "X"
                xvez = false
            }else{
                btn1.text = "O"
                xvez = true
            }
        }
        btn2.setOnClickListener {
            if(xvez){
                btn2.text = "X"
                xvez = false
            }else{
                btn2.text = "O"
                xvez = true
            }
        }
        btn3.setOnClickListener {
            if(xvez){
                btn3.text = "X"
                xvez = false
            }else{
                btn3.text = "O"
                xvez = true
            }
        }
        btn4.setOnClickListener {
            if(xvez){
                btn4.text = "X"
                xvez = false
            }else{
                btn4.text = "O"
                xvez = true
            }
        }
        btn5.setOnClickListener {
            if(xvez){
                btn5.text = "X"
                xvez = false
            }else{
                btn5.text = "O"
                xvez = true
            }
        }
        btn6.setOnClickListener {
            if(xvez){
                btn6.text = "X"
                xvez = false
            }else{
                btn6.text = "O"
                xvez = true
            }
        }
        btn7.setOnClickListener {
            if(xvez){
                btn7.text = "X"
                xvez = false
            }else{
                btn7.text = "O"
                xvez = true
            }
        }
        btn8.setOnClickListener {
            if(xvez){
                btn8.text = "X"
                xvez = false
            }else{
                btn8.text = "O"
                xvez = true
            }
        }
        btn9.setOnClickListener {
            if(xvez){
                btn9.text = "X"
                xvez = false
            }else{
                btn9.text = "O"
                xvez = true
            }
        }

    }
}