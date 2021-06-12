package vortex.project.unify.Views.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    var user_idVM = MutableLiveData<String>().apply { value = "" }
    var emailVM = MutableLiveData<String>().apply { value = "mara@gmail.dona" }
//    var emailVM = MutableLiveData<String>().apply { value = "blackphillips@dmail.goat" }
////    var passwordVM = MutableLiveData<ByteArray>().apply { value = null }
//    var passwordVM = MutableLiveData<String>().apply { value = "@@Aa123" }
    var passwordVM = MutableLiveData<String>().apply { value = "@@Aa1234" }

    var phoneVM = MutableLiveData<String>().apply { value = "" }

    var petCountVM = MutableLiveData<Int>().apply { value = 1 }

    var petMain_nameVM = MutableLiveData<String>().apply { value = "Gertrude" }
    var petMain_specieVM = MutableLiveData<String>().apply { value = "Goat" }
    var petMain_genderVM = MutableLiveData<String>().apply { value = "Female" }
    var petMain_postsVM = MutableLiveData<String>().apply { value = "" }
    var petMain_followersVM = MutableLiveData<String>().apply { value = "" }
    var petMain_addressVM = MutableLiveData<String>().apply { value = "" }


    internal fun changeMainPet(petName: String, petSpecie: String){
        petMain_nameVM.value = petName
        petMain_specieVM.value = petSpecie
    }

}
