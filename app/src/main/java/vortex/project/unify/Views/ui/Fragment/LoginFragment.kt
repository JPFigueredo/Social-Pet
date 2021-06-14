package vortex.project.unify.Views.ui.Activity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import vortex.project.unify.R
import vortex.project.unify.Views.Util
import vortex.project.unify.Views.Model.Pet
import vortex.project.unify.Views.Model.Post
import vortex.project.unify.Views.Model.User
import vortex.project.unify.Views.ViewModel.*

class LoginFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var petsViewModel: PetsViewModel
    private lateinit var postsViewModel: PostsViewModel
    private lateinit var postsUserViewModel: PostsUserViewModel
    private lateinit var petMainViewModel: PetMainViewModel

    private var firestoreDB: FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        auth = FirebaseAuth.getInstance()
        firestoreDB = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { act ->
            userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
            petsViewModel = ViewModelProviders.of(act).get(PetsViewModel::class.java)
            postsViewModel = ViewModelProviders.of(act).get(PostsViewModel::class.java)
            postsUserViewModel = ViewModelProviders.of(act).get(PostsUserViewModel::class.java)
            petMainViewModel = ViewModelProviders.of(act).get(PetMainViewModel::class.java)
        }

        fillUserData ()
        setUpListeners()
        setWidgets()
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

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            loadDatabase()
            findNavController().navigate(R.id.action_loginFragment_to_postFragment, null)
        }
    }

    private fun doLogIn() {
        if(emailLogin_input.text.toString().isNotEmpty() || passwordLogin_input.text.toString().isNotEmpty()) {
            auth.signInWithEmailAndPassword(
                emailLogin_input.text.toString(),
                passwordLogin_input.text.toString()
            )
                .addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(context, "Authentication Success", Toast.LENGTH_SHORT).show()
                    loadDatabase()
                    findNavController().navigate(R.id.action_loginFragment_to_postFragment, null)
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, "Authentication Failure", Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUserData() {
        userViewModel.user_idVM.value = auth.currentUser?.uid.toString()
        userViewModel.emailVM.value = Firebase.auth.currentUser?.email
        Util.USER_ID = userViewModel.user_idVM.value!!
    }

    private fun loadPetData() {
        firestoreDB!!.collection("Users").document(userViewModel.user_idVM.value.toString()).collection("Pets").orderBy("pet_name", Query.Direction.ASCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    petsViewModel.petsListVM.value = task.result!!.toObjects(Pet::class.java)
                }
            }
    }
    private fun loadUserPostsData() {
        firestoreDB!!.collection("Users").document(userViewModel.user_idVM.value.toString()).collection("Post").orderBy("secPost", Query.Direction.ASCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    postsUserViewModel.postsUserListVM.value = task.result!!.toObjects(Post::class.java)
                }
            }
    }
    private fun loadAllPostsData() {
        firestoreDB!!.collection("Users")
            .get()
            .addOnCompleteListener { documents ->
                if (documents.isSuccessful) {
                    var postsMutableList = mutableListOf<Post>()
                    for (document in documents.result!!) {
                        firestoreDB!!.collection("Users").document(document.id).collection("Post").orderBy("secPost", Query.Direction.ASCENDING)
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    postsMutableList.addAll(task.result!!.toObjects(Post::class.java))
                                }
                            }
                    }
                    postsMutableList.sortBy{it.secPost}
                    var orderedPostsList = postsMutableList.reversed()
                    postsViewModel.postsListVM.value = postsMutableList
                }
        }
    }

    private fun loadUserData() {
        firestoreDB!!.collection("Users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for(doc in task.result!!){
                        val user_db = doc.toObject(User::class.java)

                        if (user_db.user_id == userViewModel.user_idVM.value.toString()){
                            userViewModel.user_idVM.value = user_db.user_id
                            userViewModel.emailVM.value = user_db.email
                            userViewModel.phoneVM.value = user_db.phone
                            petMainViewModel.petMain_nameVM.value = user_db.mainPetName
                            petMainViewModel.petMain_specieVM.value = user_db.mainPetSpecie
                            petMainViewModel.petMain_photoVM.value = user_db.mainPetPhoto
                        }
                    }
                }
            }
    }

    private fun loadDatabase(){
        getUserData()
        loadUserData()
        loadPetData()
        loadAllPostsData()
        loadUserPostsData()
    }
}
