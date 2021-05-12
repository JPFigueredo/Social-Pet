package vortex.project.unify.Views.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
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

    private fun setUpListeners(){
        btn_next_email.setOnClickListener {
            findNavController().navigate(R.id.phoneFragment, null)
        }
    }

    private fun setToolbar() {
        //val background = context?.let { AppCompatResources.getDrawable(it, R.drawable.rect_gradient) }

        activity?.toolbar_feed!!.visibility = View.GONE
    }
}