package vortex.project.unify.Views.Fragment.Main

import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.toolbar.*
import vortex.project.unify.R
import vortex.project.unify.Views.Adapters.PostsAdapter


class PostFragment : Fragment() {

    private val ADD_REQUEST_CODE = 50

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWidgets()
    }

    private fun configRecycleView() {
        post_recyclerView.layoutManager = LinearLayoutManager(activity)
        post_recyclerView.adapter = PostsAdapter()
        post_recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
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
        activity?.bottom_nav_view!!.visibility = View.VISIBLE

        activity?.toolbar_layout!!.visibility = View.VISIBLE

        val parameter = activity?.toolbar!!.layoutParams as ViewGroup.MarginLayoutParams
        parameter.marginStart = 100
        activity?.toolbar!!.layoutParams = parameter
    }
}