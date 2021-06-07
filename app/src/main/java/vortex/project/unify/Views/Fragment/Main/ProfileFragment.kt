package vortex.project.unify.Views.Fragment.Main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_pet.*
import kotlinx.android.synthetic.main.fragment_profile.*
import vortex.project.unify.R
import vortex.project.unify.Views.ViewModel.UserViewModel

class ProfileFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
        setWidgets()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
                act -> userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
        }
        setData()
        setUpListeners()
    }

    private fun setUpListeners(){
        settings_ImageButton.setOnClickListener {
            findNavController().navigate(R.id.action_profile_dest_to_nav_preferences, null)
        }
    }

    private fun setWidgets() {
        activity?.toolbar_layout!!.visibility = View.GONE
        activity?.bottom_nav_view!!.visibility = View.GONE
    }

    private fun setData(){
        userViewModel.petMain_nameVM.observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotBlank()) {
                tv_pets_name_profile.text = it.toString()
            }
        })

        userViewModel.petMain_specieVM.observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotBlank()) {
                tv_bio.text = it.toString()
            }
        })
    }
}