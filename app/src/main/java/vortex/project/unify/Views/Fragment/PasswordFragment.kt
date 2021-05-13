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
                findNavController().navigate(R.id.loginFragment, null)
            } else {
                Toast.makeText(context, "Senha inválida!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun checkPassword(): Boolean {
        val passwordInput = passwordCadastro_input.toString()
        val passwordREGEX: Pattern = Pattern.compile("^" +
                "(?=.*[0-9])" +             //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +               //no white spaces
                ".{8,12}" +                 //min 8 characters, max 12
                "$")
        var flag = true
        if (passwordCadastro_input != ConfirmPasswordCadastro_input) {
            flag = false
        }
        if(passwordInput.isEmpty()){
            flag = false
        }
        if(!passwordREGEX.matcher(passwordInput).matches()){
            flag = false
        }
        return flag
    }
}