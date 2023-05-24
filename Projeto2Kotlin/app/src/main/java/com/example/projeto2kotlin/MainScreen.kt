package com.example.projeto2kotlin

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainScreen: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val storage = Firebase.storage("gs://projeto-2-kotlin.appspot.com")
    private lateinit var bitmap: Bitmap
    private lateinit var uri: Uri
    private var postagens = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainscr)
        auth = FirebaseAuth.getInstance()
        init()
    }

    fun init(){
        val btnPick = findViewById<Button>(R.id.btnPhotoPicker)
        val btnPostar = findViewById<Button>(R.id.btnPostar)
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {uri ->
            if (uri!=null){
                Log.d("PhotoPicker", "Selected URI: $uri")
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                this.uri = uri
            }else{
                Log.d("PhotoPicker", "No media selected")
            }
        }
        btnPick.setOnClickListener{
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
        btnPostar.setOnClickListener{
            postar()
        }
        val btnRefresh = findViewById<Button>(R.id.btnReload)
        btnRefresh.setOnClickListener{
            getPosts()
        }
        getPosts()
    }

    fun getRandomString(): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"
        return (1..10)
            .map { charset.random() }
            .joinToString("")
    }

    fun criarPost(view: View){
        val linearLPost: LinearLayout = findViewById(R.id.linearLMainScrPost)
        linearLPost.isVisible = !linearLPost.isVisible
    }

    fun postar(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("/posts/")
        val txtPost: EditText = findViewById(R.id.txtPost)
        val imgPost: Bitmap = bitmap
        var nomeImg = uploadImage()
        val user = auth.currentUser
        val novoPost = mapOf(
            "legenda" to txtPost.text.toString(),
            "localUri" to imgPost.toString(),
            "remoteUri" to "imagens/$nomeImg",
            "id" to user!!.uid
        )
        myRef.push().setValue(novoPost).addOnCompleteListener{task ->
            if(task.isSuccessful) {
                Toast.makeText(baseContext, "Imagem postada!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(baseContext, "Nome inválido", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun  getPosts(){
        val linearLPosts = findViewById<LinearLayout>(R.id.linearLPosts)
        linearLPosts.removeAllViews()
        val database= FirebaseDatabase.getInstance()
        val myRef = database.getReference("/posts/")
        myRef.get().addOnCompleteListener{task->
            if(task.isSuccessful){
                val posts = task.result
                if(posts.exists()){
                    for(post in posts.children){

                        val legenda = post.child("legenda").value as String
                        val txtLegenda = TextView(this)
                        txtLegenda.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        txtLegenda.text = legenda
                        val path = post.child("remoteUri").value as String
                        postagens.add(path)
                        val idUser = post.child("id").value as String
                        val postImg = ImageView(this)
                        postImg.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        var storageRef: StorageReference  = storage.getReference("$path.jpg")
                        var getDownloadUriTask: Task<Uri> = storageRef.downloadUrl
                        getDownloadUriTask.addOnCompleteListener{task->
                            if(task.isSuccessful){
                                var downloadUri: Uri? = task.getResult()
                                Glide.with(this).load(downloadUri).into(postImg)
                            }
                        }
                        getNomeWithId(idUser, linearLPosts)
                        linearLPosts.addView(postImg)
                        linearLPosts.addView(txtLegenda)
                        calcularLikes(path, linearLPosts)
                        addLikeComment(path, linearLPosts)
                        getComments(path, linearLPosts)
                        divisaPosts(linearLPosts)
                    }
                }
            }
        }
    }

    private fun getNomeWithId(idUser: String, linearL: LinearLayout) {
        val database = FirebaseDatabase.getInstance()
        val lblUser = TextView(this)
        lblUser.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val myRef = database.getReference("/cadastros/")
        val query = myRef.orderByChild("id").equalTo(idUser)
        query.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                for (user in snapshot.children){
                    var nome = user.child("nome").value as String
                    lblUser.text = "Usuário: $nome"
                }
            }
            override fun onCancelled(databaseError: DatabaseError){
            }
        })
        linearL.addView(lblUser)
    }

    private fun divisaPosts(linearL: LinearLayout) {
        val divisa = TextView(this)
        divisa.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        divisa.text="------------------------------------------------------------------------------------"
        divisa.gravity=17
        linearL.addView(divisa)
    }

    private fun calcularLikes(path: String, linearL: LinearLayout) {
        var likes = 0
        val lblLikes = TextView(this)
        lblLikes.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val database= FirebaseDatabase.getInstance()
        val myRef = database.getReference("/likes/")


        val query = myRef.orderByChild("path").equalTo(path)
        query.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(like in snapshot.children){
                        likes+=1
                    }
                    var texto = "Likes: $likes"
                    lblLikes.text = texto
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                var texto = "Likes: 0"
                lblLikes.text = texto
            }
        })
        if (lblLikes.text == ""){
            lblLikes.text = "Likes: 0"
        }
        linearL.addView(lblLikes)


    }

    fun addLikeComment(path: String, linearL: LinearLayout) {
        val database = FirebaseDatabase.getInstance()
        val myRefLikes = database.getReference("/likes/")
        val myRefComments = database.getReference("/comments/")
        val btnLike = Button(this)
        btnLike.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        btnLike.text = "Like"
        val btnComment = Button(this)
        btnComment.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        btnComment.text="Comentar"
        val txtComment = EditText(this)
        txtComment.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        txtComment.hint = "Comentário"
        btnComment.setOnClickListener{
            val novoComment = mapOf(
                "path" to path,
                "idUsuario" to auth.currentUser!!.uid,
                "comment" to txtComment.text.toString()
            )
            myRefComments.push().setValue(novoComment)
        }
        btnLike.setOnClickListener {
            val novoLike = mapOf(
                "path" to path,
                "idUsuario" to auth.currentUser!!.uid,
                "like" to true
            )
            myRefLikes.push().setValue(novoLike)
        }
        linearL.addView(btnLike)
        linearL.addView(btnComment)
        linearL.addView(txtComment)

    }

    fun getComments(path: String, linearL: LinearLayout){
        val database= FirebaseDatabase.getInstance()
        val lblComment = TextView(this)
        val myRef = database.getReference("/comments/")
        val query = myRef.orderByChild("path").equalTo(path)
        query.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(comment in snapshot.children){
                        val comentario = comment.child("comment").value as String
                        val id = comment.child("idUsuario").value as String
                        var texto = "User: $id, comentário: $comentario"
                        lblComment.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        lblComment.text = texto
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        if (lblComment.text.toString() != "Vazio"){
            linearL.addView(lblComment)
        }
    }

    fun uploadImage(): String{
        val storage = Firebase.storage("gs://projeto-2-kotlin.appspot.com")
        var nome = getRandomString()
        val myRef = storage.reference.child("imagens/$nome.jpg")
        val uploadTask = myRef.putFile(uri)
        uploadTask.addOnSuccessListener{
            Toast.makeText(baseContext, "Upload sucedido", Toast.LENGTH_SHORT).show()
        }
        return nome
    }

    fun acessarConta(view: View){
        val intent = Intent(this,TelaPerfil::class.java)
        startActivity(intent)
    }

}