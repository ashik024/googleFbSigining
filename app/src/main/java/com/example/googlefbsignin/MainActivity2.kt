package com.example.googlefbsignin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class MainActivity2 : AppCompatActivity() {

    private var gso: GoogleSignInOptions? = null
    private var gsc: GoogleSignInClient? = null

    private var details: TextView? = null




    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var loginButtonGoogle = findViewById<ImageView>(R.id.google_btn)
        var loginButtonFb =findViewById<ImageView>(R.id.fb_btn)
        details =findViewById<TextView>(R.id.userDetails)
        var logout =findViewById<Button>(R.id.logout)


        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso!!)

        loginButtonGoogle.setOnClickListener {

            Log.d("TAG", "onCreate:1 ")
            signIn();
        }
        loginButtonFb.setOnClickListener {

            Log.d("TAG", "onCreate:2 ")
        }

        logout.setOnClickListener {

            signOut()
        }
    }

    fun signIn() {
        val signInIntent = gsc!!.signInIntent
        startActivityForResult(signInIntent, 1000)
    }
    fun signOut() {
        gsc!!.signOut().addOnCompleteListener {
            Toast.makeText(applicationContext, "signedOut", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
//                Toast.makeText(applicationContext, "working", Toast.LENGTH_SHORT).show()
                val acct = GoogleSignIn.getLastSignedInAccount(this)
                if (acct != null) {
                    val personName = acct.displayName
                    val personEmail = acct.email



                    details!!.setText("Name: "+personName+"\n"+
                            "personEmail: "+ personEmail+"\n"+
                            "idToken: "+ acct.idToken+"\n"+
                            "id: "+ acct.id+"\n"+
                            "account: "+ acct.account+"\n"+
                            "serverAuthCode: "+ acct.serverAuthCode+"\n"+
                            "isExpired: "+ acct.isExpired+"\n"
                    )

                }
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}