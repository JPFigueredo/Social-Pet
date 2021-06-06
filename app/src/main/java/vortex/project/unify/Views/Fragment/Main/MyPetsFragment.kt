package vortex.project.unify.Views.Fragment.Main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_pet.*
import kotlinx.android.synthetic.main.fragment_my_pets.*
import kotlinx.android.synthetic.main.fragment_my_pets.fab_add_pet
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import vortex.project.unify.R
import vortex.project.unify.Views.Adapters.MyPetsAdapter
import vortex.project.unify.Views.Classes.Pet
import vortex.project.unify.Views.ViewModel.PetsViewModel
import vortex.project.unify.Views.ViewModel.UserViewModel

class MyPetsFragment : Fragment(), MyPetsAdapter.OnItemClickListener {

    private lateinit var petsViewModel: PetsViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var petList: List<Pet>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_pets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { act ->
            petsViewModel = ViewModelProviders.of(act).get(PetsViewModel::class.java)
            userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
            petList = petsViewModel.petsListVM.value ?: listOf()
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
        myPets_recyclerView.adapter = MyPetsAdapter(petList, this)
        myPets_recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    override fun onItemClick(position: Int) {
        val chosenPetName: String = petList[position].pet_name
        val chosenPetSpecie: String = petList[position].pet_specie

        userViewModel.petMain_nameVM.value = chosenPetName
        userViewModel.petMain_specieVM.value = chosenPetSpecie

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