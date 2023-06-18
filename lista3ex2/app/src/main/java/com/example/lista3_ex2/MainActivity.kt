package com.example.lista3_ex2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), SensorEventListener {
    lateinit var acelerometro: Sensor
    lateinit var gerenciadorSensor: SensorManager
    var Yantigo : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializarAcelerometro()

    }

    fun inicializarAcelerometro(){
        gerenciadorSensor = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        acelerometro = gerenciadorSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gerenciadorSensor.registerListener(this, this.acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }
    fun salvarDados(y:Int){
        val dados = HashMap<String, Any>()
        println(y)
        dados["latitude"] = y.toString()
        println(dados)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("coordenadas/")
        myRef.setValue(dados)
    }
    override fun onSensorChanged(event: SensorEvent?) {
        val lblAcelerometro: TextView = findViewById(R.id.lblAcelerometro)
        //var x: Float = 0f
        var y: Float = 0f
        //var z: Float = 0f
        if (event != null) {
            //x = event.values[0]
            y = event.values[1]
            //z = event.values[2]
        }
        lblAcelerometro.text =  "\n" + y.toInt().toString() + "\n"
        if(Yantigo != y.toInt()){
            salvarDados(y.toInt())
            Yantigo = y.toInt()
        }
    }
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }


}