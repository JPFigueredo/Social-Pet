package vortex.project.unify.Views.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import vortex.project.unify.R
import vortex.project.unify.Views.ViewModel.UserViewModel

class LoginFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
                act -> userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
        }
        fillUserData ()
        setUpListeners()
        setWidgets()
    }

    private fun setUpListeners(){
        tv_not_member.setOnClickListener {
            findNavController().navigate(R.id.regEmail, null)
        }
        fab_login.setOnClickListener {
            findNavController().navigate(R.id.home_dest, null)
        }
        tv_forgot_password.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordFragment, null)
        }
    }

    private fun setWidgets() {
        activity?.toolbar_layout!!.visibility = View.GONE
        activity?.bottom_nav_post!!.visibility = View.GONE
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

}