package vortex.project.unify.Views.Fragment.Main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_my_pets.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.toolbar.*
import vortex.project.unify.R
import vortex.project.unify.Views.Adapters.MyPetsAdapter
import vortex.project.unify.Views.Adapters.SearchAdapter
import vortex.project.unify.Views.Classes.Pet
import vortex.project.unify.Views.Classes.User
import vortex.project.unify.Views.ViewModel.PetsViewModel
import vortex.project.unify.Views.ViewModel.SearchViewModel
import vortex.project.unify.Views.ViewModel.UserViewModel

class SearchFragment : Fragment(), SearchAdapter.OnItemClickListener {

    private var firestoreDB: FirebaseFirestore? = null
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var petList: List<Pet>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        firestoreDB = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { act ->
            searchViewModel = ViewModelProviders.of(act).get(SearchViewModel::class.java)
            petList = searchViewModel.petsListSearch.value ?: listOf()
        }

        setToolbar()
        setupListeners()
        configRecycleView()
        subscribe()
    }

    private fun configRecycleView() {
        search_recyclerView.layoutManager = LinearLayoutManager(activity)
        search_recyclerView.adapter = SearchAdapter(petList, this)
    }

    override fun onItemClick(position: Int) {
//        val chosenPetName: String = petList[position].pet_name
//        val chosenPetSpecie: String = petList[position].pet_specie
//
//        searchViewModel.petsListSearch.value = chosenPetName
//        searchViewModel.petsListSearch.value = chosenPetSpecie

        Toast.makeText(context, "chosen as your main Pet", Toast.LENGTH_SHORT).show()
    }

    private fun subscribe(){
        searchViewModel.petsListSearch.observe(viewLifecycleOwner, Observer { list->
            if (list != null){
                val adapter = search_recyclerView.adapter
                if (adapter is SearchAdapter){
                    adapter.changeData(list)
                }
            }
        })
    }

    private fun setupListeners() {
        search_text_input.addTextChangedListener {
            searchViewModel.petsListSearch.value = listOf()
            if (!it.isNullOrEmpty()) {
                actionSearch(it.toString())
            }
        }
    }

    private fun actionSearch(petName: String) {
        firestoreDB!!.collection("Users").get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (doc in task.result!!) {
                        firestoreDB!!.collection("Users").document(doc.id)
                            .collection("Pets")
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    for (docPet in task.result!!) {
                                        firestoreDB!!.collection("Pets")
                                            .orderBy("pet_name")
                                            .startAt(petName)
                                            .endAt(petName + "\uf8ff")
                                            .get()
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    petList = searchViewModel.petsListSearch.value ?: listOf()
                                                    val pet = docPet.toObject(Pet::class.java)
                                                    if (pet.pet_name.contains(petName)) {
                                                        searchViewModel.petsListSearch.value = petList + pet
                                                        Log.i("TESTE", pet.pet_name)
                                                    }

                                                }
                                            }
                                        }
                                    }
                            }
                    }
                }
            }
        }

    private fun setToolbar() {
        activity?.toolbar_layout!!.visibility = View.VISIBLE
//        activity?.camera_button!!.visibility = View.VISIBLE
//        activity?.new_followers_button!!.visibility = View.VISIBLE
//            .endAt(petName + "\uf8ff")

    }
}