package com.witsindia.firebaseremoteconfigdemo
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes


class MainActivity : AppCompatActivity() {
    private lateinit var textField:TextView
    private lateinit var mFirebaseRemoteConfig:FirebaseRemoteConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textField=findViewById(R.id.Text);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(10)
            .build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.hello_text_default);
        mFirebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("valuesfromfirebase", "Config params updated: ${task.result}")
                    Toast.makeText(
                        this@MainActivity, mFirebaseRemoteConfig.getString("Hello_World"),
                        Toast.LENGTH_SHORT
                    ).show()
                    textField.setText(mFirebaseRemoteConfig.getString("Hello_World"))
                } else {
                    Toast.makeText(
                        this@MainActivity, "Fetch failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }




        AppCenter.start(
            application, "{Your app secret here}",
            Analytics::class.java, Crashes::class.java
        )


    }
}