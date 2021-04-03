package vortex.project.unify.Views.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import vortex.project.unify.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun startRegister(view: View) {}
    fun logIN(view: View) {}
    fun forgotPass(view: View) {}
}