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

    private fun setUpListeners(){
        btn_next_password.setOnClickListener {
            if(checkPassword()){
                findNavController().navigate(R.id.loginFragment, null)
            }else{
                Toast.makeText(context, "Senha inválida!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkPassword(): Boolean {
        val specialCharPattern: Pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
        val upperCasePattern: Pattern = Pattern.compile("[A-Z ]")
        val lowerCasePattern: Pattern = Pattern.compile("[a-z ]")
        val digitCasePattern: Pattern = Pattern.compile("[0-9 ]")
        var flag = true
        if (passwordCadastro_input != ConfirmPasswordCadastro_input) {
            flag = false
        }
        if (passwordCadastro_input.length() in 13 downTo 7) {
            flag = false
        }
        if (!specialCharPattern.matcher(passwordCadastro_input.toString()).find()) {
            flag = false
        }
        if (!upperCasePattern.matcher(passwordCadastro_input.toString()).find()) {
            flag = false
        }
        if (!lowerCasePattern.matcher(passwordCadastro_input.toString()).find()) {
            flag = false
        }
        if (!digitCasePattern.matcher(passwordCadastro_input.toString()).find()) {
            flag = false
        }
        return flag
    }
}