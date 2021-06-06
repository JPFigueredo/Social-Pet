package vortex.project.unify.Views.Fragment.Main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
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

    }

    private fun setWidgets() {
        activity?.toolbar_layout!!.visibility = View.GONE
        activity?.bottom_nav_view!!.visibility = View.GONE
    }

    private fun setData(){
        tv_pets_name_profile.text = userViewModel.pet1_nameVM.value.toString()
        tv_bio.text = userViewModel.pet1_specieVM.value.toString()
    }
}