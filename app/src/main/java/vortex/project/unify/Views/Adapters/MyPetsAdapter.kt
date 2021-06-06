package vortex.project.unify.Views.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_add_pet.*
import kotlinx.android.synthetic.main.fragment_profile.*
import vortex.project.unify.R
import vortex.project.unify.Views.Classes.Pet
import kotlin.coroutines.coroutineContext


class MyPetsAdapter (var petsList: List<Pet?> = listOf()): RecyclerView.Adapter<MyPetsAdapter.Viewholder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_pets_card, parent, false)
        val viewholder = Viewholder(view)
//        view.setOnClickListener(onClick)
        return viewholder
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

//        holder.petPhotoCard.image = petsList[position]!!.goodRestaurantStaff
        holder.petNameCard.text = petsList[position]!!.pet_name
        holder.petSpecieCard.text = petsList[position]!!.pet_specie
        holder.petGenderCard.text = petsList[position]!!.pet_gender

//        holder.selectPet.setOnClickListener {
//            tv_pets_name_profile.text =
//            tv_bio.text =
//
//        }
    }

    override fun getItemCount(): Int {
        return petsList.size
    }

    class Viewholder(itemView: View): RecyclerView.ViewHolder(itemView){

        val petPhotoCard: ImageView = itemView.findViewById(R.id.iv_pet_photo_profile)
        val petNameCard: TextView = itemView.findViewById(R.id.tv_name_pet_card)
        val petSpecieCard: TextView = itemView.findViewById(R.id.tv_species_card)
        val petGenderCard: TextView = itemView.findViewById(R.id.tv_gender_card)
        val selectPet: FloatingActionButton = itemView.findViewById(R.id.select_pet_Button)
    }

    fun changeData(pets: List<Pet>){
        petsList = pets
        notifyDataSetChanged()
    }
    fun onClick(view: View?) {
//        Toast.makeText(contex, "Click", Toast.LENGTH_LONG).show()
    }
}