package vortex.project.unify.Views.ui.Activity.Adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vortex.project.unify.R
import vortex.project.unify.Views.Model.Post

class ProfileAdapter (var postsList: List<Post?> = listOf(), private val listener: OnItemClickListener)
    : RecyclerView.Adapter<ProfileAdapter.Viewholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_card, parent, false)
        val viewholder = Viewholder(view)
        return viewholder
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

//        var date = postsList[position]!!.datePost
////        var newDate = date as Date
//        var dateFormat = DateFormat.getDateTimeInstance().format(newDate)
//        var dateString = dateFormat.toString()

//        holder.petPhotoPost.setImageBitmap(handleBitmap(postsList[position]!!.petPhotoPost))

        if (postsList[position]!!.photoPost.isNotEmpty()) {
            holder.postPhoto.setImageBitmap(handleBitmap(postsList[position]!!.photoPost))
        }

        holder.postPetName.text = postsList[position]!!.petNamePost
        holder.postDate.text = postsList[position]!!.datePost
        holder.postLikes.text = postsList[position]!!.likesPost
        holder.postDescription.text = postsList[position]!!.descriptionsPost

    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    inner class Viewholder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        //val petPhotoPost: ImageView = itemView.findViewById(R.id.img_pet)
        val postPhoto: ImageView = itemView.findViewById(R.id.img_post_profile)
        val postPetName: TextView = itemView.findViewById(R.id.tv_username_profile_card)
        val postDate: TextView = itemView.findViewById(R.id.tv_datePost_profile)
        val postLikes: TextView = itemView.findViewById(R.id.tv_num_likes)
        val postDescription: TextView = itemView.findViewById(R.id.tv_description_post_profile)
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

    private fun handleBitmap(photo: String): Bitmap? {
        val byteArray: ByteArray = Base64.decode(photo, Base64.DEFAULT)
        val bmImage = BitmapFactory.decodeByteArray(
            byteArray, 0,
            byteArray.size
        )
        return bmImage
    }

}