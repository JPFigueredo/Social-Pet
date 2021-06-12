package vortex.project.unify.Views.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vortex.project.unify.Views.Classes.Post

class PostsViewModel: ViewModel() {
    val postsListVM = MutableLiveData<List<Post>>()
}