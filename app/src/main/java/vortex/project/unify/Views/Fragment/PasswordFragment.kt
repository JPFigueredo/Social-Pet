package vortex.project.unify.Views.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_password.*
import vortex.project.unify.R
import java.util.regex.Matcher
import java.util.regex.Pattern

class PasswordFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        btn_next_password.setOnClickListener {
            if (checkPassword()) {
                activity?.let{
                    getActivity()?.finish()
                }
            } else {
                Toast.makeText(context, "Password Mismatch", Toast.LENGTH_SHORT).show()
            }
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
