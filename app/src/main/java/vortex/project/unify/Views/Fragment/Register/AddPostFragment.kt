package vortex.project.unify.Views.Fragment.Register

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_pet.*
import kotlinx.android.synthetic.main.fragment_add_post.*
import kotlinx.android.synthetic.main.fragment_reg_pet.*
import vortex.project.unify.R
import vortex.project.unify.Views.Classes.Pet
import vortex.project.unify.Views.Classes.Post
import vortex.project.unify.Views.ViewModel.*
import java.io.ByteArrayOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddPostFragment : Fragment() {

    private lateinit var postsViewModel: PostsViewModel
    private lateinit var postsUserViewModel: PostsUserViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var petsViewModel: PetsViewModel
    private lateinit var petMainViewModel: PetMainViewModel
    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null
    private val GRANTED = PermissionChecker.PERMISSION_GRANTED
    private val CAMERA  = Manifest.permission.CAMERA
    private val REQUEST_IMAGE_CAPTURE = 1003
    private val REQUEST_PERMISSION_CODE = 1011
    private var imageBitmap: Bitmap? = null
    private var encodedImageString = ""

    companion object{private val REQUEST_PERMISSION_REQUEST_CODE = 2020}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        firestoreDB = FirebaseFirestore.getInstance()
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_add_post, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { act ->
            userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
            postsViewModel = ViewModelProviders.of(act).get(PostsViewModel::class.java)
            petsViewModel = ViewModelProviders.of(act).get(PetsViewModel::class.java)
            postsUserViewModel = ViewModelProviders.of(act).get(PostsUserViewModel::class.java)
            petMainViewModel = ViewModelProviders.of(act).get(PetMainViewModel::class.java)
        }
        img_camera_add_post.visibility = View.VISIBLE
        setUpListeners()
        setWidgets()
        changeImage()
    }

    private fun setUpListeners(){
        confirmAddPost_Button.setOnClickListener {
            savePost()
        }
        fab_add_location_post.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_PERMISSION_REQUEST_CODE)
            }else {
                tv_location_post.text = ""
//                tvLatitude.text = ""
//                tvLongitude.text = ""
                location_post_pb.visibility = View.VISIBLE
                getCurrentLocation()
            }

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

    private fun savePost() {
//        if (checkEmpty()) {
            val allPostslist = postsViewModel.postsListVM.value ?: listOf()
            val userPostslist = postsUserViewModel.postsUserListVM.value ?: listOf()
            val petName = petMainViewModel.petMain_nameVM.value.toString()
            val descriptionPost = description_post_input.text.toString()
            val petPhoto = petsViewModel.petsListVM.value?.get(0)!!.pet_photo
            val datePost = getDate()
            val newPost = Post(petPhoto, encodedImageString, petName, datePost, "0", getSecPost(), descriptionPost)

            addPostFirebase(newPost)

            postsViewModel.postsListVM.value = allPostslist + newPost
            postsUserViewModel.postsUserListVM.value = userPostslist + newPost

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

    private fun changeImage() {
        addPhotoAddPost_ImageButton.setOnClickListener {
            when {
                PermissionChecker.checkSelfPermission(requireContext(), CAMERA) == GRANTED -> captureImage()
                shouldShowRequestPermissionRationale(CAMERA) -> showDialogPermission(
                    "É preciso liberar o acesso à câmera!",
                    arrayOf(CAMERA)
                )
                else -> requestPermissions(
                    arrayOf(CAMERA),
                    REQUEST_IMAGE_CAPTURE
                )
            }
        }
    }

    private fun captureImage(){
        val capturaImagemIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(capturaImagemIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: Exception) {
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                try {
                    img_camera_add_post.visibility = View.GONE
                    imageBitmap = data!!.extras!!["data"] as Bitmap?
                    addPhotoAddPost_ImageButton.setImageBitmap(imageBitmap)

                    val baos = ByteArrayOutputStream()
                    imageBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val b: ByteArray = baos.toByteArray()
                    encodedImageString = Base64.encodeToString(b, Base64.DEFAULT)

                } catch (e: Exception) {
                    Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showDialogPermission(
        message: String, permissions: Array<String>
    ) {
        val alertDialog = AlertDialog
            .Builder(requireContext())
            .setTitle("Permissões")
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                requestPermissions(
                    permissions,
                    REQUEST_PERMISSION_CODE)
                dialog.dismiss()
            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        alertDialog.show()
    }

    private fun setWidgets() {
        activity?.bottom_nav_view!!.visibility = View.GONE
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_REQUEST_CODE && grantResults.size > 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation()
            }else{
                Toast.makeText(requireActivity(),"Permission Denied!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {

        var locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        //now getting address from latitude and longitude

        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        var addresses:List<Address>

        LocationServices.getFusedLocationProviderClient(requireActivity())
            .requestLocationUpdates(locationRequest,object : LocationCallback(){
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                        .removeLocationUpdates(this)
                    if (locationResult != null && locationResult.locations.size > 0){
                        var locIndex = locationResult.locations.size-1

                        var latitude = locationResult.locations[locIndex].latitude
                        var longitude = locationResult.locations[locIndex].longitude
//                        tvLatitude.text = "Latitude: "+latitude
//                        tvLongitude.text = "Longitude: "+longitude

                        addresses = geocoder.getFromLocation(latitude,longitude,1)

                        var address:String = addresses[0].getAddressLine(0)
                        tv_location_post.text = address
                        if (tv_location_post != null){
                            location_post_pb.visibility = View.GONE
                        }
                    }
                }
            }, Looper.getMainLooper())

    }
}