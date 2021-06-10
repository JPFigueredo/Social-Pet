package vortex.project.unify.Views.Classes

import org.junit.Before
import org.junit.Assert.*
import org.junit.Test

class PetTest {

    var pet: Pet? = Pet(
        "Gertrude",
        "Goat",
        "Female",
        50,
        100,
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
    fun verifyIfAcceptsZero() {
        try {
            pet!!.pet_posts = 0
        } catch (e: Throwable){
            assertTrue(e is Exception)
        }
    }

    @Test
    fun verifyIfFollowersAcceptsLessThanZero(){
        try {
            pet!!.pet_followers = -4
        } catch (e: Throwable) {
            assertTrue(e is Exception)
        }
    }
    @Test
    fun verifyIfPostsAcceptsLessThanZero(){
        try {
            pet!!.pet_posts = -4
        } catch (e: Throwable) {
            assertTrue(e is Exception)
        }
    }
    @Test
    fun verifyIfAcceptsNameWithLessThanTwoStrings(){
        try {
            pet!!.pet_name = "Zé"
        } catch (e: Throwable){
            assertTrue(e is Exception)
        }
    }

    @Test
    fun verifyIfItAcceptsOtherGenders(){
        try {
            pet!!.pet_gender = "Male"
        } catch (e: Throwable) {
            assertTrue(e is Exception)
        }
    }

    @Test
    fun verifyIfItAcceptsOtherSpecies(){
        try {
            pet = Pet("Gertrude","Duck", "Male")
        } catch (e: Throwable) {
            assertTrue(e is Exception)
        }
    }
}