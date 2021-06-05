package vortex.project.unify.Views.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    var user_idVM = MutableLiveData<String>().apply { value = "" }
    var emailVM = MutableLiveData<String>().apply { value = "blackphillips@dmail.goat" }
//    var passwordVM = MutableLiveData<ByteArray>().apply { value = null }
    var passwordVM = MutableLiveData<String>().apply { value = "@@Aa123" }
    var phoneVM = MutableLiveData<String>().apply { value = "" }

    var pet1_nameVM = MutableLiveData<String>().apply { value = "Gertrude" }
    var pet1_specieVM = MutableLiveData<String>().apply { value = "Goat" }
    var pet1_genderVM = MutableLiveData<String>().apply { value = "Female" }
    var pet1_followersVM = MutableLiveData<String>().apply { value = "" }
    var pet1_addressVM = MutableLiveData<String>().apply { value = "" }

    var pet2_nameVM = MutableLiveData<String>().apply { value = "" }
    var pet2_specieVM = MutableLiveData<String>().apply { value = "" }
    var pet2_genderVM = MutableLiveData<String>().apply { value = "" }
    var pet2_followersVM = MutableLiveData<String>().apply { value = "" }
    var pet2_addressVM = MutableLiveData<String>().apply { value = "" }

    var pet3_nameVM = MutableLiveData<String>().apply { value = "" }
    var pet3_specieVM = MutableLiveData<String>().apply { value = "" }
    var pet3_genderVM = MutableLiveData<String>().apply { value = "" }
    var pet3_followersVM = MutableLiveData<String>().apply { value = "" }
    var pet3_addressVM = MutableLiveData<String>().apply { value = "" }

    var pet4_nameVM = MutableLiveData<String>().apply { value = "" }
    var pet4_specieVM = MutableLiveData<String>().apply { value = "" }
    var pet4_genderVM = MutableLiveData<String>().apply { value = "" }
    var pet4_followersVM = MutableLiveData<String>().apply { value = "" }
    var pet4_addressVM = MutableLiveData<String>().apply { value = "" }

    var pet5_nameVM = MutableLiveData<String>().apply { value = "" }
    var pet5_specieVM = MutableLiveData<String>().apply { value = "" }
    var pet5_genderVM = MutableLiveData<String>().apply { value = "" }
    var pet5_followersVM = MutableLiveData<String>().apply { value = "" }
    var pet5_addressVM = MutableLiveData<String>().apply { value = "" }

}