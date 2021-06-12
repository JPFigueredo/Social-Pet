package vortex.project.unify.Views.Fragment.Main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_my_pets.*
import vortex.project.unify.R
import vortex.project.unify.Views.Activity.MainActivity
import vortex.project.unify.Views.Adapters.MyPetsAdapter
import vortex.project.unify.Views.Adapters.PostsAdapter
import vortex.project.unify.Views.Classes.Post
import vortex.project.unify.Views.ViewModel.PetsViewModel
import vortex.project.unify.Views.ViewModel.PostsViewModel
import vortex.project.unify.Views.ViewModel.UserViewModel


class FeedFragment : Fragment(), PostsAdapter.OnItemClickListener {

    private lateinit var postsViewModel: PostsViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var petsViewModel: PetsViewModel
    private lateinit var postsList: List<Post>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { act ->
            userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
            postsViewModel = ViewModelProviders.of(act).get(PostsViewModel::class.java)
            petsViewModel = ViewModelProviders.of(act).get(PetsViewModel::class.java)
            postsList = postsViewModel.postsListVM.value ?: listOf()
        }
        setWidgets()
        setUpListeners()
        configRecycleView()
        subscribe()
    }
    private fun setUpListeners(){
        fab_add_post.setOnClickListener {
            findNavController().navigate(R.id.action_home_dest_to_addPostFragment, null)
        }
    }

    private fun configRecycleView() {
        post_recyclerView.layoutManager = LinearLayoutManager(activity)
        post_recyclerView.adapter = PostsAdapter(postsList, this)
//        post_recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }
    override fun onItemClick(position: Int) {
        postsList = postsViewModel.postsListVM.value ?: listOf()
        val petPostName: String = postsList[position].petNamePost
        var postLikes = postsList[position].likesPost.toInt()
        postLikes += 1

        postsList[position].likesPost = postLikes.toString()
        postsViewModel.postsListVM.value = postsList

        Toast.makeText(context, "You Like a post of $petPostName", Toast.LENGTH_SHORT).show()
    }
    private fun subscribe(){
        postsViewModel.postsListVM.observe(viewLifecycleOwner, Observer { list->
            if (list != null){
                val adapter = post_recyclerView.adapter
                if (adapter is PostsAdapter){
                    adapter.changeData(list)
                }
            }
        })
    }

//    private fun subscribe(){
//        if (getRestaurantsList() != null){
//            val adapter = post_recyclerView.adapter
//            if (adapter is PostsAdapter){
//                adapter.changeData(getRestaurantsList())
//            }
//        }
//    }

//    private fun getRestaurantsList(): ArrayList<Post> {
//        val databaseHandler: DatabaseHandler = DatabaseHandler(context)
//        val restaurantList: ArrayList<Post> = databaseHandler.viewRestaurants()
//        return restaurantList
//    }

    private fun setWidgets() {
//        activity?.bottom_nav_view!!.visibility = View.VISIBLE
        (activity as MainActivity).bottom_nav_view!!.visibility = View.VISIBLE
    }
}