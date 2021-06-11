package vortex.project.unify.Views.Fragment.Main

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_preferences.*
import kotlinx.android.synthetic.main.toolbar.*
import vortex.project.unify.R
import vortex.project.unify.Views.Fragment.CustomLanguageDialogFragment
import vortex.project.unify.Views.ViewModel.PreferencesViewModel
import java.util.*

class PreferencesFragment : Fragment() {

    private lateinit var preferencesViewModel: PreferencesViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sharedPreferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val view = inflater.inflate(R.layout.fragment_preferences, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            act -> preferencesViewModel = ViewModelProviders.of(act).get(PreferencesViewModel::class.java)
        }
//        loadLocate()
//        setToolbar()
        setUpListeners()
        fillUserData()
    }

    private fun setUpListeners() {
//        btn_changeLanguage.setOnClickListener {
//            preferencesViewModel.language.value =
//                if (preferencesViewModel.language.value == getString(R.string.dialogPortuguese)) ({
//                findNavController().navigate(R.id.action_nav_preferences_to_customLanguageDialogFragment, null)
//            }).toString()
//            else ({
//                    if (preferencesViewModel.language.value == getString(R.string.dialogEnglish)) {
//                        findNavController().navigate(R.id.action_nav_preferences_to_customLanguageDialogFragment, null)
//                    }else{
//                        findNavController().navigate(R.id.action_nav_preferences_to_customLanguageDialogFragment, null)
//                    }
//            }).toString()
//        }

        btn_changeApparence.setOnClickListener {
            preferencesViewModel.modeNight.value = if (preferencesViewModel.modeNight.value == getString(R.string.day_mode)) {
                getString(R.string.night_mode)
            } else {
                getString(R.string.day_mode)
            }
        }
    }

    private fun fillUserData() {
        preferencesViewModel.modeNight.observe(viewLifecycleOwner, Observer {
            if (it == getString(R.string.day_mode)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                tv_appearance.text = it
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                tv_appearance.text = it
            }
        })
    }

//    private fun setToolbar() {
//
//        activity?.toolbar_layout!!.visibility = View.VISIBLE
//        activity?.drawer_button!!.visibility = View.GONE
////        activity?.camera_button!!.visibility = View.GONE
////        activity?.new_followers_button!!.visibility = View.GONE
//        activity?.bottom_nav_view!!.visibility = View.GONE
//
//        val parameter = activity?.toolbar!!.layoutParams as ViewGroup.MarginLayoutParams
//        parameter.marginStart = 0
//        activity?.toolbar!!.layoutParams = parameter
//    }
}