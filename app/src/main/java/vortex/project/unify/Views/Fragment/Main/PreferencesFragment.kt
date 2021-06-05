package vortex.project.unify.Views.Fragment.Main

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_preferences.*
import kotlinx.android.synthetic.main.toolbar.*
import vortex.project.unify.R
import vortex.project.unify.Views.ViewModel.PreferencesViewModel
import java.util.*

class PreferencesFragment : Fragment() {

    private lateinit var preferencesViewModel: PreferencesViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sharedPreferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val view = inflater.inflate(R.layout.fragment_reg_password, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            act -> preferencesViewModel = ViewModelProviders.of(act).get(PreferencesViewModel::class.java)
        }
        loadLocate()
        setToolbar()
        setUpListeners()
        fillUserData()
    }

    private fun setUpListeners() {
        btn_changeLanguage.setOnClickListener {
//            alertPop()
//            changeLanguage()
        }

        btn_changeApparence.setOnClickListener {
            preferencesViewModel.modeNight.value = if (preferencesViewModel.modeNight.value == getString(R.string.day_mode)) {
                getString(R.string.night_mode)
            } else {
                getString(R.string.day_mode)
            }
        }
    }
//    private fun changeLanguage() {
//        val listItems = arrayOf("English", "Português")
//
//        val mBuilder = AlertDialog.Builder(requireContext())
//        mBuilder.setTitle("Choose Language:")
//        mBuilder.setSingleChoiceItems(listItems, -1){ _, which ->
//            if(which == 0){
//                setLocate("en")
//                recreate()
//            }
//            else if(which==1){
//                setLocate("pt")
//                recreate()
//            }
//            //dialog.dismiss()
//        }
//    }

    private fun setLocate(Lang: String){
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context?.resources?.updateConfiguration(config, context?.resources!!.displayMetrics)

        val editor = sharedPreferences.edit()
        editor.putString("My Lang", Lang)
        editor.apply()
    }

    private fun loadLocate(){
        val language = sharedPreferences.getString("My Lang","")
        setLocate(language.toString())
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

//    private fun alertPop(view: View){
////        val dialogBuilder = AlertDialog.Builder(requireActivity())
////        dialogBuilder.setMessage("Choose Language")
////            // if the dialog is cancelable
////            .setCancelable(false)
////        dialogBuilder.setNegativeButton("English") { dialog, which ->
////
////        }
////        dialogBuilder.setPositiveButton("Portugues", this) { dialog, which ->
////
////        }
////            .show()
////            .setPositiveButton("Ok", DialogInterface.OnClickListener {
////                    dialog, id ->
////                dialog.dismiss()
////
////            })
//
//        val alert = dialogBuilder.create()
//        alert.setTitle("Test")
//        alert.show()
//    }


    private fun setToolbar() {

        activity?.toolbar_layout!!.visibility = View.VISIBLE
        activity?.drawer_button!!.visibility = View.GONE
//        activity?.camera_button!!.visibility = View.GONE
//        activity?.new_followers_button!!.visibility = View.GONE
        activity?.bottom_nav_view!!.visibility = View.GONE

        val parameter = activity?.toolbar!!.layoutParams as ViewGroup.MarginLayoutParams
        parameter.marginStart = 0
        activity?.toolbar!!.layoutParams = parameter
    }
}