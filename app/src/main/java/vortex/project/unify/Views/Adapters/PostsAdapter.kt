package vortex.project.unify.Views.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vortex.project.unify.R
import vortex.project.unify.Views.Classes.Post

class PostsAdapter (var postList: List<Post?> = listOf()): RecyclerView.Adapter<PostsAdapter.Viewholder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return Viewholder(LayoutInflater.from(parent.context).inflate(R.layout.post_card, parent, false))
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

//        holder.photoPost = postList[position]!!.photoPost
        holder.petNamePost.text = postList[position]!!.petNamePost
        holder.datePost.text = postList[position]!!.datePost
        holder.likesPost.text = postList[position]!!.likesPost
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    class Viewholder(itemView: View): RecyclerView.ViewHolder(itemView){

        val photoPost: TextView = itemView.findViewById(R.id.img_post)
        val petNamePost: TextView = itemView.findViewById(R.id.tv_petname_card)
        val datePost: TextView = itemView.findViewById(R.id.tv_datePost_card)
        val likesPost: TextView = itemView.findViewById(R.id.tv_num_likes)
        var descriptionsPost: String = ""
        var locationPost: String = "0000.0000"

    }

    fun changeData(restaurants: List<Post>){
        postList = restaurants
        notifyDataSetChanged()
    }
}