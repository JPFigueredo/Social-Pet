package vortex.project.unify.Views.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val firstNameDB = MutableLiveData<String>().apply { value = "Black" }
    val lastNameDB = MutableLiveData<String>().apply { value = "Phillips" }
    val addressStreetNameDB = MutableLiveData<String>().apply { value = "Dirt Street" }
    val addressStreetNumberDB = MutableLiveData<String>().apply { value = "76" }
    val addressCityDB = MutableLiveData<String>().apply { value = "Rust Village" }
    val addressUfDB = MutableLiveData<String>().apply { value = "RF" }
    val phoneNumberDB = MutableLiveData<String>().apply { value = "+661 999 666 666" }
    var emailDB = MutableLiveData<String>().apply { value = "blackphillips@dmail.goat" }
    var passwordDB = MutableLiveData<String>().apply { value = "@@Aa123" }
    val pet0NameDB = MutableLiveData<String>().apply { value = "Patolino" }

}