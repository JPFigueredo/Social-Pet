package vortex.project.unify.Views.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vortex.project.unify.R
import vortex.project.unify.Views.Classes.Post
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PostsAdapter (var postsList: List<Post?> = listOf(), private val listener: OnItemClickListener): RecyclerView.Adapter<PostsAdapter.Viewholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_card, parent, false)
        val viewholder = Viewholder(view)
        return viewholder
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

//        var date = postsList[position]!!.datePost
////        var newDate = date as Date
//        var dateFormat = DateFormat.getDateTimeInstance().format(newDate)
//        var dateString = dateFormat.toString()

//        holder.petPhotoPost.image = postsList[position]!!.petPhotoPost
//        holder.postPhoto.image = postsList[position]!!.photoPost
        holder.postPetName.text = postsList[position]!!.petNamePost
        holder.postDate.text = postsList[position]!!.datePost
        holder.postLikes.text = postsList[position]!!.likesPost

    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    inner class Viewholder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val petPhotoPost: ImageView = itemView.findViewById(R.id.img_pet)
        val postPhoto: ImageView = itemView.findViewById(R.id.img_post)
        val postPetName: TextView = itemView.findViewById(R.id.tv_petname_card)
        val postDate: TextView = itemView.findViewById(R.id.tv_datePost_card)
        val postLikes: TextView = itemView.findViewById(R.id.tv_num_likes)
//        val postDescription: TextView = itemView.findViewById(R.id.)
        val postLikeAddComments: ImageView = itemView.findViewById(R.id.img_like)

        init {
            postLikeAddComments.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun changeData(posts: List<Post>){
        postsList = posts.reversed()
        notifyDataSetChanged()
    }
//    fun convertLongToTime(time: Long): String {
//        val date = Date(time)
//        val format = SimpleDateFormat("dd.MM..yyyy HH:mm")
//        return format.format(date)
//    }
}