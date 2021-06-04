package vortex.project.unify.Views.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import vortex.project.unify.R
import vortex.project.unify.Views.ViewModel.UserViewModel

class LoginFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
                act -> userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
        }
        fillUserData ()
        setUpListeners()
//        setWidgets()
    }

    private fun setUpListeners(){
        tv_not_member.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_regEmailFragment, null)
        }
        fab_login.setOnClickListener {
            doLogIn()
        }
        tv_forgot_password.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment, null)
        }
    }

    private fun setWidgets() {
        activity?.toolbar_layout!!.visibility = View.GONE
        activity?.bottom_nav_view!!.visibility = View.GONE
    }

    private fun fillUserData () {
        userViewModel.emailDB.observe(viewLifecycleOwner, Observer {
            if(it != null && it.isNotBlank()){
                emailLogin_input.setText(it.toString())
            }
        })
        userViewModel.passwordDB.observe(viewLifecycleOwner, Observer {
            if(it != null && it.isNotBlank()){
                passwordLogin_input.setText(it.toString())
            }
        })
    }

    fun doLogIn() {
        auth.signInWithEmailAndPassword(emailLogin_input.text.toString(), passwordLogin_input.text.toString())
            .addOnSuccessListener { taskSnapshot ->
                Toast.makeText(context, "Authentication Success", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_postFragment, null)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Authentication Failure", Toast.LENGTH_SHORT).show()
            }

    }

}