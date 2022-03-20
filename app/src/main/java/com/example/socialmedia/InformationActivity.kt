package com.example.socialmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
//import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseAuth
//import com.facebook.GraphResponse

import org.json.JSONObject

//import com.facebook.GraphRequest




class InformationActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth

    private lateinit var userName :TextView
    private lateinit var userEmail :TextView
    private lateinit var btnSignOut :Button
    private lateinit var userImage :ImageView
    //private  var accessToken :AccessToken? = null
    private lateinit var strName :String
    private lateinit var strUrl :String

    private  var strEmail: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

       userName = findViewById(R.id.tv_name)
       userEmail = findViewById(R.id.tv_email)
       btnSignOut = findViewById(R.id.btn_sign_out)
       userImage = findViewById(R.id.imageView)


        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if( user != null) {
            strName = user?.displayName.toString()
            strEmail = user?.email.toString()
            strUrl = user?.photoUrl.toString()

            if( strEmail =="null") {
                strEmail = intent.getStringExtra("githubUserEmail")!!
            }
            strName = intent.getStringExtra("githubUserName")!!

        }







        userName.text = strName
        userEmail.text = strEmail


        Glide.with(this).load(strUrl).into(userImage)


        btnSignOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
       }



    }
}