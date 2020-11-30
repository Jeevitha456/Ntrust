package com.example.ntrust

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Intents.Insert.EMAIL
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.facebook.login.LoginManager



const val RC_SIGN_IN=123
const val FB_SIGN_IN=125
class MainActivity : AppCompatActivity() {
    private lateinit var edtmail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var hideshow: ImageView
    private lateinit var alertText: TextView
    private lateinit var btnLogin: Button
    private var ishide: Boolean = false
    private lateinit var btnGoogle: ImageView
    private lateinit var btnFacebook: ImageView
    private lateinit var callbackManager: CallbackManager
    private lateinit var loginButton:LoginButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtmail = findViewById(R.id.edt_gmail)
        edtpassword = findViewById(R.id.edt_password)
        hideshow = findViewById(R.id.img_eye)
        alertText = findViewById(R.id.alertTitle)
        btnLogin = findViewById(R.id.btn_login)
        btnGoogle=findViewById(R.id.img_google)
        btnFacebook=findViewById(R.id.img_fb)
        seteventhandler()
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
       val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        btnGoogle.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            edtmail.setText(acct.displayName)
        }

        //facebook login
        callbackManager = CallbackManager.Factory.create()
        loginButton=findViewById(R.id.login_button)
        loginButton.setPermissions(listOf("public_profile","email"))
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(result: LoginResult?) {
                Log.d("MainActivity", "Facebook token: " + result!!.accessToken.token)
                getUserProfile(result?.accessToken, result?.accessToken?.userId)

                startActivity(
                    Intent(
                        applicationContext,
                        HomeActivity::class.java
                    )
                )// App
            }

            override fun onCancel() {
                TODO("Not yet implemented")
            }

            override fun onError(error: FacebookException?) {
                TODO("Not yet implemented")
            }

        })
    }
    fun onClick(view:View)
    {
        if (view == btnFacebook) {
            loginButton.performClick();
        }
    }
    @SuppressLint("LongLogTag")
    fun getUserProfile(token: AccessToken?, userId: String?) {

        val parameters = Bundle()
        parameters.putString(
            "fields",
            "id, first_name, middle_name, last_name, name, picture, email"
        )
        GraphRequest(token,
            "/$userId/",
            parameters,
            HttpMethod.GET,
            GraphRequest.Callback { response ->
                val jsonObject = response.jsonObject

                // Facebook Access Token
                // You can see Access Token only in Debug mode.
                // You can't see it in Logcat using Log.d, Facebook did that to avoid leaking user's access token.
                if (BuildConfig.DEBUG) {
                    FacebookSdk.setIsDebugEnabled(true)
                    FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS)
                }

                // Facebook Id
                if (jsonObject.has("id")) {
                    val facebookId = jsonObject.getString("id")
                    Log.i("Facebook Id: ", facebookId.toString())
                } else {
                    Log.i("Facebook Id: ", "Not exists")
                }


                // Facebook First Name
                if (jsonObject.has("first_name")) {
                    val facebookFirstName = jsonObject.getString("first_name")
                    Log.i("Facebook First Name: ", facebookFirstName)
                } else {
                    Log.i("Facebook First Name: ", "Not exists")
                }


                // Facebook Middle Name
                if (jsonObject.has("middle_name")) {
                    val facebookMiddleName = jsonObject.getString("middle_name")
                    Log.i("Facebook Middle Name: ", facebookMiddleName)
                } else {
                    Log.i("Facebook Middle Name: ", "Not exists")
                }


                // Facebook Last Name
                if (jsonObject.has("last_name")) {
                    val facebookLastName = jsonObject.getString("last_name")
                    Log.i("Facebook Last Name: ", facebookLastName)
                } else {
                    Log.i("Facebook Last Name: ", "Not exists")
                }


                // Facebook Name
                if (jsonObject.has("name")) {
                    val facebookName = jsonObject.getString("name")
                    Log.i("Facebook Name: ", facebookName)
                } else {
                    Log.i("Facebook Name: ", "Not exists")
                }


                // Facebook Profile Pic URL
                if (jsonObject.has("picture")) {
                    val facebookPictureObject = jsonObject.getJSONObject("picture")
                    if (facebookPictureObject.has("data")) {
                        val facebookDataObject = facebookPictureObject.getJSONObject("data")
                        if (facebookDataObject.has("url")) {
                            val facebookProfilePicURL = facebookDataObject.getString("url")
                            Log.i("Facebook Profile Pic URL: ", facebookProfilePicURL)
                        }
                    }
                } else {
                    Log.i("Facebook Profile Pic URL: ", "Not exists")
                }

                // Facebook Email
                if (jsonObject.has("email")) {
                    val facebookEmail = jsonObject.getString("email")
                    Log.i("Facebook Email: ", facebookEmail)
                } else {
                    Log.i("Facebook Email: ", "Not exists")
                }
            }).executeAsync()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FB_SIGN_IN) {
            callbackManager.onActivityResult(requestCode, resultCode, data)


        }
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            if(completedTask.isSuccessful){
                val account =
                    completedTask.getResult(ApiException::class.java)
                edtmail.setText(account?.displayName)
            }
            else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Failed")
                //set message for alert dialog
                builder.setMessage("Could'nt login using credentials")
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setPositiveButton("Yes"){dialogInterface, which ->
                    Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
                }
                builder.setNeutralButton("Cancel"){dialogInterface , which ->
                    Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
                }
                builder.setNegativeButton("No"){dialogInterface, which ->
                    Toast.makeText(applicationContext,"clicked No",Toast.LENGTH_LONG).show()
                }
                val alertDialog: AlertDialog = builder.create()

                alertDialog.setCancelable(false)
                alertDialog.show()
            }


        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
             Log.w("Failed", "signInResult:failed code=" + e.statusCode)
            //updateUI(null)
        }
    }

    private fun seteventhandler() {
        btnLogin.setOnClickListener {
            if (isValid()) {
                showDialog()
            }
        }
        hideshow.setOnClickListener {
            if (!ishide) {
                edtpassword.transformationMethod = PasswordTransformationMethod()
                ishide = true
            } else {
                edtpassword.transformationMethod = null
                ishide = false
            }

        }
        btnFacebook.setOnClickListener{

        }
    }

    @SuppressLint("SetTextI18n")
    private fun isValid(): Boolean {
        val email: String = edtmail.text.toString().trim()
        val password: String = edtpassword.text.toString().trim()
        if (email.isEmpty() && edtpassword.text.isNullOrEmpty()) {
            alertText.setText(R.string.AlertEmailPassword)
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            return false
        }
        if (email.isEmpty()) {
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            alertText.setText(R.string.AlertEmail)
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            alertText.setText(R.string.AlertEmailInvalid)
            return false
        }
        if (password.isEmpty()) {
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            alertText.setText(R.string.AlertPassword)
            return false
        }
        if (password.length < 5) {
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            alertText.setText(R.string.AlertPasswordInvalid)
            return false
        }
        edtmail.setBackgroundResource(R.drawable.customedittext)
        edtpassword.setBackgroundResource(R.drawable.customedittext)
        alertText.text = ""
        return true
    }

    private fun showDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.verify_email_dialogbox, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        val mAlertDialog = mBuilder.show()
        val resendBtn = mDialogView.findViewById(R.id.btn_resend) as Button

        resendBtn.setOnClickListener {
            mAlertDialog.dismiss()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }
}



