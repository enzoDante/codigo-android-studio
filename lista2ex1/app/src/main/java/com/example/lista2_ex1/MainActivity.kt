package com.example.lista2_ex1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var TAG: String ="APP"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        var buttonlogin: Button = findViewById(R.id.btntelalogin)
        var buttoncad: Button = findViewById(R.id.btntelacadastro)
        var layoutCad: LinearLayout = findViewById(R.id.linearCadastro)
        var layoutLog: LinearLayout = findViewById(R.id.linearLogin)
        buttoncad.setOnClickListener {
            //layoutCad.visibility = layoutCad.INVISIBLE
            layoutCad.isVisible = true
            layoutLog.isVisible = false
        }
        buttonlogin.setOnClickListener {
            layoutCad.isVisible = false
            layoutLog.isVisible = true
        }



        var buttoncadastrar: Button = findViewById(R.id.button)
        var buttonlogar: Button = findViewById(R.id.buttonl)
        buttoncadastrar.setOnClickListener {
            var email: EditText = findViewById(R.id.editText1)
            var senha: EditText = findViewById(R.id.editText2)

            cadastro(email.text.toString(), senha.text.toString())
        }
        buttonlogar.setOnClickListener {
            var email: EditText = findViewById(R.id.editText1l)
            var senha: EditText = findViewById(R.id.editText2l)
            var estado: EditText = findViewById(R.id.humor)
            verificaruser(email.text.toString(), senha.text.toString(), estado.text.toString())
        }
    }
    //=========login================
    fun verificaruser(email: String, senha: String, estado: String){
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    //var text: TextView = findViewById(R.id.options1)
                    //text.text = user?.email.toString()

                    if(!user?.isEmailVerified()!!){
                        user.sendEmailVerification().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "Email enviado.")
                            }
                        }
                    }
                    //tornar linear layouts invisiveis e mostrar outros
                    /*var layoutlogcad: LinearLayout = findViewById(R.id.bntlogcad)
                    var layoutCad: LinearLayout = findViewById(R.id.linearCadastro)
                    var layoutLog: LinearLayout = findViewById(R.id.linearLogin)
                    layoutCad.isVisible = false
                    layoutLog.isVisible = false
                    layoutlogcad.isVisible = false*/
                    alterarvisibilidade(true)
                    //===outros
                    telaprincipal()
                    //============================================


                    Toast.makeText(baseContext, "Logado com sucesso.", Toast.LENGTH_SHORT).show()

                    //post realtime do estado de humor=================================
                    val dados = HashMap<String, Any>()
                    dados["email"] = email
                    dados["humor"] = estado
                    var tempCaminho = email.replace(".", "")
                    val database = FirebaseDatabase.getInstance()
                    val myRef = database.getReference("usuario/"+tempCaminho+"/")
                    myRef.setValue(dados)
                    //===============================================================
                } else {
                    Toast.makeText(baseContext, "Erro Login.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun cadastro(email: String, senha: String) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                var user : FirebaseUser? = auth.currentUser
                var idUsuario: String? = user?.uid
                Toast.makeText(baseContext, "Usuário Criado com sucesso",Toast.LENGTH_SHORT).show()
                verificaruser(email, senha, "neutro")

            } else {
                Toast.makeText(baseContext, "Falha ao criar usuário", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun telaprincipal(){
        //var layoutprincipal: LinearLayout = findViewById(R.id.telaprincipal)
        //layoutprincipal.isVisible = true
        var bemvindotexto: TextView = findViewById(R.id.usuarioola)
        val user = FirebaseAuth.getInstance().currentUser

        bemvindotexto.text = "Bem vindo(a), ${user?.email.toString()}!"

        var btnredef: Button = findViewById(R.id.btnredef)
        var btndeletec: Button = findViewById(R.id.btndeleteconta)
        btnredef.setOnClickListener {
            var nsenha: EditText = findViewById(R.id.editTextredef)
            redefinirsenha(nsenha.text.toString())
        }
        btndeletec.setOnClickListener {
            deletarconta()

        }
    }
    fun redefinirsenha(nsenha: String){
        val user = FirebaseAuth.getInstance().currentUser
        //Toast.makeText(baseContext, "Senha ${nsenha}, ${user?.email.toString()}",Toast.LENGTH_SHORT).show()
        user!!.updatePassword(nsenha)!!.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Senha atualizada.")
                Toast.makeText(baseContext, "Senha atualizada",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(baseContext, "erro / senha deve conter mais de 6 caracteres",Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun deletarconta(){
        val user = FirebaseAuth.getInstance().currentUser
        user!!.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "usuario excluído.")
                //var layoutprincipal: LinearLayout = findViewById(R.id.telaprincipal)
                //layoutprincipal.isVisible = false
                alterarvisibilidade(false)


            }
        }
    }
    fun alterarvisibilidade(logado:Boolean){
        var layoutlogcad: LinearLayout = findViewById(R.id.bntlogcad)
        var layoutCad: LinearLayout = findViewById(R.id.linearCadastro)
        var layoutLog: LinearLayout = findViewById(R.id.linearLogin)
        layoutCad.isVisible = !logado
        layoutLog.isVisible = false
        layoutlogcad.isVisible = !logado

        var layoutprincipal: LinearLayout = findViewById(R.id.telaprincipal)
        layoutprincipal.isVisible = logado
    }
}