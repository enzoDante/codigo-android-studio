package com.example.projeto3bi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.speech.tts.TextToSpeech
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.speech.RecognizerIntent
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONArray
import java.util.Locale
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener, SensorEventListener {
    lateinit var textToSpeech: TextToSpeech
    lateinit var locationManager : LocationManager
    lateinit var acelerometro: Sensor
    lateinit var gerenciadorSensor: SensorManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textToSpeech = TextToSpeech(this, this)
        permissoesUsuario()
        permissoesUsuario2()

        init()
    }

    fun init(){
        inicializarAcelerometro()
        val btn : Button = findViewById(R.id.btnFalar)
        carregarAudios()
        btn!!.setOnClickListener {
            capturarAudioMicrofone()
        }
    }
    fun permissoesUsuario(){
        val codigoIdentificacao = 201;
        ActivityCompat.requestPermissions(this,arrayOf<String>(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.INTERNET
        ),
            codigoIdentificacao
        )
    }
    fun permissoesUsuario2(){
        val codigoIdentificacao = 200;
        ActivityCompat.requestPermissions(
            this,
            arrayOf<String>(ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION),
            codigoIdentificacao
        )

    }
    override fun onRequestPermissionsResult(codigoIdentificacao: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(codigoIdentificacao, permissions, grantResults)
        if(codigoIdentificacao==201){
            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.checkSelfPermission(this,Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
                }
            }
        }
        if(codigoIdentificacao==200){
            if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.checkSelfPermission(this,ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager = (getSystemService(LOCATION_SERVICE) as LocationManager?)!!
                    locationManager?.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener
                    )
                }
            }
        }
    }
    private fun capturarAudioMicrofone(){
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

    //é executado automaticamente após o fechamento da interface de recuperar audio.
    override fun onActivityResult(requestCode: Int,resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

            SalvarAudio(result?.get(0).toString())
        }
    }
    fun SalvarAudio(texto : String){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("/cadastros/")
        val novoCadastro = mapOf(
            "texto" to "${texto}"
        )
        myRef.push().setValue(novoCadastro).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                carregarAudios()
                analisarAudio(texto)
            } else {
            }
        }

    }
    fun analisarAudio(texto: String){
        //val string = "teste a gravar posição aqui"
        //val check : Boolean = "gravar posição" in texto
        //println("teste seráaaaa q tem??? ${check}")
        if("gravar posição" in texto){
            salvarLocalizacao()
        }
    }
    fun carregarAudios(){
        var linearlayout: LinearLayout = findViewById(R.id.layout2)
        linearlayout.removeAllViews()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("/cadastros/")

        myRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val valores = task.result
                if (valores.exists()) {
                    for (cliente in valores.children) {
                        val id = cliente.key
                        val texto = cliente.child("texto").value as String

                        criarLayout(id.toString(), texto)
                    }
                }
            }
        }
    }
    fun criarLayout(id:String, texto:String){
        var linearlayout: LinearLayout = findViewById(R.id.layout2)

        var bloco = LinearLayout(this)
        //bloco linear layout de produto
        bloco.orientation = LinearLayout.VERTICAL
        bloco.setBackgroundColor(Color.parseColor("#D6D6D6"))
        bloco.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        var novoTextView = TextView(this)

        novoTextView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        novoTextView.text = "Audio: ${texto}"//${desc}
        novoTextView.textSize = 20F

        //botao
        val escutarAudio = Button(this)
        escutarAudio.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        escutarAudio.text = "Ouvir"
        //texto
        escutarAudio.setOnClickListener {
            escutarSom(texto)
        }

        //botao id
        val excluirAudio = Button(this)
        excluirAudio.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        excluirAudio.text = "Excluir"
        excluirAudio.setOnClickListener {
            deletarAudio(id, texto)
        }

        bloco.addView(novoTextView)
        bloco.addView(escutarAudio)
        bloco.addView(excluirAudio)
        linearlayout.addView(bloco, 0)
        var espaco = TextView(this)

        //gambiarra p espacamento abaixo de outros produtos
        espaco.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        espaco.text = "   "
        espaco.height = 100
        linearlayout.addView(espaco, 0)
    }
    fun deletarAudio(id:String, texto:String){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("/cadastros/${id}/")
        myRef.removeValue()
        carregarAudios()

    }
    private fun escutarSom(falar:String) {
        textToSpeech!!.speak(falar, TextToSpeech.QUEUE_FLUSH, null,null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val localeBR = Locale("pt", "BR")
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

    var salvar : Boolean = false
    fun salvarLocalizacao(){
        //println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
        salvar = true
        escutarSom("Posição salva!")
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
           // val database = FirebaseDatabase.getInstance()
            val latitude = location.latitude
            val longitude = location.longitude
            println("===-=-=--=-=-=-=--=-=-=--=-=-=-==-=")
            println(latitude)
            println(longitude)
            /*if(salvar){
                println("aqqq teste")
                val dados = HashMap<String, Any>()
                dados["latitude"] = latitude.toString()
                dados["longitude"] = longitude.toString()
                println(dados)
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("coordenadas/")
                myRef.setValue(dados)
                escutarSom("Posição salva!")
                salvar = false
            }*/
        }
    }
    fun inicializarAcelerometro(){
        gerenciadorSensor = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        acelerometro = gerenciadorSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gerenciadorSensor.registerListener(this, this.acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }
    override fun onSensorChanged(event: SensorEvent?) {
        //val lblAcelerometro: TextView = findViewById(R.id.lblAcelerometro)
        var x: Float = 0f
        var y: Float = 0f
        var z: Float = 0f
        if (event != null) {
            x = event.values[0]
            y = event.values[1]
            z = event.values[2]
            // Calcular a força resultante do acelerômetro
            val acceleration = Math.sqrt((x * x + y * y + z * z).toDouble())

            // Defina um valor de aceleração que indica uma queda (ajuste conforme necessário)
            val threshold = 9.8 * 2.5 // 2.5 vezes a gravidade terrestre

            // Verificar se a aceleração excede o limite definido
            if (acceleration > threshold) {
                escutarSom("Socorro, estou caindo!")
            }
        }
        //lblAcelerometro.text = x.toString() + "\n" + y.toString() + "\n" + z.toString()
    }
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

}
