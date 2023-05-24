package com.example.projeto2kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        auth = FirebaseAuth.getInstance()
        val txtEmail : EditText = findViewById(R.id.txtEmailLogin)
        val txtSenha : EditText = findViewById(R.id.txtSenhaLogin)
        val btnLogar: Button = findViewById(R.id.btnLogar)
        btnLogar.setOnClickListener {
            var email = txtEmail.text.toString()
            var password = txtSenha.text.toString()
            firebaseAuthLogin(email, password)
        }
        val btnEsqueciSenha: Button = findViewById(R.id.btnEsqueciSenha)
        btnEsqueciSenha.setOnClickListener{
            esqueciSenha()
        }
    }

    fun logar(){
        var email = findViewById<EditText>(R.id.txtEmailLogin).text.toString()
        var password = findViewById<EditText>(R.id.txtSenhaLogin).text.toString()
        firebaseAuthLogin(email, password)

    }

    fun esqueciSenha(){
        val auth = FirebaseAuth.getInstance()
        var txtEmail: EditText = findViewById(R.id.txtEmailLogin)
        if (txtEmail.text.toString() != ""){
            auth.sendPasswordResetEmail(txtEmail.text.toString()).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(baseContext, "Email enviado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "Email não encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun telaCadastro(view : View){
        val intent = Intent(this,TelaCadastro::class.java)
        startActivity(intent)
    }

    fun firebaseAuthLogin(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val intent = Intent(this,MainScreen::class.java)
                startActivity(intent)
                Toast.makeText(baseContext, "Logado com sucesso.", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(baseContext, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
            }

        }
    }


}