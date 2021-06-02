package vortex.project.unify.Views.Fragment.Main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import vortex.project.unify.R

class SearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
    }

    private fun setToolbar() {

        activity?.toolbar_layout!!.visibility = View.VISIBLE
        activity?.drawer_button!!.visibility = View.VISIBLE
//        activity?.camera_button!!.visibility = View.VISIBLE
//        activity?.new_followers_button!!.visibility = View.VISIBLE

        val parameter = activity?.toolbar!!.layoutParams as ViewGroup.MarginLayoutParams
        parameter.marginStart = 100
        activity?.toolbar!!.layoutParams = parameter
    }
}