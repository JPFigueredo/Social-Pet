package vortex.project.unify.Views.Model

import org.junit.Before
import org.junit.Assert.*
import org.junit.Test

class PetTest {

    var pet: Pet? = Pet(
        "",
        "",
        "",
        0,
        0,
        "",
        "")

    @Before
    fun setUpPet() {
        pet = Pet(
            "Gertrude",
            "Goat",
            "Female",
            50,
            100,
            "Agadir Ida-Outanane, Marrocos",
            "encurtador.com.br/zOX12")
    }

    @Test
    fun verifyPetStrings(){
        assertEquals("Gertrude", pet!!.pet_name)
        assertEquals("Goat", pet!!.pet_specie)
        assertEquals("Female", pet!!.pet_gender)
        assertTrue(pet is Pet)
    }

    @Test
    fun verifyIfAcceptsNegative() {
        val pet = pet
        if (pet != null) {
            pet.pet_posts = -1
        }

        if (pet != null) {
            if (pet.pet_posts < 0){
                assertTrue(true)
            }else{
                assertTrue(false)
            }
        }
    }

    @Test
    fun verifyIfFollowersAcceptsLessThanZero(){
        pet!!.pet_followers = -4
        if(pet!!.pet_followers < 0){
            assertTrue(true)
        }else{
            assertTrue(false)
        }
    }

    @Test
    fun verifyIfPostsAcceptsLessThanZero(){
        pet!!.pet_posts = -4
        if(pet!!.pet_posts < 0){
            assertTrue(true)
        }else{
            assertTrue(false)
        }
    }

    @Test
    fun verifyIfAcceptsNameWithLessThanTwoStrings(){
        pet!!.pet_name = "Zé"
        if(pet!!.pet_name.length <= 2){
            assertTrue(true)
        }else{
            assertTrue(false)
        }
    }
    @Test
    fun verifyIfItAcceptsOtherGenders(){
            pet!!.pet_gender = "Hermaphrodite"
        if(pet!!.pet_gender != "Male" || pet!!.pet_gender != "Female"){
            assertTrue(true)
        }else{
            assertTrue(false)
        }

    }

    @Test
    fun verifyIfItAcceptsOtherSpecies(){
        if(pet!!.pet_specie != "Dog" || pet!!.pet_specie != "Cat"){
            assertTrue(true)
        }else{
            assertTrue(false)
        }
    }

}