package vortex.project.unify.Views.Fragment.Register

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_add_pet.*
import kotlinx.android.synthetic.main.fragment_add_post.*
import vortex.project.unify.R
import vortex.project.unify.Views.Classes.Pet
import vortex.project.unify.Views.Classes.Post
import vortex.project.unify.Views.ViewModel.PetsViewModel
import vortex.project.unify.Views.ViewModel.PostsViewModel
import vortex.project.unify.Views.ViewModel.UserViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddPostFragment : Fragment() {

    private lateinit var postsViewModel: PostsViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var petsViewModel: PetsViewModel
    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        firestoreDB = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_add_post, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { act ->
            userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
            postsViewModel = ViewModelProviders.of(act).get(PostsViewModel::class.java)
            petsViewModel = ViewModelProviders.of(act).get(PetsViewModel::class.java)
        }
        setUpListeners()
    }
    private fun setUpListeners(){
        confirmAddPost_Button.setOnClickListener {
            saveViewModel()
        }
    }
    private fun addPostFirebase(newPost: Post){
        firestoreDB!!.collection("Users").document(userViewModel.user_idVM.value.toString()).collection("Post")
            .add(newPost)
            .addOnSuccessListener { documentReference ->
                Log.e(ContentValues.TAG, "DocumentSnapshot written with ID: " + documentReference.id)
                Toast.makeText(context, "Post has been added!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e(ContentValues.TAG, "Error adding Product document", e)
                Toast.makeText(context, "Post could not be added!", Toast.LENGTH_SHORT).show()
            }

    }

    private fun saveViewModel() {
//        if (checkEmpty()) {
            val list = postsViewModel.postsListVM.value ?: listOf()
            val petName = userViewModel.petMain_nameVM.value.toString()
            val datePost = getDate()
            val newPost = Post(petName, datePost, "0", getSecPost())

            addPostFirebase(newPost)

            postsViewModel.postsListVM.value = list + newPost

            findNavController().navigate(R.id.action_addPostFragment_to_home_dest, null)

//        }
    }

    private fun checkEmpty(): Boolean {
        var check = false

        if (add_pets_name_input.text!!.isNotEmpty()) {
            if (add_pets_especies_input.text!!.isNotEmpty()) {
                if (add_pets_gender_input.text!!.isNotEmpty()) {
                    check = true
                } else {
                    Toast.makeText(context, "Pet`s Gender can't be empty", Toast.LENGTH_SHORT).show()}
            } else {
                Toast.makeText(context, "Pet`s Specie can't be empty", Toast.LENGTH_SHORT).show()}
        } else {
            Toast.makeText(context, "Pet`s Name can't be empty", Toast.LENGTH_SHORT).show()}

        return check
    }
    private fun getDate(): String {
        val date = Calendar.getInstance().time
        return DateFormat.getDateTimeInstance().format(date)
    }
    private fun getSecPost(): String {
        return Date().time.toString()
    }
}