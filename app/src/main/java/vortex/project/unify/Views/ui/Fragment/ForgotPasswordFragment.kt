package vortex.project.unify.Views.ui.Activity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import vortex.project.unify.R

class ForgotPasswordFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setToolbar()
        setupListeners()
    }

    private fun setupListeners() {
        btn_resetPassword.setOnClickListener {
            val email = emailForgotPassword_input.text.toString()
            if (email.isNotEmpty()) {
                resetPassword(email)
            } else {
                Toast.makeText(context, "Enter your email", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Email send", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun setToolbar() {
        activity?.toolbar_layout!!.visibility = View.VISIBLE
    }
}