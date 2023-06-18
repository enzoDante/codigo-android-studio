package com.example.lista3_ex4

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissoesUsuario();
        var caputrarAudio: Button = findViewById(R.id.button)
        caputrarAudio!!.setOnClickListener {
            capturarAudioMicrofone()
        }
    }

    fun permissoesUsuario(){
        val codigoIdentificacao = 201;
        ActivityCompat.requestPermissions(
            this,
            arrayOf<String>(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.INTERNET
            ),
            codigoIdentificacao
        )
    }
    fun capturarAudioMicrofone(){
        //cria um intente com a interface padrão do android para capturar audio.
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
        //Verifica se o sistema operacional oferece o intent de captura de audio
        if (intent.resolveActivity(packageManager) != null){
            startActivityForResult(intent, 202)
        } else{
            Toast.makeText(this,"Não suportado", Toast.LENGTH_SHORT).show()
        }

    }
    fun salvarDados(y:String){
        val dados = HashMap<String, Any>()
        println(y)
        dados["msg"] = y
        println(dados)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("/msgs/")

        //myRef.setValue(dados)
        myRef.push().setValue(dados).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this,"Salvo!!!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"Erro !!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int,resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val lblTextoReconhecido: TextView = findViewById(R.id.lblTextoReconhecido)
            lblTextoReconhecido.text = result?.get(0) ?: "a"
            salvarDados(result?.get(0).toString())
        }
    }
    override fun onRequestPermissionsResult(codigoIdentificacao: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(codigoIdentificacao, permissions, grantResults)
        if(codigoIdentificacao==201){
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.INTERNET
                    ) == PackageManager.PERMISSION_GRANTED){
                }
            }
        }
    }
}