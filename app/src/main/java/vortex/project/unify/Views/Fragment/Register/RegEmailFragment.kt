package vortex.project.unify.Views.Fragment.Register

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_reg_email.*
import kotlinx.android.synthetic.main.toolbar.*
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
        setToolbar()
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

//            findNavController().navigate(R.id.action_reg_email_to_reg_phone, null)

            if(validateEmail()) {
                userViewModel.emailDB.setValue(regEmail_input.text.toString())
                findNavController().navigate(R.id.action_reg_email_to_reg_phone, null)
            }else{
                Toast.makeText(context, "Email inválido!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setToolbar() {
        //val background = context?.let { AppCompatResources.getDrawable(it, R.color.white) }

//        val value = TypedValue()
//        activity?.theme!!.resolveAttribute(R.attr.colorOnPrimarySurface, value, true)
//        val background = context?.let { ContextCompat.getColor(it, value.resourceId) }

        activity?.toolbar_layout!!.visibility = View.VISIBLE
        activity?.drawer_button!!.visibility = View.GONE
//        activity?.camera_button!!.visibility = View.GONE
//        activity?.new_fallowers_button!!.visibility = View.GONE
//        activity?.toolbar_layout!!.background = background!!.toDrawable()

        val parameter = activity?.toolbar!!.layoutParams as ViewGroup.MarginLayoutParams
        parameter.marginStart = 0
        activity?.toolbar!!.layoutParams = parameter
    }
}
