package vortex.project.unify.Views.Fragment.Register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_reg_password.*
import vortex.project.unify.R
import vortex.project.unify.Views.ViewModel.UserViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegPasswordFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reg_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
                act -> userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
        }
        setUpListeners()
    }

    private fun setUpListeners() {
        btn_next_password.setOnClickListener {
            findNavController().navigate(R.id.action_reg_password_to_login, null)

//            if (checkPassword()) {
//                findNavController().navigate(R.id.action_reg_password_to_login, null)
//            } else {
//                Toast.makeText(context, "Password Mismatch", Toast.LENGTH_SHORT).show()
//            }
        }
    }


    private fun checkPassword(): Boolean {
        
        val passwordInput = passwordCadastro_input.text.toString()
        val confirmInput = ConfirmPasswordCadastro_input.text.toString()
        val passwordREGEX: Pattern = Pattern.compile("""^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%¨&*()_+=-])(?=\S+$).{8,12}$""")
        val match : Matcher = passwordREGEX.matcher(passwordInput)
        var flag = true

        if (passwordInput != confirmInput) {
            flag = false
        }
        if(passwordInput.isEmpty()){
            flag = false
        }
        if(!match.matches()){
            flag = false
        }
        return flag
    }
}
