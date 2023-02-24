package com.example.lista1_ex7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button: Button = findViewById(R.id.buton)
        button.setOnClickListener {
            var texto: EditText = findViewById(R.id.editText1)
            criarText(texto)

        }

    }

    fun criarText(msg: EditText){
        var linearLayout: LinearLayout = findViewById(R.id.linearLayout)
        var Textview = TextView(this)
        Textview.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        Textview.text = msg.text.toString()
        linearLayout.addView(Textview)

    }
}