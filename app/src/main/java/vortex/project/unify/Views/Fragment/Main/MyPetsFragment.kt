package vortex.project.unify.Views.Fragment.Main

import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_pet.*
import kotlinx.android.synthetic.main.fragment_my_pets.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import vortex.project.unify.R
import vortex.project.unify.Views.Adapters.MyPetsAdapter
import vortex.project.unify.Views.Classes.Pet
import vortex.project.unify.Views.Classes.Post
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


//    private fun addPetFirebase(pet_name: String, pet_specie: String, pet_gender: String, pet_followers: Int, pet_posts: Int, pet_address: String, pet_photo: String){
//        val pet = Pet(pet_name, pet_specie, pet_gender, pet_followers, pet_posts, pet_address, pet_photo)
//
//        firestoreDB!!.collection("Users").document(userViewModel.user_idVM.value.toString()).collection("MainPet")
//            .add(pet)
//            .addOnSuccessListener { documentReference ->
//                Log.e(TAG, "DocumentSnapshot written with ID: " + documentReference.id)
//                Toast.makeText(context, "Pet has been added!", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener { e ->
//                Log.e(TAG, "Error adding Product document", e)
//                Toast.makeText(context, "Pet could not be added!", Toast.LENGTH_SHORT).show()
//            }
//
//    }
//    private fun downloadPetsFirebase(){
//        firestoreListener = firestoreDB!!.collection("Users").document(userViewModel.user_idVM.value.toString()).collection("Pets")
//            .addSnapshotListener(EventListener { documentSnapshots, e ->
//                if (e != null) {
//                    return@EventListener
//                }
//
//                for (doc in documentSnapshots!!) {
//                    Log.e("MyPetsFragment", doc.id, e)
//                    val pet = doc.toObject(Pet::class.java)
//
//                    petsViewModel.petsListVM.value = petsViewModel.petsListVM.value!! + pet
//                }
//            })
//    }
//
//    private fun uploadPetsFirebase(){
//        firestoreListener = firestoreDB!!.collection("Users").document(userViewModel.user_idVM.value.toString()).collection("Pets")
//            .addSnapshotListener(EventListener { documentSnapshots, e ->
//                if (e != null) {
//                    return@EventListener
//                }
//
//                for (doc in documentSnapshots!!) {
//                    Log.e("MyPetsFragment", doc.id, e)
//                    val pet = doc.toObject(Pet::class.java)
//
//                    petsViewModel.petsListVM.value = petsViewModel.petsListVM.value!! + pet
//                }
//            })
//    }



//    private fun setToolbar() {
////        val value = TypedValue()
////        activity?.theme!!.resolveAttribute(R.attr.colorOnPrimary, value, true)
////        val background = context?.let { ContextCompat.getColor(it, value.resourceId) }
//
//        activity?.toolbar_layout!!.visibility = View.VISIBLE
//        activity?.drawer_button!!.visibility = View.VISIBLE
////        activity?.camera_button!!.visibility = View.GONE
////        activity?.new_followers_button!!.visibility = View.GONE
//        activity?.bottom_nav_view!!.visibility = View.VISIBLE
////        activity?.toolbar_layout!!.background = background!!.toDrawable()
//
//        val parameter = activity?.toolbar!!.layoutParams as ViewGroup.MarginLayoutParams
//        parameter.marginStart = 0
//        activity?.toolbar!!.layoutParams = parameter
//    }
}