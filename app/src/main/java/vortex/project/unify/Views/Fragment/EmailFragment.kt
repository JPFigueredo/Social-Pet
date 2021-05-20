package vortex.project.unify.Views.Fragment

import android.os.Bundle
import android.util.Patterns
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_email.*
import kotlinx.android.synthetic.main.toolbar.*
import vortex.project.unify.R

class EmailFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setUpListeners()
    }

    private fun validateEmail(): Boolean {
        val emailInput = owner_email_input.text.toString()
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
                findNavController().navigate(R.id.phoneFragment, null)
            }else{
                Toast.makeText(context, "Email inválido!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setToolbar() {
        //val background = context?.let { AppCompatResources.getDrawable(it, R.color.white) }

        val value = TypedValue()
        activity?.theme!!.resolveAttribute(R.attr.colorOnPrimary, value, true)
        val background = context?.let { ContextCompat.getColor(it, value.resourceId) }

        activity?.toolbar_layout!!.visibility = View.VISIBLE
        activity?.drawer_button!!.visibility = View.GONE
        activity?.camera_button!!.visibility = View.GONE
        activity?.message_button!!.visibility = View.GONE
        activity?.toolbar_layout!!.background = background!!.toDrawable()

        val parameter = activity?.toolbar!!.layoutParams as ViewGroup.MarginLayoutParams
        parameter.marginStart = 0
        activity?.toolbar!!.layoutParams = parameter
    }
}
