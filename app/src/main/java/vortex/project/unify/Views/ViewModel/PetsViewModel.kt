package vortex.project.unify.Views.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vortex.project.unify.Views.Model.Pet

class PetsViewModel: ViewModel() {
    val petsListVM = MutableLiveData<List<Pet>>()
}