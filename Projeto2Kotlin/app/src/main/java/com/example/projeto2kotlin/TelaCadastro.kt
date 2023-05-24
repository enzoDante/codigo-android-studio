package com.example.projeto2kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class TelaCadastro : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        init()
    }

    fun init(){
        auth = FirebaseAuth.getInstance()
        val btnCadastrar: Button = findViewById(R.id.btnCadastrar)
        btnCadastrar.setOnClickListener{
            firebaseAuthCadastro(
                findViewById<EditText>(R.id.txtEmailCadastro).text.toString(),
                findViewById<EditText>(R.id.txtSenhaCadastro).text.toString()
            )
        }
    }

    fun telaLogin(view: View){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    fun firebaseAuthCadastro(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var user : FirebaseUser? = auth.currentUser
                    var idUsuario:String = user!!.uid
                    cadastrarDados(idUsuario)
                    Toast.makeText(baseContext, "Usuário criado com sucesso", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(baseContext, "Falha ao criar usuário", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    fun cadastrarDados(id: String){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("/cadastros/")
        val txtNomeCadastro: EditText = findViewById(R.id.txtNomeCadastro)
        val txtEmail: EditText = findViewById(R.id.txtEmailCadastro)
        val novoCadastro = mapOf(
            "id" to id,
            "email" to txtEmail.text.toString(),
            "nome" to txtNomeCadastro.text.toString()
        )

        myRef.push().setValue(novoCadastro).addOnCompleteListener{task ->
            if(task.isSuccessful) {
                Toast.makeText(baseContext, "Nome criado com sucesso", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(baseContext, "Nome inválido", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

}