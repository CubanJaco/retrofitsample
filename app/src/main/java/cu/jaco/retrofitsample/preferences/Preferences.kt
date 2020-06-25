package cu.jaco.retrofitsample.preferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import cu.jaco.retrofitsample.Application
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preferences @Inject constructor(app: Application) {

    companion object {
        private const val SHARED_PREF_NAME = "sitras_shared_preferences"
    }

    private val sharedPreferences by lazy(LazyThreadSafetyMode.NONE) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val masterKeyAlias = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            } else {
                "_androidx_security_master_key_"
            }

            EncryptedSharedPreferences.create(
                SHARED_PREF_NAME,
                masterKeyAlias,
                app,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            app.getSharedPreferences(
                SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        }
    }

    var token: String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = sharedPreferences.getString("token",
            "56158b6d-4833-3c2f") ?: ""
        // custom setter to save a preference back to preferences file
        set(value) = sharedPreferences.put {
            putString("token", value)
        }

}

private inline fun SharedPreferences.put(body: SharedPreferences.Editor.() -> Unit) {
    val editor = this.edit()
    editor.body()
    editor.apply()
    editor.commit()
}