package vortex.project.unify.Views.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vortex.project.unify.Views.Classes.Pet

class SearchViewModel : ViewModel() {

    val petsListSearch = MutableLiveData<List<Pet>>()

}