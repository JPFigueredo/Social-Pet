package vortex.project.unify.Views.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_reg_pet.*
import vortex.project.unify.R
import vortex.project.unify.Views.Classes.Pet
import vortex.project.unify.Views.Encrypto
import vortex.project.unify.Views.ViewModel.PetsViewModel
import vortex.project.unify.Views.ViewModel.UserViewModel

class LoginFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var petsViewModel: PetsViewModel
    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null
    private lateinit var auth: FirebaseAuth
    private val encrypto = Encrypto()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        auth = FirebaseAuth.getInstance()
        firestoreDB = FirebaseFirestore.getInstance()

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { act ->
            userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
            petsViewModel = ViewModelProviders.of(act).get(PetsViewModel::class.java)
        }
        fillUserData ()
        setUpListeners()
//        setWidgets()
    }

    private fun setUpListeners(){
        tv_not_member.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_regPetFragment, null)
        }
        fab_login.setOnClickListener {
            doLogIn()
        }
        tv_forgot_password.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment, null)
        }
    }

    private fun setWidgets() {
        activity?.toolbar_layout!!.visibility = View.GONE
        activity?.bottom_nav_view!!.visibility = View.GONE
    }

    private fun fillUserData () {
        userViewModel.emailVM.observe(viewLifecycleOwner, Observer {
            if(it != null && it.isNotBlank()){
                emailLogin_input.setText(it.toString())
            }
        })
        userViewModel.passwordVM.observe(viewLifecycleOwner, Observer {
            if(it != null){
                passwordLogin_input.setText(userViewModel.passwordVM.value)
            }
        })
    }

//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            getUserData()
//            loadUserData()
//            getPetsDataFromUserVMtoPetsVM()
//            findNavController().navigate(R.id.action_loginFragment_to_postFragment, null)
//        }
//    }

    fun doLogIn() {
        auth.signInWithEmailAndPassword(emailLogin_input.text.toString(), passwordLogin_input.text.toString())
            .addOnSuccessListener { taskSnapshot ->
                Toast.makeText(context, "Authentication Success", Toast.LENGTH_SHORT).show()
                getUserData()
                loadUserData()
//                getPetsDataFromUserVMtoPetsVM()
                findNavController().navigate(R.id.action_loginFragment_to_postFragment, null)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Authentication Failure", Toast.LENGTH_SHORT).show()
            }

    }

    private fun getUserData() {
        userViewModel.user_idVM.value = auth.currentUser?.uid.toString()
        userViewModel.emailVM.value = Firebase.auth.currentUser?.email
    }

    private fun loadUserData() {
        firestoreDB!!.collection("Users").document(userViewModel.user_idVM.value.toString()).collection("Pets")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    petsViewModel.petsListVM.value = task.result!!.toObjects(Pet::class.java)

//                    for (doc in task.result!!) {
//                        val pet = doc.toObject(Pet::class.java)
//                        petsViewModel.petsListVM.value = petsViewModel.petsListVM.value!! + pet
//                    }
            }
        }
    }

    //Firebase
    //fb.collection("Users").document("dasdasda").collection("Pets")

