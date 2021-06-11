package vortex.project.unify.Views.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import vortex.project.unify.R
import vortex.project.unify.Views.Classes.Pet

class SearchAdapter (var petsList: List<Pet?> = listOf(),
                     private val listener: SearchAdapter.OnItemClickListener)
    : RecyclerView.Adapter<SearchAdapter.Viewholder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_card, parent, false)
        val viewholder = Viewholder(view)
        return viewholder
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

//        holder.petPhotoCard.image = petsList[position]!!.pet_photos

        holder.userName.text = petsList[position]!!.pet_name
        holder.speciesSearch.text = petsList[position]!!.pet_specie

    }

    override fun getItemCount(): Int {
        return petsList.size
    }

    inner class Viewholder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val userName: TextView = itemView.findViewById(R.id.tv_username_search)
        val speciesSearch: TextView = itemView.findViewById(R.id.tv_species_search)

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
}