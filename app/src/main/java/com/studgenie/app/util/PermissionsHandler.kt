package com.studgenie.app.util

import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.studgenie.app.util.PERMISSION_REQUEST_CODE

class PermissionsHandler(private val activity: Activity, private val permissions: Array<String>) {


    fun checkForPermissions() {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("LOG_DEBUG_PERMISSION", "$permission Granted")
            } else {
                Log.d("LOG_DEBUG_PERMISSION", "$permission Rejected")
                requestPermission(permission)
            }
        }
    }

    private fun requestPermission(permission: String) {
        Log.d("LOG_DEBUG_PERMISSION", "$permission Requesting")
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            AlertDialog.Builder(activity)
                .setTitle("Permission Required")
                .setMessage("App needs permission for marking distance.")
                .setPositiveButton("Grant") { _, _ ->
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(permission),
                        PERMISSION_REQUEST_CODE
                    )
                }
                .setNegativeButton("Revoke") { dialog, _ ->
                    dialog.dismiss()
                }
                .create().show()
        } else {
            Log.d("LOG_DEBUG_PERMISSION", "$permission Asking now...")
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission),
                PERMISSION_REQUEST_CODE
            )
        }
    }
}