package vortex.project.unify.Views.Fragment.Register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.android.synthetic.main.fragment_reg_password.*
import kotlinx.android.synthetic.main.fragment_reg_phone.*
import vortex.project.unify.R
import vortex.project.unify.Views.Encrypto
import vortex.project.unify.Views.ViewModel.UserViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegPasswordFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var auth: FirebaseAuth
    private val encrypto = Encrypto()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        auth = FirebaseAuth.getInstance()

        val view = inflater.inflate(R.layout.fragment_reg_password, container, false)
        return view
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
            if (checkPassword()) {
                saveViewModel()
                doRegister()
            } else {
                Toast.makeText(context, "Password Mismatch", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPassword(): Boolean {
        
        val passwordInput = regPassword_input.text.toString()
        val confirmInput = regConfirmPassword_input.text.toString()
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

    private fun doRegister() {
        auth.createUserWithEmailAndPassword(userViewModel.emailVM.value.toString(), userViewModel.passwordVM.value.toString())
            .addOnSuccessListener {
                Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_reg_password_to_login, null)
            }
            .addOnFailureListener {
                when (it) {
                    is FirebaseAuthWeakPasswordException -> Toast.makeText(context, "  Password length error", Toast.LENGTH_SHORT).show()
                    is FirebaseAuthUserCollisionException -> Toast.makeText(context, "E-mail already registered", Toast.LENGTH_SHORT).show()
                    is FirebaseNetworkException -> Toast.makeText(context, " Internet error", Toast.LENGTH_SHORT).show()
                    else ->  Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }

    }

    //Somente para testes
    private fun saveViewModel(){
        userViewModel.passwordVM.value = regPassword_input.text.toString()
    }
}
