package com.example.projetohelio1bi

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnFinalizar: Button = findViewById(R.id.btn)
        var checbRefri: CheckBox = findViewById(R.id.cbRefri)
        var checbPiz: CheckBox = findViewById(R.id.cbPiz)
        var checbSob: CheckBox = findViewById(R.id.cbSobr)
        carregarProdutos(checbRefri.isChecked, checbPiz.isChecked, checbSob.isChecked)

        checbRefri.setOnCheckedChangeListener { buttonView, isChecked ->
            carregarProdutos(checbRefri.isChecked, checbPiz.isChecked, checbSob.isChecked)
        }
        checbPiz.setOnCheckedChangeListener { buttonView, isChecked ->
            carregarProdutos(checbRefri.isChecked, checbPiz.isChecked, checbSob.isChecked)
        }
        checbSob.setOnCheckedChangeListener { buttonView, isChecked ->
            carregarProdutos(checbRefri.isChecked, checbPiz.isChecked, checbSob.isChecked)
        }

        btnFinalizar.setOnClickListener {
            finalizarPedido()
        }

    }
    fun carregarProdutos(checbR: Boolean, checbP: Boolean, checbS: Boolean) {
        val linearProdutos: LinearLayout = findViewById(R.id.produtos)
        linearProdutos.removeAllViews()

        val queue = Volley.newRequestQueue(this)
        val url = "http://helioesperidiao.com/api.php"
        val requestBody = "id=1" + "&msg=test_msg"
        val stringReq : StringRequest =
            object : StringRequest(Method.POST, url,
                Response.Listener { response ->

                    var resposta = response.toString()
                    val array = JSONArray(resposta)
                    val tamanho =array.length()
                    for (i in 0 until tamanho ) {
                        val item: JSONObject = array.getJSONObject(i) // recupera o objeto na posição dentro do array
                        var idProduto = item.get("id").toString()
                        var type = item.get("type").toString();
                        var nome = item.get("nome").toString();
                        var desc = item.get("desc").toString();
                        var qtd = item.get("qtd").toString();
                        var preco = item.get("preco").toString();
                        var img = item.get("img").toString();

                        if(checbR || checbP || checbS){
                            if( (checbR && (type=="refrigerante")) || (checbP && (type=="pizza")) || (checbS && (type=="sobremesa")) )
                                criarProdutosDinamicos(idProduto, type, nome, desc, qtd, preco, img)
                        }else
                            criarProdutosDinamicos(idProduto, type, nome, desc, qtd, preco, img)
                        println(idProduto + " -teste " +type)

                    }
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

    fun criarProdutosDinamicos(idProduto: String, type: String, nome:String, desc:String, qtd:String, preco:String, img:String){
        val linearProdutos: LinearLayout = findViewById(R.id.produtos)
        linearProdutos.scrollY
        var bloco = LinearLayout(this)

        //bloco linear layout de produto
        bloco.orientation = LinearLayout.VERTICAL
        bloco.setBackgroundColor(Color.parseColor("#D6D6D6"))

        bloco.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        //criar imagem
        var imagev = ImageView(this)
        imagev.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        //carregar img
        DownloadImageFromInternet(imagev).execute(img)

        //texto do produto
        var novoTextView = TextView(this)

        novoTextView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        novoTextView.text = "${type} -> ${nome} R$ ${preco} \n "//${desc}

        //botao para adicionar produto em pedido
        val adicionar = Button(this)
        adicionar.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        adicionar.text = "Adicionar ao pedido"
        adicionar.setOnClickListener {
            adicionarPedido(idProduto, nome, preco)
        }

        bloco.addView(imagev)
        bloco.addView(novoTextView)
        bloco.addView(adicionar)

        linearProdutos.addView(bloco)
        var espaco = TextView(this)

        espaco.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        espaco.text = "   "
        espaco.height = 100
        linearProdutos.addView(espaco)

    }
    fun adicionarPedido(id:String, nome:String, preco:String){
        var linearPedido: LinearLayout = findViewById(R.id.pedidos)
        var total: TextView = findViewById(R.id.total)

        var bloco = LinearLayout(this)
        //bloco linear layout de produto
        bloco.orientation = LinearLayout.VERTICAL
        bloco.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        //texto do produto
        var novoTextView = TextView(this)

        novoTextView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        novoTextView.text = "Código: ${id} -> ${nome} R$ ${preco} \n "
        novoTextView.textSize = 25F

        bloco.addView(novoTextView)
        linearPedido.addView(bloco)

        total.text = (total.text.toString().toFloat() + preco.toFloat()).toString()

    }
    fun finalizarPedido(){
        var linearpedido: LinearLayout = findViewById(R.id.pedidos)
        var total: TextView = findViewById(R.id.total)
        linearpedido.removeAllViews()
        total.text = "0"
        //...falta coisa (enviar post)
    }
    @SuppressLint("StaticFieldLeak")
    @Suppress("DEPRECATION")
    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        init {
            Toast.makeText(applicationContext, "Carregando imagem", Toast.LENGTH_SHORT).show()
        }
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }


}