package vortex.project.unify.Views.ui.Activity.Fragment.Register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_reg_phone.*
import vortex.project.unify.R
import vortex.project.unify.Views.ViewModel.UserViewModel

class RegPhoneFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reg_phone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
                act -> userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
        }
        setUpListeners()
    }

    private fun setUpListeners(){
        btn_next_phone.setOnClickListener {
            if (regPhone_input != null) {
                saveViewModel()
            }
            findNavController().navigate(R.id.action_reg_phone_to_reg_password, null)
        }
    }

    private fun saveViewModel(){
        userViewModel.phoneVM.value = regPhone_input.text.toString()
    }
}