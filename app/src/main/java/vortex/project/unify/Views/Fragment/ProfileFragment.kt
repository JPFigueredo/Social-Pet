package vortex.project.unify.Views.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import vortex.project.unify.R

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)

        setWidgets()
    }

    private fun setWidgets() {
        activity?.toolbar_layout!!.visibility = View.GONE
        activity?.bottom_nav_post!!.visibility = View.GONE

    }
}