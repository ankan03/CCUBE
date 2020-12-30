package com.studgenie.app.ui.onboarding.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.studgenie.app.R
import com.studgenie.app.ui.main.activity.HomeActivity
import androidx.lifecycle.Observer
import com.studgenie.app.data.local.userStatusDatabase.UserStatusViewModel
import com.studgenie.app.util.SPLASH_DISPLAY_TIME

//import com.studgenie.app.util.PermissionsHandler
//import com.studgenie.app.util.PERMISSION_REQUEST_CODE
//import com.studgenie.app.util.SPLASH_DISPLAY_TIME


@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {
//    private val SPLASH_DISPLAY_TIME = 500
    private lateinit var statusViewModel: UserStatusViewModel
    var isStatusEmpty = 1
    val MANIFEST_PERMISSION_ALL: Array<String> = arrayOf("android.permission.READ_PHONE_NUMBERS")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Log.d("Splash", "Inside Splash Activity")

        //Checks & Request for All Permissions
//        PermissionsHandler(this, MANIFEST_PERMISSION_ALL).checkForPermissions()

        statusViewModel = ViewModelProvider(this).get(UserStatusViewModel::class.java)

        statusViewModel.readAllData?.observe(this, Observer { status ->
            if (status.isEmpty()) {
                isStatusEmpty = 1
                Log.d("Splash1", "List is empty")
            } else {
                isStatusEmpty = 0
                Log.d("Splash1", status[0].id.toString() + status[0].status)
            }
        })
        /* Create an Intent that will start the Menu-Activity. */
        Handler().postDelayed(Runnable {
            if (isStatusEmpty == 1) {
                val signupActivity = Intent(this, SignUpActivity::class.java)
                this.startActivity(signupActivity)
                this.finish()
            } else {
                val homeActivity = Intent(this, HomeActivity::class.java)
                this.startActivity(homeActivity)
                this.finish()
            }
        }, SPLASH_DISPLAY_TIME.toLong())
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Log.d("LOG_DEBUG_PERMISSION", "Permission Granted")
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
//            } else {
//                Log.d("LOG_DEBUG_PERMISSION", "Permission Denied")
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
//            }
//        }
//    }

}