package com.example.myapp.pembayaranqris.util

import android.content.Context

class CameraPermissionManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("camera_permission", Context.MODE_PRIVATE)

    fun isCameraPermissionGranted(): Boolean {
        return sharedPreferences.getBoolean("camera_permission_granted", false)
    }

    fun setCameraPermissionGranted(granted: Boolean) {
        sharedPreferences.edit().putBoolean("camera_permission_granted", granted).apply()
    }

    fun clearCameraPermission() {
        sharedPreferences.edit().remove("camera_permission_granted").apply()
    }
}
