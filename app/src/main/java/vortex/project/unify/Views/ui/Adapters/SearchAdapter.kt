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
import vortex.project.unify.Views.Model.Pet

class SearchAdapter (var petsList: List<Pet?> = listOf(),
                     private val listener: SearchAdapter.OnItemClickListener)
    : RecyclerView.Adapter<SearchAdapter.Viewholder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_card, parent, false)
        val viewholder = Viewholder(view)
        return viewholder
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        if (petsList[position]!!.pet_photo.isNotEmpty()) {
            holder.photoSearch.setImageBitmap(handleBitmap(petsList[position]!!.pet_photo))
        }
        holder.userName.text = petsList[position]!!.pet_name
        holder.speciesSearch.text = petsList[position]!!.pet_specie

    }

    override fun getItemCount(): Int {
        return petsList.size
    }

    inner class Viewholder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val userName: TextView = itemView.findViewById(R.id.tv_username_search)
        val speciesSearch: TextView = itemView.findViewById(R.id.tv_species_search)
        val photoSearch: ImageView = itemView.findViewById(R.id.img_user_search)

        init {
            itemView.setOnClickListener(this)
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

    fun changeData(pets: List<Pet>){
        petsList = pets
        notifyDataSetChanged()
    }

    private fun handleBitmap(photo: String): Bitmap? {
        val byteArray: ByteArray = Base64.decode(photo, Base64.DEFAULT)
        val bmImage = BitmapFactory.decodeByteArray(
            byteArray, 0,
            byteArray.size
        )
        return bmImage
    }
}