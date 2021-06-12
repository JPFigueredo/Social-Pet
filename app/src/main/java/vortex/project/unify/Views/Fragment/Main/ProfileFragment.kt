package vortex.project.unify.Views.Fragment.Main

import android.os.Bundle
import android.view.*
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
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
                act -> userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
        }

//        setWidgets()
        setData()
        setUpListeners()
    }

    private fun setUpListeners(){
//        settings_ImageButton.setOnClickListener {
//            findNavController().navigate(R.id.action_profile_dest_to_nav_preferences, null)
//        }
    }

//    private fun setWidgets() {
//        activity?.toolbar_layout!!.visibility = View.VISIBLE
//        activity?.bottom_nav_view!!.visibility = View.VISIBLE
//    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater!!.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout -> {
                //auth.signOut()
            }
            R.id.nav_preferences -> {
                findNavController().navigate(R.id.nav_preferences, null)
            }
        }
        return true
    }
}