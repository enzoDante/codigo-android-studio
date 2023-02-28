package com.example.lista1_ex9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var vezesJogada: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var textom: TextView = findViewById(R.id.textov)

        var xvez: Boolean
        var btn1: Button = findViewById(R.id.btn1)

        btn1.setOnClickListener {
            textom.text = ""
            vezesJogada = 0
            xvez = true
            criarTabuleiro(xvez)
        }

    }
    fun criarTabuleiro(xvez:Boolean){
        var vez = xvez
        var tabuleiro = Array(3) {IntArray(3)}

        val layoutPrincipal: LinearLayout = findViewById(R.id.linearl)
        layoutPrincipal.removeAllViews()

        for(j in 1..3){
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL

            for(i in 1..3){
                val button = Button(this)
                button.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                button.text = "-"
                button.setOnClickListener {
                    vezesJogada++
                    if(vez){
                        button.text = "X"
                        vez = false
                        tabuleiro[j-1][i-1] = 1
                    }else{
                        button.text = "O"
                        vez = true
                        tabuleiro[j-1][i-1] = 2
                    }
                    button.isEnabled = false
                    analisarV(tabuleiro)
                }
                linearLayout.addView(button)

            }
            layoutPrincipal.addView(linearLayout)
        }
    }

    fun analisarV(ta: Array<IntArray>){
        var textom: TextView = findViewById(R.id.textov)
        //empate
        if(vezesJogada == 9){
            textom.text = "Empate!"
        }
        if(vezesJogada >= 5){
            //diagonais
            if(ta[0][0] == ta[1][1] && ta[1][1] == ta[2][2]){
                if(ta[0][0] == 1)
                    textom.text = "d1 Vitória de X"
                else
                    textom.text = "d1 Vitória de O"
            }

            if(ta[0][2] == ta[1][1] && ta[1][1] == ta[2][0]){
                if(ta[0][2] == 1)
                    textom.text = "d2 Vitória de X"
                else
                    textom.text = "d2 Vitória de O"
            }

            //horizontal
            for(i in 0..2){
                if(ta[i][0] == ta[i][1] && ta[i][1] == ta[i][2]){
                    if(ta[i][0] == 1)
                        textom.text = "h Vitória de X"
                    else
                        textom.text = "h Vitória de O"
                }
            }
            //vertical
            for(i in 0..2){
                if(ta[0][i] == ta[1][i] && ta[1][i] == ta[2][i]){
                    if(ta[0][i] == 1)
                        textom.text = "v Vitória de X"
                    else
                        textom.text = "v Vitória de O"
                }
            }
        }

    }
}