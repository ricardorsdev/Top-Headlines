package com.ricardosousa.topheadlines.utils

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ricardosousa.topheadlines.R

object BiometricsUtils {
    fun isBiometricAvailable(context: Context): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun showBiometricPrompt(fragment: Fragment, onSuccess: () -> Unit, onError: () -> Unit) {
        val promptInfo = createPromptInfo(fragment)
        val biometricPrompt = createBiometricPrompt(fragment, onSuccess, onError)
        biometricPrompt.authenticate(promptInfo)
    }

    private fun createBiometricPrompt(
        fragment: Fragment, onSuccess: () -> Unit, onError: () -> Unit
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(fragment.requireContext())

        val callback = object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errCode, errString)
                onError.invoke()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onError.invoke()

            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess.invoke()
            }
        }
        return BiometricPrompt(fragment, executor, callback)
    }

    private fun createPromptInfo(fragment: Fragment): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(fragment.getString(R.string.biometric_prompt_title))
            setDescription(fragment.getString(R.string.biometric_prompt_description))
            setNegativeButtonText(fragment.getString(R.string.cancel_label))
            setConfirmationRequired(false)
        }.build()
}