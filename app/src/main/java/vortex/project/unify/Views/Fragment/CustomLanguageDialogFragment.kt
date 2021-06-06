package vortex.project.unify.Views.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_custom_language_dialog.*
import kotlinx.android.synthetic.main.fragment_custom_language_dialog.view.*
import vortex.project.unify.R

class CustomLanguageDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_custom_language_dialog, container, false)

        setUpListeners()

        return view
    }

    private fun setUpListeners(){
        view?.cancelButton?.setOnClickListener{
            dismiss()
        }
        view?.submitButton?.setOnClickListener{
            val selectedID = LanguageRG.checkedRadioButtonId
            val radio = view?.findViewById<RadioButton>(selectedID)

            var ratingResult = radio?.text.toString()

            if(ratingResult == "@string/english"){
                val bundle = Bundle()
                bundle.putString("key", value)
                findNavController().navigate(R.id.action_customLanguageDialogFragment_to_nav_preferences, bundle)
            }else if(ratingResult == "@string/Portuguese"){
                val bundle = Bundle()
                bundle.putString("key", value)
                findNavController().navigate(R.id.action_customLanguageDialogFragment_to_nav_preferences, bundle)
            }

        }
    }
}