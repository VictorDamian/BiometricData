package com.example.biometricdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var biometricPrompt: BiometricPrompt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        biometricPrompt = createBiometricPrompt()

        btnSing.setOnClickListener{
            val promptInfo = createPromptInfo()
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun createBiometricPrompt(): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(this)

        val callback = object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                Toast.makeText(
                        this@MainActivity,
                        "onAuthenticationError\nerrorCOde: $errorCode\nerrString: $errString",
                        Toast.LENGTH_LONG
                ).show()
            }
            override fun onAuthenticationFailed() {
                Toast.makeText(this@MainActivity, "onAuthenticationFailed",
                        Toast.LENGTH_LONG).show()
            }
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                val intent = Intent(this@MainActivity, Main2Activity::class.java)
                startActivity(intent)
            }
        }
        return BiometricPrompt(this, executor, callback)
    }

    private fun createPromptInfo(): BiometricPrompt.PromptInfo{
        return BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric data")
                .setSubtitle("sing up")
                .setDescription("touch the sensor")
                .setDeviceCredentialAllowed(true)
                .build()
    }
}
