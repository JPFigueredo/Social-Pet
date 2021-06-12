package vortex.project.unify.Views.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vortex.project.unify.Views.Classes.Post

class PostsUserViewModel : ViewModel() {
    val postsUserListVM = MutableLiveData<List<Post>>()
}