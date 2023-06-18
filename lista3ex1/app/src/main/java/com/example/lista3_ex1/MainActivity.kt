package com.example.lista3_ex1

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var locationManager : LocationManager
    //lateinit var locationListener: LocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissoesUsuario()
    }
    fun permissoesUsuario(){
        val codigoIdentificacao = 200;
        ActivityCompat.requestPermissions(
            this,
            arrayOf<String>(ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION),
            codigoIdentificacao
        )
    }
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            println("localizacao mudou")
            val latitude = location.latitude
            val longitude = location.longitude
            val lblLocalizacao: TextView = findViewById(R.id.lblLocalizacao)
            lblLocalizacao.text = ("" + latitude + ":" +longitude)
            //adicionarDados(latitude, longitude)
        }
    }
    fun adicionarDados(latitude:Double, logitude:Double){
        val dados = HashMap<String, Any>()
        println(latitude)
        dados["latitude"] = latitude.toString()
        dados["longitude"] = logitude.toString()
        println(dados)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("coordenadas/")
        myRef.setValue(dados)
    }

    override fun onRequestPermissionsResult(codigoIdentificacao: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(codigoIdentificacao, permissions, grantResults)
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



}