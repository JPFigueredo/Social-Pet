package vortex.project.unify.Views.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PetMainViewModel: ViewModel() {

    var petMain_nameVM = MutableLiveData<String>().apply { value = "Main Pet" }
    var petMain_specieVM = MutableLiveData<String>().apply { value = "" }
    var petMain_photoVM = MutableLiveData<String>().apply { value = "" }
    var petMain_genderVM = MutableLiveData<String>().apply { value = "" }
    var petMain_followersVM = MutableLiveData<String>().apply { value = "" }
    var petMain_addressVM = MutableLiveData<String>().apply { value = "" }

}