package vortex.project.unify.Views.Fragment.Register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.tv_not_member
import kotlinx.android.synthetic.main.fragment_reg_pet.*
import vortex.project.unify.R
import vortex.project.unify.Views.Encrypto
import vortex.project.unify.Views.ViewModel.UserViewModel

class RegPetFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reg_pet, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
                act -> userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
        }
        setUpListeners()
    }
    private fun setUpListeners(){
        reg_pet_fab.setOnClickListener {
            saveViewModel()
            findNavController().navigate(R.id.action_regPetFragment_to_regEmail, null)
        }
    }

    private fun saveViewModel(){
        userViewModel.pet1_nameVM.value = pets_name_input.text.toString()
        userViewModel.pet1_specieVM.value = pets_especies_input.text.toString()
        userViewModel.pet1_genderVM.value = pets_gender_input.text.toString()
    }
}