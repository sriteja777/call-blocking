package com.browserstack.crkotlin

import android.app.role.RoleManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!isRedirection())
            roleAcquire(RoleManager.ROLE_CALL_REDIRECTION)
    }

    private fun isRedirection(): Boolean {
        return isRoleHeldByApp(RoleManager.ROLE_CALL_REDIRECTION)
    }

    private fun isRoleHeldByApp(roleName: String): Boolean {
        val roleManager: RoleManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            roleManager = getSystemService(RoleManager::class.java)
            return roleManager.isRoleHeld(roleName)
        }
        return false
    }

    private fun roleAcquire(roleName: String) {
        val roleManager: RoleManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (roleAvailable(roleName)) {
                roleManager = getSystemService(RoleManager::class.java)
                val intent = roleManager.createRequestRoleIntent(roleName)
                startActivityForResult(intent, ROLE_ACQUIRE_REQUEST_CODE)
            } else {
                Toast.makeText(this, "Redirection call with role in not available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun roleAvailable(roleName: String): Boolean {
        val roleManager: RoleManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            roleManager = getSystemService(RoleManager::class.java)
            return roleManager.isRoleAvailable(roleName)
        }
        return false
    }

    companion object {
        private const val ROLE_ACQUIRE_REQUEST_CODE = 4378
    }

}