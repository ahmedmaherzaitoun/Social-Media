package com.example.socialmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider

import com.google.android.gms.tasks.OnFailureListener

import com.google.firebase.auth.AuthResult

import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task


class GithubActivity : AppCompatActivity() {
    private lateinit var githubEmail : EditText
    private lateinit var btnGithub : Button
    private lateinit var auth :FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github)
        githubEmail = findViewById(R.id.github_email)
        btnGithub = findViewById(R.id.btn_github_login)

        auth = FirebaseAuth.getInstance()

        val provider = OAuthProvider.newBuilder("github.com")
        provider.addCustomParameter("login", githubEmail.text.toString())

        val scopes: ArrayList<String?> = object : ArrayList<String?>() {
            init {
                add("user:email")
            }
        }
        provider.scopes = scopes
        btnGithub.setOnClickListener {
            intent.putExtra("githubUserEmail", githubEmail.text.toString())
            signInWithGithubProvider(provider = provider)
        }
    }
    private fun signInWithGithubProvider(provider: OAuthProvider.Builder) {

        // There's something already here! Finish the sign-in for your user.
        val pendingResultTask: Task<AuthResult>? = auth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener {
                    // User is signed in.
                    Toast.makeText(this, "User exist", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    // Handle failure.
                    Toast.makeText(this, "Error : $it", Toast.LENGTH_LONG).show()
                }
        } else {

            auth.startActivityForSignInWithProvider( /* activity= */this, provider.build())
                .addOnSuccessListener(
                    OnSuccessListener<AuthResult?> {
                        // User is signed in.
                        // retrieve the current user
                        val user = auth.currentUser!!
                        // navigate to HomePageActivity after successful login
                        val intent = Intent(this, InformationActivity::class.java)

                        // send github user name from MainActivity to HomePageActivity
                        intent.putExtra("githubUserName", user.uid)
                        intent.putExtra("githubUserEmail", githubEmail.text.toString())

                        this.startActivity(intent)
                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show()

                    })
                .addOnFailureListener(
                    OnFailureListener {
                        // Handle failure.
                        Log.d("github", "signInWithGithubProvider: $it " )
                        Toast.makeText(this, "Error : $it", Toast.LENGTH_LONG).show()
                    })
        }

    }
}