//    private fun getPetsDataFromUserVMtoPetsVM() {
//        if (userViewModel.petCountVM.value == 0) {
//            //Do nothing
//        } else if (userViewModel.petCountVM.value == 1) {
//            var petsList = petsViewModel.petsListVM.value ?: listOf()
//            val petName = userViewModel.pet1_nameVM.value.toString()
//            val petSpecie = userViewModel.pet1_specieVM.value.toString()
//            val petGender = userViewModel.pet1_genderVM.value.toString()
//
//            val addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//        } else if (userViewModel.petCountVM.value == 2) {
//            var petsList = petsViewModel.petsListVM.value ?: listOf()
//            var petName = userViewModel.pet1_nameVM.value.toString()
//            var petSpecie = userViewModel.pet1_specieVM.value.toString()
//            var petGender = userViewModel.pet1_genderVM.value.toString()
//            var addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//            petName = userViewModel.pet2_nameVM.value.toString()
//            petSpecie = userViewModel.pet2_specieVM.value.toString()
//            petGender = userViewModel.pet2_genderVM.value.toString()
//            addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//        } else if (userViewModel.petCountVM.value == 3) {
//            var petsList = petsViewModel.petsListVM.value ?: listOf()
//            var petName = userViewModel.pet1_nameVM.value.toString()
//            var petSpecie = userViewModel.pet1_specieVM.value.toString()
//            var petGender = userViewModel.pet1_genderVM.value.toString()
//            var addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//            petName = userViewModel.pet2_nameVM.value.toString()
//            petSpecie = userViewModel.pet2_specieVM.value.toString()
//            petGender = userViewModel.pet2_genderVM.value.toString()
//            addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//            petName = userViewModel.pet3_nameVM.value.toString()
//            petSpecie = userViewModel.pet3_specieVM.value.toString()
//            petGender = userViewModel.pet3_genderVM.value.toString()
//            addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//        } else if (userViewModel.petCountVM.value == 4) {
//            var petsList = petsViewModel.petsListVM.value ?: listOf()
//            var petName = userViewModel.pet1_nameVM.value.toString()
//            var petSpecie = userViewModel.pet1_specieVM.value.toString()
//            var petGender = userViewModel.pet1_genderVM.value.toString()
//            var addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//            petName = userViewModel.pet2_nameVM.value.toString()
//            petSpecie = userViewModel.pet2_specieVM.value.toString()
//            petGender = userViewModel.pet2_genderVM.value.toString()
//            addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//            petName = userViewModel.pet3_nameVM.value.toString()
//            petSpecie = userViewModel.pet3_specieVM.value.toString()
//            petGender = userViewModel.pet3_genderVM.value.toString()
//            addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//            petName = userViewModel.pet4_nameVM.value.toString()
//            petSpecie = userViewModel.pet4_specieVM.value.toString()
//            petGender = userViewModel.pet4_genderVM.value.toString()
//            addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//        }  else if (userViewModel.petCountVM.value == 4) {
//            var petsList = petsViewModel.petsListVM.value ?: listOf()
//            var petName = userViewModel.pet1_nameVM.value.toString()
//            var petSpecie = userViewModel.pet1_specieVM.value.toString()
//            var petGender = userViewModel.pet1_genderVM.value.toString()
//            var addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//            petName = userViewModel.pet2_nameVM.value.toString()
//            petSpecie = userViewModel.pet2_specieVM.value.toString()
//            petGender = userViewModel.pet2_genderVM.value.toString()
//            addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//            petName = userViewModel.pet3_nameVM.value.toString()
//            petSpecie = userViewModel.pet3_specieVM.value.toString()
//            petGender = userViewModel.pet3_genderVM.value.toString()
//            addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//            petName = userViewModel.pet4_nameVM.value.toString()
//            petSpecie = userViewModel.pet4_specieVM.value.toString()
//            petGender = userViewModel.pet4_genderVM.value.toString()
//            addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//        } else if (userViewModel.petCountVM.value == 5) {
//            var petsList = petsViewModel.petsListVM.value ?: listOf()
//            var petName = userViewModel.pet1_nameVM.value.toString()
//            var petSpecie = userViewModel.pet1_specieVM.value.toString()
//            var petGender = userViewModel.pet1_genderVM.value.toString()
//            var addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//            petName = userViewModel.pet2_nameVM.value.toString()
//            petSpecie = userViewModel.pet2_specieVM.value.toString()
//            petGender = userViewModel.pet2_genderVM.value.toString()
//            addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//            petName = userViewModel.pet3_nameVM.value.toString()
//            petSpecie = userViewModel.pet3_specieVM.value.toString()
//            petGender = userViewModel.pet3_genderVM.value.toString()
//            addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//            petName = userViewModel.pet4_nameVM.value.toString()
//            petSpecie = userViewModel.pet4_specieVM.value.toString()
//            petGender = userViewModel.pet4_genderVM.value.toString()
//            addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//
//            petName = userViewModel.pet5_nameVM.value.toString()
//            petSpecie = userViewModel.pet5_specieVM.value.toString()
//            petGender = userViewModel.pet5_genderVM.value.toString()
//            addPet = Pet(petName, petSpecie, petGender, null, null, null, null)
//            petsViewModel.petsListVM.value = petsList + addPet
//        }
//    }

}
