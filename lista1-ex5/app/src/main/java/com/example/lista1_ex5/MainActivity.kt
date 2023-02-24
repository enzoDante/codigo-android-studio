package com.example.lista1_ex5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var radiobutton: RadioGroup = findViewById(R.id.radiogroup1)

        radiobutton.setOnCheckedChangeListener { radioGroup, i ->
            var salario: EditText = findViewById(R.id.editText1)
            var texto: TextView = findViewById(R.id.texto)
            // i é o id do componente
            if(i == 2131231060){
                texto.text = "novo salário é\nR$ ${salario.text.toString().toFloat() + (salario.text.toString().toFloat() * 0.40)}"
            }
            else if(i == 2131231061){
                texto.text = "novo salário é\nR$ ${salario.text.toString().toFloat() + (salario.text.toString().toFloat() * 0.45)}"
            }else{
                texto.text = "novo salário é\nR$ ${salario.text.toString().toFloat() + (salario.text.toString().toFloat() * 0.50)}"
            }

        }

    }
}