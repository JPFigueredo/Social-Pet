package vortex.project.unify.Views.ui.Activity.Fragment.Main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_pet.*
import kotlinx.android.synthetic.main.fragment_my_pets.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import vortex.project.unify.R
import vortex.project.unify.Views.Util
import vortex.project.unify.Views.ui.Activity.Adapters.MyPetsAdapter
import vortex.project.unify.Views.Model.Pet
import vortex.project.unify.Views.ViewModel.PetMainViewModel
import vortex.project.unify.Views.ViewModel.PetsViewModel
import vortex.project.unify.Views.ViewModel.UserViewModel

class MyPetsFragment : Fragment(), MyPetsAdapter.OnItemClickListener {

    private lateinit var petsViewModel: PetsViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var petMainViewModel: PetMainViewModel

    private var firestoreDB: FirebaseFirestore? = null

    private lateinit var petList: List<Pet>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        firestoreDB = FirebaseFirestore.getInstance()
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_my_pets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { act ->
            petsViewModel = ViewModelProviders.of(act).get(PetsViewModel::class.java)
            userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
            petMainViewModel = ViewModelProviders.of(act).get(PetMainViewModel::class.java)
            petList = petsViewModel.petsListVM.value ?: listOf()
        }
        updateFirebasePet()
        configRecycleView()
        subscribe()
        setWidgets()
    }

    private fun configRecycleView() {
        myPets_recyclerView.layoutManager = LinearLayoutManager(activity)
        myPets_recyclerView.adapter = MyPetsAdapter(petList, this)
    }

    override fun onItemClick(position: Int) {
        val chosenPetName: String = petList[position].pet_name
        val chosenPetSpecie: String = petList[position].pet_specie
        val chosenPetPhoto: String = petList[position].pet_photo

        petMainViewModel.petMain_nameVM.value = chosenPetName
        petMainViewModel.petMain_specieVM.value = chosenPetSpecie
        petMainViewModel.petMain_photoVM.value = chosenPetPhoto

        setMainPetFirebase(chosenPetName, chosenPetSpecie, chosenPetPhoto)

        Toast.makeText(context, "$chosenPetName chosen as your main Pet", Toast.LENGTH_SHORT).show()
    }

    private fun subscribe(){
        petsViewModel.petsListVM.observe(viewLifecycleOwner, Observer { list->
            if (list != null){
                val adapter = myPets_recyclerView.adapter
                if (adapter is MyPetsAdapter){
                    adapter.changeData(list)
                }
            }
        })
    }

    private fun setWidgets() {
        activity?.toolbar_layout!!.visibility = View.VISIBLE
        activity?.bottom_nav_view!!.visibility = View.VISIBLE
    }

    private fun setMainPetFirebase(chosenPetName: String, chosenPetSpecie: String, chosenPetPhoto: String){

        petMainViewModel.petMain_nameVM.value = chosenPetName
        petMainViewModel.petMain_specieVM.value = chosenPetSpecie
        petMainViewModel.petMain_photoVM.value = chosenPetPhoto

        val pet = hashMapOf(
            "user_id" to userViewModel.user_idVM.value,
            "email" to userViewModel.emailVM.value,
            "phone" to userViewModel.phoneVM.value,
            "mainPetName" to chosenPetName,
            "mainPetSpecie" to chosenPetSpecie,
            "mainPetPhoto" to chosenPetPhoto
        )

        firestoreDB!!.collection("Users").document(userViewModel.user_idVM.value.toString())
            .set(pet)
            .addOnSuccessListener {
                Toast.makeText(context, "Main Pet has been added to Firebase!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Main Pet could not be added to Firebase!", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater!!.inflate(R.menu.add_pet_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_add_pet -> {
                findNavController().navigate(R.id.action_myPets_dest_to_addPetFragment, null)
            }
        }
        return true
    }

    private fun updateFirebasePet(){
        firestoreDB!!.collection("Users").document(Util.USER_ID).collection("Pets")
            .addSnapshotListener(EventListener { documentSnapshots, e ->
                if (e != null) {
                    return@EventListener
                }

                var petList = mutableListOf<Pet>()

                for (doc in documentSnapshots!!) {
                    val pet = doc.toObject(Pet::class.java)
                    Pet(pet.pet_name, pet.pet_specie, pet.pet_gender, pet.pet_followers, pet.pet_posts, pet.pet_address, pet.pet_photo)
                    petList.add(pet)
                }
                petsViewModel.petsListVM.value = petList
            })

    }
}