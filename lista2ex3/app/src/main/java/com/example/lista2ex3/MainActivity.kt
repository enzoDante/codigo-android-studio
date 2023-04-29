package com.example.lista2ex3

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONArray


class MainActivity : AppCompatActivity() {

    //private val dados = HashMap<String, Any>()

    private var TAG: String ="APP"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnadd: Button = findViewById(R.id.btnadd)
        var btndados: Button = findViewById(R.id.btnanalise)
        btnadd.setOnClickListener {
            visibilidade(true)
        }
        btndados.setOnClickListener {
            visibilidade(false)
            databasef()
        }

        var btncad: Button = findViewById(R.id.salvar)
        btncad.setOnClickListener {
            cadastroDad()
        }

    }
    fun cadastroDad(){
        var nome: EditText = findViewById(R.id.nome)
        var altura:EditText = findViewById(R.id.altura)
        var peso: EditText = findViewById(R.id.peso)

        var imc = peso.text.toString().toFloat() / (altura.text.toString().toFloat() * altura.text.toString().toFloat())


        salvarDadosHash(nome.text.toString(), altura.text.toString(), peso.text.toString(), imc.toString())

    }
    fun visibilidade(cadastroDados: Boolean){
        var linearcad: LinearLayout = findViewById(R.id.linearCad)
        var linearDados: LinearLayout = findViewById(R.id.linearDados)
        linearcad.isVisible = cadastroDados
        linearDados.isVisible = !cadastroDados


    }

    fun salvarDadosHash(nome: String, altura: String, peso: String, imc: String){

        val dados = HashMap<String, Any>()
        dados["nome"] = nome
        dados["altura"] = altura
        dados["peso"] = peso
        dados["imc"] = imc

        var Caminho = nome.replace(" ", "")
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("usuarios2/"+Caminho+"/")
        myRef.setValue(dados)
        Toast.makeText(baseContext, "Adicionado com sucesso!", Toast.LENGTH_SHORT).show()

    }
    fun databasef(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("usuarios2/")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
            // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue() as Map<String, String>
                Log.d(TAG, "Value is: $value")

                for(i in value){
                    //criar os elementos na tela!!!!
                    println(i)
                    println(i.value)
                    val valores = i.value as HashMap<String, String>
                    println(valores)
                    println(valores["altura"])
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })



        /*val databaseGet = Firebase.database.reference
        databaseGet.child("usuarios").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            println(it)

            var texto: TextView = findViewById(R.id.nometext)

        }*/

        //var texto: TextView = findViewById(R.id.nometext)
        //texto.text = myRef.toString()
        //myRef.setValue(dados)

    }
}