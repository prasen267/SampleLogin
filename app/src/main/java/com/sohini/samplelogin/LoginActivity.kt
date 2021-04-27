package com.sohini.samplelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


const val USER_EMAIL = "userEmail"
class LoginActivity : AppCompatActivity() {
    lateinit var loginbutton: MaterialButton
    lateinit var userEmail: TextInputEditText
    lateinit var userPassword: TextInputEditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        loginbutton=findViewById(R.id.btn_login)
        userEmail=findViewById(R.id.et_userEmail)
        userPassword=findViewById(R.id.et_userPassword)
        auth = FirebaseAuth.getInstance()
        loginbutton.setOnClickListener {
            val email = userEmail.text.toString()
            val password = userPassword.text.toString()
            when{
                email.isEmpty()->{
                  Toast.makeText(this,"Please enter a valid email",Toast.LENGTH_SHORT).show()
                }
                password.isEmpty()->{
                    Toast.makeText(this,"Please enter a password",Toast.LENGTH_SHORT).show()
                }
                else->{
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                        if(it.isSuccessful){
                           val intent = Intent(this,MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra(USER_EMAIL,it.result?.user?.email)
                            startActivity(intent)
                        }
                    }
                        .addOnFailureListener {
                            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                        }
                }
            }


        }
    }
}
