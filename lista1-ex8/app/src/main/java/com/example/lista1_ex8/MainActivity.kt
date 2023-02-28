package com.example.lista1_ex8

import android.content.Intent
import android.graphics.Color
import android.icu.text.ListFormatter.Width
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn: Button = findViewById(R.id.btn)
        btn.setOnClickListener {
            iniciarJogo()
        }
    }

    fun iniciarJogo(){
        val layoutPrincipal: LinearLayout = findViewById(R.id.linearLayout)
        layoutPrincipal.removeAllViews()
        /*var posicoes = IntArray(20)
        while (true){
            for(i in 0..19){
                posicoes[i] = (0..5).random()
            }
            if(posicoes.count{it == 1} == 7)
                break
        }*/
        for(j in 1..5){
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL

            for(i in 1..4){
                val button = Button(this)
                button.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                button.text = "-"
                button.setOnClickListener {
                    var numB = (0..5).random()
                    button.isEnabled = false
                    if(numB == 1){
                        button.setBackgroundColor(Color.RED)
                    }else{
                        button.setBackgroundColor(Color.GREEN)
                    }
                }
                linearLayout.addView(button)
            }

            layoutPrincipal.addView(linearLayout)
        }
    }
}