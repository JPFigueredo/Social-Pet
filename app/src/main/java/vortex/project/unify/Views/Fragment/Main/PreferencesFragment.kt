package vortex.project.unify.Views.Fragment.Main

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preferences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            act -> preferencesViewModel = ViewModelProviders.of(act).get(PreferencesViewModel::class.java)
        }

        setToolbar()
        setUpListeners()
        fillUserData()
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

    private fun setToolbar() {
//        val value = TypedValue()
//        activity?.theme!!.resolveAttribute(R.attr.colorOnPrimary, value, true)
//        val background = context?.let { ContextCompat.getColor(it, value.resourceId) }

        activity?.toolbar_layout!!.visibility = View.VISIBLE
        activity?.drawer_button!!.visibility = View.GONE
        activity?.camera_button!!.visibility = View.GONE
        activity?.message_button!!.visibility = View.GONE
        activity?.bottom_nav_post!!.visibility = View.GONE
//        activity?.toolbar_layout!!.background = background!!.toDrawable()

        val parameter = activity?.toolbar!!.layoutParams as ViewGroup.MarginLayoutParams
        parameter.marginStart = 0
        activity?.toolbar!!.layoutParams = parameter
    }
}