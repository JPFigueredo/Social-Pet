package vortex.project.unify.Views.Fragment.Register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_pet.*
import kotlinx.android.synthetic.main.fragment_my_pets.*
import kotlinx.android.synthetic.main.fragment_reg_pet.*
import kotlinx.android.synthetic.main.fragment_reg_pet.reg_pet_fab
import vortex.project.unify.R
import vortex.project.unify.Views.Classes.Pet
import vortex.project.unify.Views.ViewModel.PetsViewModel
import vortex.project.unify.Views.ViewModel.UserViewModel

class AddPetFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var petsViewModel: PetsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_pet, container, false)
        setWidgets()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { act ->
            userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
            petsViewModel = ViewModelProviders.of(act).get(PetsViewModel::class.java)
        }
        setUpListeners()
    }
    private fun setUpListeners(){
        add_pet_finish_fab.setOnClickListener {
            saveViewModel()
        }
    }
    private fun setWidgets() {
        activity?.toolbar_layout!!.visibility = View.GONE
        activity?.bottom_nav_view!!.visibility = View.GONE
    }

    private fun saveViewModel() {
        if (checkEmpty()) {
            var list = petsViewModel.petsListVM.value ?: listOf()
            var petName = add_pets_name_input.text.toString()
            var petSpecie = add_pets_especies_input.text.toString()
            var petGender = add_pets_gender_input.text.toString()

            if (userViewModel.petCountVM.value == 0) {
                userViewModel.pet1_nameVM.value = petName
                userViewModel.pet1_specieVM.value = petSpecie
                userViewModel.pet1_genderVM.value = petGender

                val newPet = Pet(petName, petSpecie, petGender, null, null, null)
                petsViewModel.petsListVM.value = list + newPet

            } else if (userViewModel.petCountVM.value == 1) {
                userViewModel.pet2_nameVM.value = petName
                userViewModel.pet2_specieVM.value = petSpecie
                userViewModel.pet2_genderVM.value = petGender

                val newPet = Pet(petName, petSpecie, petGender, null, null, null)
                petsViewModel.petsListVM.value = list + newPet

            } else if (userViewModel.petCountVM.value == 2) {
                userViewModel.pet3_nameVM.value = petName
                userViewModel.pet3_specieVM.value = petSpecie
                userViewModel.pet3_genderVM.value = petGender

                val newPet = Pet(petName, petSpecie, petGender, null, null, null)
                petsViewModel.petsListVM.value = list + newPet

            } else if (userViewModel.petCountVM.value == 3) {
                userViewModel.pet4_nameVM.value = petName
                userViewModel.pet4_specieVM.value = petSpecie
                userViewModel.pet4_genderVM.value = petGender

                val newPet = Pet(petName, petSpecie, petGender, null, null, null)
                petsViewModel.petsListVM.value = list + newPet

            } else if (userViewModel.petCountVM.value == 4) {
                userViewModel.pet5_nameVM.value = petName
                userViewModel.pet5_specieVM.value = petSpecie
                userViewModel.pet5_genderVM.value = petGender

                val newPet = Pet(petName, petSpecie, petGender, null, null, null)
                petsViewModel.petsListVM.value = list + newPet

            } else {
                Toast.makeText(context, "You already have the maximum number of registered Pets. If you want to add more pets, buy Premium.", Toast.LENGTH_LONG).show()
            }
            userViewModel.petCountVM.value = userViewModel.petCountVM.value?.plus(1)
            findNavController().navigate(R.id.action_addPetFragment_to_myPets_dest, null)
        }
    }

    private fun checkEmpty(): Boolean {
        var check = false

        if (add_pets_name_input.text!!.isNotEmpty()) {
            if (add_pets_especies_input.text!!.isNotEmpty()) {
                if (add_pets_gender_input.text!!.isNotEmpty()) {
                    check = true
                } else {Toast.makeText(context, "Pet`s Gender can't be empty", Toast.LENGTH_SHORT).show()}
            } else {Toast.makeText(context, "Pet`s Specie can't be empty", Toast.LENGTH_SHORT).show()}
        } else {Toast.makeText(context, "Pet`s Name can't be empty", Toast.LENGTH_SHORT).show()}

        return check
    }
}