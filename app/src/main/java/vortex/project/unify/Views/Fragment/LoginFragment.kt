package vortex.project.unify.Views.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import vortex.project.unify.R

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setToolbar()
    }

    private fun setUpListeners(){
        tv_not_member.setOnClickListener {
            findNavController().navigate(R.id.emailFragment, null)
        }
        fab_login.setOnClickListener {
            findNavController().navigate(R.id.postFragment, null)
        }
        tv_forgot_password.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordFragment, null)
        }
    }

    private fun setToolbar() {
        activity?.toolbar_layout!!.visibility = View.GONE
    }
}