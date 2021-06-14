package vortex.project.unify.Views.ui.Activity.Fragment.Main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_preferences.*
import vortex.project.unify.R
import vortex.project.unify.Views.ViewModel.PreferencesViewModel

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

        setWidgets()
        setUpListeners()
        changeTheme()
    }

    private fun setUpListeners() {
        btn_changeApparence.setOnClickListener {
            preferencesViewModel.modeNight.value = if (preferencesViewModel.modeNight.value == getString(R.string.day_mode)) {
                getString(R.string.night_mode)
            } else {
                getString(R.string.day_mode)
            }
        }
    }

    private fun changeTheme() {
        preferencesViewModel.modeNight.observe(viewLifecycleOwner, Observer {
            if (it == getString(R.string.day_mode)) {
                tv_appearance.text = it
            } else {
                tv_appearance.text = it
            }
        })
    }

    private fun setWidgets() {
        activity?.bottom_nav_view!!.visibility = View.GONE
    }
}