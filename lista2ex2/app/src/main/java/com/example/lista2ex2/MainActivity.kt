package com.example.lista2ex2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private var TAG: String ="APP"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var btndados: Button = findViewById(R.id.btndados)
        btndados.setOnClickListener {
            cadastroDados()
        }
    }

    fun cadastroDados(){
        var nome: EditText = findViewById(R.id.nome)
        var rg: EditText = findViewById(R.id.rg)
        var cpf: EditText = findViewById(R.id.cpf)
        var email: EditText = findViewById(R.id.email)
        var senha: EditText = findViewById(R.id.senha)

        val dados = HashMap<String, Any>()

        dados["nome"] = nome.text.toString()
        dados["rg"] = rg.text.toString()
        dados["cpf"] = cpf.text.toString()
        dados["email"] = email.text.toString()
        dados["senha"] = senha.text.toString()

        var nnomec = nome.text.toString().replace(" ", "")
        var eemailc = email.text.toString().replace(".", "")
        var caminho = "${nnomec}_${eemailc}"

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("usuarios/${caminho}/")
        myRef.setValue(dados)

        Toast.makeText(baseContext, "Adicionado com sucesso!", Toast.LENGTH_SHORT).show()
    }
}