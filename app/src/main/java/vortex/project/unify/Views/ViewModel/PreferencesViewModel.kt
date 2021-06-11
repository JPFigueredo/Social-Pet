package vortex.project.unify.Views.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PreferencesViewModel : ViewModel() {

    val modeNight = MutableLiveData<String>().apply { value = "Night Mode" }

    val language =  MutableLiveData<String>().apply { value = "English" }

}