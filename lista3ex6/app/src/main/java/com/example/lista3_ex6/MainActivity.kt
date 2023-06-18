package com.example.lista3_ex6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import java.util.Locale
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset
import android.util.Log

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    lateinit var textToSpeech: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textToSpeech = TextToSpeech(this, this)
//instancia do botão
        var botaoParaFalar: Button = findViewById(R.id.btn)
        botaoParaFalar!!.setOnClickListener {
            acessarApi()
        }
    }
    fun falar(frase : String){
        textToSpeech!!.speak(frase, TextToSpeech.QUEUE_FLUSH, null,null)
    }
    fun acessarApi(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.adviceslip.com/advice"
        val requestBody = ""
        val stringReq : StringRequest =
            object : StringRequest(Method.GET, url,
                Response.Listener { response ->
                    var resposta = response.toString()

                    val jsonob: JSONObject = JSONObject(resposta)
                    val xa = JSONObject(jsonob["slip"].toString())
                    println(xa["advice"])
                    falar(xa["advice"].toString())

                },
                Response.ErrorListener { error ->
                    Log.d("API", "error => $error")
                }
            ){
                override fun getBody(): ByteArray {
                    return requestBody.toByteArray(Charset.defaultCharset())
                }
            }
        queue.add(stringReq)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val localeBR = Locale("en", "US")
            val result = textToSpeech!!.setLanguage(localeBR)
        }
    }
    public override fun onDestroy() {
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }
        super.onDestroy()
    }

}