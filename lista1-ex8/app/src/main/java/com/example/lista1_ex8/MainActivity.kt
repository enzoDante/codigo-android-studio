package com.example.lista1_ex8

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn1: Button = findViewById(R.id.btn1)
        var btn2: Button = findViewById(R.id.btn2)
        var btn3: Button = findViewById(R.id.btn3)
        var btn4: Button = findViewById(R.id.btn4)
        var btn5: Button = findViewById(R.id.btn5)
        var btn6: Button = findViewById(R.id.btn6)
        var btn7: Button = findViewById(R.id.btn7)
        var btn8: Button = findViewById(R.id.btn8)
        var btn9: Button = findViewById(R.id.btn9)
        var btn10: Button = findViewById(R.id.btn10)
        var btn11: Button = findViewById(R.id.btn11)
        var btn12: Button = findViewById(R.id.btn12)
        var btn13: Button = findViewById(R.id.btn13)
        var btn14: Button = findViewById(R.id.btn14)
        var btn15: Button = findViewById(R.id.btn15)
        var btn16: Button = findViewById(R.id.btn16)
        var btn17: Button = findViewById(R.id.btn17)
        var btn18: Button = findViewById(R.id.btn18)
        var btn19: Button = findViewById(R.id.btn19)
        var btn20: Button = findViewById(R.id.btn20)

        var gmor: TextView = findViewById(R.id.gameover)
        var posicoes = IntArray(20)
        while (true){
            for(i in 0..19){
                posicoes[i] = (0..5).random()
            }
            if(posicoes.count{it == 1} == 7)
                break
        }


        btn1.setOnClickListener {
            if(posicoes[0] == 1){
                gmor.text = "perdeu no btn1"
                btn1.setBackgroundColor(Color.RED)
            }else{
                btn1.setBackgroundColor(Color.GREEN)
            }
        }
        btn2.setOnClickListener{
            if(posicoes[1] == 1){
                gmor.text = "perdeu no btn2"
                btn2.setBackgroundColor(Color.RED)
            }else{
                btn2.setBackgroundColor(Color.GREEN)
            }
        }

        btn3.setOnClickListener{
            if(posicoes[2] == 1){
                gmor.text = "perdeu no btn3"
                btn3.setBackgroundColor(Color.RED)
            }else{
                btn3.setBackgroundColor(Color.GREEN)
            }
        }

        btn4.setOnClickListener{
            if(posicoes[3] == 1){
                gmor.text = "perdeu no btn4"
                btn4.setBackgroundColor(Color.RED)
            }else{
                btn4.setBackgroundColor(Color.GREEN)
            }
        }

        btn5.setOnClickListener{
            if(posicoes[4] == 1){
                gmor.text = "perdeu no btn5"
                btn5.setBackgroundColor(Color.RED)
            }else{
                btn5.setBackgroundColor(Color.GREEN)
            }
        }

        btn6.setOnClickListener{
            if(posicoes[5] == 1){
                gmor.text = "perdeu no btn6"
                btn6.setBackgroundColor(Color.RED)
            }else{
                btn6.setBackgroundColor(Color.GREEN)
            }
        }

        btn7.setOnClickListener{
            if(posicoes[6] == 1){
                gmor.text = "perdeu no btn7"
                btn7.setBackgroundColor(Color.RED)
            }else{
                btn7.setBackgroundColor(Color.GREEN)
            }
        }

        btn8.setOnClickListener{
            if(posicoes[7] == 1){
                gmor.text = "perdeu no btn8"
                btn8.setBackgroundColor(Color.RED)
            }else{
                btn8.setBackgroundColor(Color.GREEN)
            }
        }

        btn9.setOnClickListener{
            if(posicoes[8] == 1){
                gmor.text = "perdeu no btn9"
                btn9.setBackgroundColor(Color.RED)
            }else{
                btn9.setBackgroundColor(Color.GREEN)
            }
        }

        btn10.setOnClickListener{
            if(posicoes[9] == 1){
                gmor.text = "perdeu no btn10"
                btn10.setBackgroundColor(Color.RED)
            }else{
                btn10.setBackgroundColor(Color.GREEN)
            }
        }

        btn11.setOnClickListener{
            if(posicoes[10] == 1){
                gmor.text = "perdeu no btn11"
                btn11.setBackgroundColor(Color.RED)
            }else{
                btn11.setBackgroundColor(Color.GREEN)
            }
        }

        btn12.setOnClickListener{
            if(posicoes[11] == 1){
                gmor.text = "perdeu no btn12"
                btn12.setBackgroundColor(Color.RED)
            }else{
                btn12.setBackgroundColor(Color.GREEN)
            }
        }

        btn13.setOnClickListener{
            if(posicoes[12] == 1){
                gmor.text = "perdeu no btn13"
                btn13.setBackgroundColor(Color.RED)
            }else{
                btn13.setBackgroundColor(Color.GREEN)
            }
        }

        btn14.setOnClickListener{
            if(posicoes[13] == 1){
                gmor.text = "perdeu no btn14"
                btn14.setBackgroundColor(Color.RED)
            }else{
                btn14.setBackgroundColor(Color.GREEN)
            }
        }

        btn15.setOnClickListener{
            if(posicoes[14] == 1){
                gmor.text = "perdeu no btn15"
                btn15.setBackgroundColor(Color.RED)
            }else{
                btn15.setBackgroundColor(Color.GREEN)
            }
        }

        btn16.setOnClickListener{
            if(posicoes[15] == 1){
                gmor.text = "perdeu no btn16"
                btn16.setBackgroundColor(Color.RED)
            }else{
                btn16.setBackgroundColor(Color.GREEN)
            }
        }

        btn17.setOnClickListener{
            if(posicoes[16] == 1){
                gmor.text = "perdeu no btn17"
                btn17.setBackgroundColor(Color.RED)
            }else{
                btn17.setBackgroundColor(Color.GREEN)
            }
        }

        btn18.setOnClickListener{
            if(posicoes[17] == 1){
                gmor.text = "perdeu no btn18"
                btn18.setBackgroundColor(Color.RED)
            }else{
                btn18.setBackgroundColor(Color.GREEN)
            }
        }

        btn19.setOnClickListener{
            if(posicoes[18] == 1){
                gmor.text = "perdeu no btn19"
                btn19.setBackgroundColor(Color.RED)
            }else{
                btn19.setBackgroundColor(Color.GREEN)
            }
        }

        btn20.setOnClickListener{
            if(posicoes[19] == 1){
                gmor.text = "perdeu no btn20"
                btn20.setBackgroundColor(Color.RED)
            }else{
                btn20.setBackgroundColor(Color.GREEN)
            }
        }

    }
}