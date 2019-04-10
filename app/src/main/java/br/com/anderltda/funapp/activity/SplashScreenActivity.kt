package br.com.anderltda.funapp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.anderltda.funapp.R
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 12000 // 12 segundos

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        auth = FirebaseAuth.getInstance();

        if (auth.currentUser != null) {

            next()

        } else {

            startSplash()
        }


    }

    private fun startSplash() {

        val splash = object : Thread() {

            override fun run() {

                try {

                    Thread.sleep(SPLASH_DELAY)
                    next()

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }


            }

        }

        splash.start();
    }

    private fun next() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
    }

}
