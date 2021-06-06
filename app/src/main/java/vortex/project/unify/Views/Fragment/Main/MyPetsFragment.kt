package vortex.project.unify.Views.Fragment.Main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_pet.*
import kotlinx.android.synthetic.main.fragment_my_pets.*
import kotlinx.android.synthetic.main.fragment_my_pets.fab_add_pet
import kotlinx.android.synthetic.main.toolbar.*
import vortex.project.unify.R
import vortex.project.unify.Views.Adapters.MyPetsAdapter
import vortex.project.unify.Views.Classes.Pet
import vortex.project.unify.Views.ViewModel.PetsViewModel
import vortex.project.unify.Views.ViewModel.UserViewModel

class MyPetsFragment : Fragment() {

    private lateinit var petsViewModel: PetsViewModel
//    private val ADD_REQUEST_CODE = 51

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_pets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { act ->
            petsViewModel = ViewModelProviders.of(act).get(PetsViewModel::class.java)
        }
        configRecycleView()
        subscribe()
        setUpListeners()
//        setToolbar()
    }
    private fun setUpListeners(){
        fab_add_pet.setOnClickListener {
            findNavController().navigate(R.id.action_myPets_dest_to_addPetFragment, null)
        }
    }
    private fun configRecycleView() {
        myPets_recyclerView.layoutManager = LinearLayoutManager(activity)
        myPets_recyclerView.adapter = MyPetsAdapter()
        myPets_recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }
    private fun subscribe(){
        petsViewModel.petsListVM.observe(viewLifecycleOwner, Observer {
                list->
            if (list != null){
                val adapter = myPets_recyclerView.adapter
                if (adapter is MyPetsAdapter){
                    adapter.changeData(list)
                }
            }
        })
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK){
//            if (requestCode == ADD_REQUEST_CODE){
//                data?.let {
//                    val list = petsViewModel.petsListVM.value ?: listOf()
//                    val petName = data.getStringExtra("petName").toString()
//                    val petSpecie = data.getStringExtra("petSpecie").toString()
//                    val petGender = data.getStringExtra("petGender").toString()
//
//                    val newPet = Pet(petName, petSpecie, petGender, null, null, null)
//                    petsViewModel.petsListVM.value = list + newPet
//                }
//            }
//        }
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