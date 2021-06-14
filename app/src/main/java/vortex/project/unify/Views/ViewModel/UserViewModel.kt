package vortex.project.unify.Views.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    var user_idVM = MutableLiveData<String>().apply { value = "" }
    var emailVM = MutableLiveData<String>().apply { value = "" }
    var passwordVM = MutableLiveData<String>().apply { value = "" }
    var phoneVM = MutableLiveData<String>().apply { value = "" }
}
