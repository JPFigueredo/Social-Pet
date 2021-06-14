package vortex.project.unify.Views.ui.Activity.Fragment.Register

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_reg_pet.*
import vortex.project.unify.R
import vortex.project.unify.Views.Model.Pet
import vortex.project.unify.Views.ViewModel.PetMainViewModel
import vortex.project.unify.Views.ViewModel.PetsViewModel
import vortex.project.unify.Views.ViewModel.UserViewModel
import java.io.ByteArrayOutputStream

class RegPetFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var petsViewModel: PetsViewModel
    private lateinit var petMainViewModel: PetMainViewModel
    private val GRANTED = PermissionChecker.PERMISSION_GRANTED
    private val CAMERA  = Manifest.permission.CAMERA
    private val REQUEST_IMAGE_CAPTURE = 1002
    private val REQUEST_PERMISSION_CODE = 1010
    private var imageBitmap: Bitmap? = null
    private var encodedImageString = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reg_pet, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {act ->
            userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
            petsViewModel = ViewModelProviders.of(act).get(PetsViewModel::class.java)
            petMainViewModel = ViewModelProviders.of(act).get(PetMainViewModel::class.java)
        }
        setUpListeners()
        changeImage()
    }
    private fun setUpListeners(){
        reg_pet_fab.setOnClickListener {
            saveViewModel()
            findNavController().navigate(R.id.action_regPetFragment_to_regEmail, null)
        }
    }

    private fun saveViewModel(){
        petsViewModel.petsListVM.value = null
        val list = petsViewModel.petsListVM.value ?: listOf()
        val petName = reg_pets_name_input.text.toString()
        val petSpecie = reg_pets_especies_input.text.toString()
        val petGender = reg_pets_gender_input.text.toString()
        val petPhoto = encodedImageString
        val newPet = Pet(petName, petSpecie, petGender,0,0,"", petPhoto)
        petsViewModel.petsListVM.value = list + newPet
        petMainViewModel.petMain_nameVM.value = petName
        petMainViewModel.petMain_specieVM.value = petSpecie
    }

    private fun changeImage() {
        btn_change_profile_photo_reg.setOnClickListener {
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
                    imageBitmap = data!!.extras!!["data"] as Bitmap?
                    img_edit_profile_reg.setImageBitmap(imageBitmap)

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
}