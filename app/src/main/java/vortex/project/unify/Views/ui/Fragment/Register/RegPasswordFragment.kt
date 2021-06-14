package vortex.project.unify.Views.ui.Activity.Fragment.Register

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_reg_password.*
import vortex.project.unify.R
import vortex.project.unify.Views.Model.Pet
import vortex.project.unify.Views.ViewModel.PetsViewModel
import vortex.project.unify.Views.ViewModel.UserViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegPasswordFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var petsViewModel: PetsViewModel
    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        auth = FirebaseAuth.getInstance()
        firestoreDB = FirebaseFirestore.getInstance()
        val view = inflater.inflate(R.layout.fragment_reg_password, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { act ->
            petsViewModel = ViewModelProviders.of(act).get(PetsViewModel::class.java)
            userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
        }
        setUpListeners()
    }

    private fun setUpListeners() {
        Snackbar.make(regPassword_root, "Enter 8-12 letter, 1 Number, 1 lowercase letter, 1 uppercase letter, 1 special character.", Snackbar.LENGTH_LONG).show()

        btn_next_password.setOnClickListener {
            if (checkPassword()) {
                saveViewModel()
                doRegister()
            } else {
                Snackbar.make(regPassword_root, "Enter 8-12 letter, 1 Number, 1 lowercase letter, 1 uppercase letter, 1 special character.", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun checkPassword(): Boolean {
        
        val passwordInput = regPassword_input.text.toString()
        val confirmInput = regConfirmPassword_input.text.toString()
        val passwordREGEX: Pattern = Pattern.compile("""^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%¨&*()_+=-])(?=\S+$).{8,12}$""")
        val match : Matcher = passwordREGEX.matcher(passwordInput)
        var flag = true

        if (passwordInput != confirmInput) {
            flag = false
        }
        if(passwordInput.isEmpty()){
            flag = false
        }
        if(!match.matches()){
            flag = false
        }
        return flag
    }

    private fun doRegister() {
        auth.createUserWithEmailAndPassword(userViewModel.emailVM.value.toString(), userViewModel.passwordVM.value.toString())
            .addOnSuccessListener {
                Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()
                getUserData()
                addNewUserDatabase()
                findNavController().navigate(R.id.action_reg_password_to_login, null)
            }
            .addOnFailureListener {
                when (it) {
                    is FirebaseAuthWeakPasswordException -> Toast.makeText(context, "  Password length error", Toast.LENGTH_SHORT).show()
                    is FirebaseAuthUserCollisionException -> Toast.makeText(context, "E-mail already registered", Toast.LENGTH_SHORT).show()
                    is FirebaseNetworkException -> Toast.makeText(context, " Internet error", Toast.LENGTH_SHORT).show()
                    else ->  Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }

    }
    private fun getUserData() {
        userViewModel.user_idVM.value = auth.currentUser?.uid.toString()
        userViewModel.emailVM.value = Firebase.auth.currentUser?.email
    }

    private fun addNewUserDatabase(){
        val petsList = petsViewModel.petsListVM.value?: listOf()
        val pet_name = petsList!![0].pet_name
        val pet_gender = petsList!![0].pet_gender
        val pet_specie = petsList!![0].pet_specie
        val pet_followers = petsList!![0].pet_followers
        val pet_posts = petsList!![0].pet_posts
        val pet_address = petsList!![0].pet_address
        val pet_photo = petsList!![0].pet_photo


        addUserFirebase(userViewModel.user_idVM.value.toString(), userViewModel.emailVM.value.toString(), userViewModel.phoneVM.value.toString(), pet_name, pet_specie)
        addPetFirebase(pet_name, pet_specie, pet_gender, pet_followers, pet_posts, pet_address, pet_photo)

    }

    private fun addUserFirebase(user_id: String, email: String, phone: String, mainPetName: String, mainPetSpecie: String){

        val user = hashMapOf(
            "user_id" to user_id,
            "email" to email,
            "phone" to phone,
            "mainPetName" to mainPetName,
            "mainPetSpecie" to mainPetSpecie
        )

        firestoreDB!!.collection("Users").document(userViewModel.user_idVM.value.toString())
                .set(user)
            .addOnSuccessListener {
                Toast.makeText(context, "User has been added!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "User could not be added!", Toast.LENGTH_SHORT).show()
            }

    }
    private fun addPetFirebase(pet_name: String, pet_specie: String, pet_gender: String, pet_followers: Int, pet_posts: Int, pet_address: String, pet_photo: String){
        val pet = Pet(pet_name, pet_specie, pet_gender, pet_followers, pet_posts, pet_address, pet_photo)

        firestoreDB!!.collection("Users").document(userViewModel.user_idVM.value.toString()).collection("Pets")
            .add(pet)
            .addOnSuccessListener { documentReference ->
                Log.e(ContentValues.TAG, "DocumentSnapshot written with ID: " + documentReference.id)
                Toast.makeText(context, "Pet has been added!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e(ContentValues.TAG, "Error adding Product document", e)
                Toast.makeText(context, "Pet could not be added!", Toast.LENGTH_SHORT).show()
            }

    }

    //Somente para testes
    private fun saveViewModel(){
        userViewModel.passwordVM.value = regPassword_input.text.toString()
    }
}
