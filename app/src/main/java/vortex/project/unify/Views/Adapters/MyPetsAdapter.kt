package vortex.project.unify.Views.Adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_add_pet.*
import kotlinx.android.synthetic.main.fragment_profile.*
import vortex.project.unify.R
import vortex.project.unify.Views.Classes.Pet


class MyPetsAdapter (var petsList: List<Pet?> = listOf(), private val listener: OnItemClickListener ): RecyclerView.Adapter<MyPetsAdapter.Viewholder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_pets_card, parent, false)
        val viewholder = Viewholder(view)
        return viewholder
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        if (petsList[position]!!.pet_photo.isNotEmpty()) {
            holder.petPhotoCard.setImageBitmap(handleBitmap(petsList[position]!!.pet_photo))
        }

        holder.petNameCard.text = petsList[position]!!.pet_name
        holder.petSpecieCard.text = petsList[position]!!.pet_specie
        holder.petGenderCard.text = petsList[position]!!.pet_gender
    }

    override fun getItemCount(): Int {
        return petsList.size
    }

    inner class Viewholder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val petPhotoCard: ImageView = itemView.findViewById(R.id.tv_pet_photo_profile)
        val petNameCard: TextView = itemView.findViewById(R.id.tv_name_pet_card)
        val petSpecieCard: TextView = itemView.findViewById(R.id.tv_species_card)
        val petGenderCard: TextView = itemView.findViewById(R.id.tv_gender_card)
        val selectPet: FloatingActionButton = itemView.findViewById(R.id.select_pet_Button)

        init {
            selectPet.setOnClickListener(this)
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