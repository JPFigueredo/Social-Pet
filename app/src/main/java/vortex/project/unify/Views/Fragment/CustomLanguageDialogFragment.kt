package vortex.project.unify.Views.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_custom_language_dialog.*
import kotlinx.android.synthetic.main.fragment_custom_language_dialog.view.*
import vortex.project.unify.R
import vortex.project.unify.Views.ViewModel.PreferencesViewModel

class CustomLanguageDialogFragment : DialogFragment() {

    private lateinit var preferencesViewModel: PreferencesViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        sharedPreferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)

        var view: View = inflater.inflate(R.layout.fragment_custom_language_dialog, container, false)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            act -> preferencesViewModel = ViewModelProviders.of(act).get(PreferencesViewModel::class.java)
        }
        setUpListeners()
    }


    private fun setUpListeners(){
        view?.cancelButton?.setOnClickListener{
            dismiss()
        }
        view?.submitButton?.setOnClickListener{
            val selectedID = LanguageRG.checkedRadioButtonId
            val radio = view?.findViewById<RadioButton>(selectedID)

            var ratingResult = radio?.text.toString()

            if(ratingResult == "English"){
                val bundle = Bundle()
                bundle.putString("My Lang", "en")
                preferencesViewModel.language.value = "en"
                findNavController().navigate(R.id.action_customLanguageDialogFragment_to_nav_preferences, bundle)
            }else if(ratingResult == "Portuguese"){
                val bundle = Bundle()
                bundle.putString("My Lang", "pt")
                preferencesViewModel.language.value = "pt"
                findNavController().navigate(R.id.action_customLanguageDialogFragment_to_nav_preferences, bundle)
            }

        }
    }
}