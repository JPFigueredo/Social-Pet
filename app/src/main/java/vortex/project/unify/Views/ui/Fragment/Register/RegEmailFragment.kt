package vortex.project.unify.Views.ui.Activity.Fragment.Register

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_reg_email.*
import vortex.project.unify.R
import vortex.project.unify.Views.ViewModel.UserViewModel

class RegEmailFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reg_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
                act -> userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
        }
        setUpListeners()

    }

    private fun validateEmail(): Boolean {
        val emailInput = regEmail_input.text.toString()
        var flag = true
        if (emailInput.isEmpty()) {
            flag = false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            flag = false
        }
        return flag
    }

    private fun setUpListeners(){
        btn_next_email.setOnClickListener {
            if(validateEmail()) {
                saveViewModel()
                findNavController().navigate(R.id.action_reg_email_to_reg_phone, null)
            }else{
                Toast.makeText(context, "Email inválido!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun saveViewModel(){
        userViewModel.emailVM.value = regEmail_input.text.toString()
    }
}
