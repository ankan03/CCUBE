package com.studgenie.app.ui.onboarding.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.msg91.sendotpandroid.library.internal.SendOTP
import com.studgenie.app.R
import com.studgenie.app.data.local.tokenDatabase.AuthTokenViewModel
import com.studgenie.app.ui.onboarding.fragment.SignUp1Fragment
import com.studgenie.app.ui.onboarding.fragment.SignUp3Fragment
import androidx.lifecycle.Observer
import com.studgenie.app.util.PermissionsHandler
import com.studgenie.app.util.PERMISSION_REQUEST_CODE


class SignUpActivity : AppCompatActivity() {
    private lateinit var authTokenViewModel: AuthTokenViewModel

    val MANIFEST_PERMISSION_ALL: Array<String> = arrayOf(
        "android.permission.READ_PHONE_NUMBERS",
        "android.permission.READ_PHONE_NUMBERS",
        "android.permission.WAKE_LOCK",
        "android.permission.ACCESS_WIFI_STATE",
        "android.permission.RECEIVE_SMS",
        "android.permission.READ_SMS",
        "android.permission.ACCESS_NETWORK_STATE"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Checks & Request for All Permissions
        PermissionsHandler(this, MANIFEST_PERMISSION_ALL).checkForPermissions()

        SendOTP.initializeApp(application, "343141A0eUofjHNg5f73eff8P1")

        authTokenViewModel = ViewModelProvider(this).get(AuthTokenViewModel::class.java)

        authTokenViewModel.readAllData?.observe(this, Observer { auth ->
            if (auth.isEmpty()) {
                Log.d("SignUpActivity", "List is empty")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.signup_fragment_container, SignUp1Fragment())
                    .commit()
            } else {
                Log.d("SignUpActivity", auth[0].id.toString() + auth[0].authToken)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.signup_fragment_container, SignUp3Fragment())
                    .commit()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("LOG_DEBUG_PERMISSION", "Permission Granted")
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                Log.d("LOG_DEBUG_PERMISSION", "Permission Denied")
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}