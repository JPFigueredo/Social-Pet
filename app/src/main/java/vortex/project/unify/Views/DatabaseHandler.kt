package vortex.project.unify.Views

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//class DatabaseHandler(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
//
//    companion object {
//        private const val DATABASE_VERSION = 1
//        private const val DATABASE_NAME = "RestaurantDatabase"
//        private const val TABLE_RESTAURANTS = "RestaurantsTable"
//
//        //        private const val KEY_ID = "_id"
//        private const val KEY_USER_ID = "userIDRanking"
//        private const val KEY_RESTAURANTS_NAME = "restaurantName"
//        private const val KEY_RESTAURANTADDRESS = "restaurantAddress"
//        private const val ANSWER_01 = "answer01"
//        private const val ANSWER_02 = "answer02"
//        private const val ANSWER_03 = "answer03"
//        private const val ANSWER_04 = "answer04"
//        private const val ANSWER_05 = "answer05"
//        private const val ANSWER_06 = "answer06"
//    }
//
//    override fun onCreate(db: SQLiteDatabase?) {
//        val CREATE_RESTAURANTS_TABLE = (
//                "CREATE TABLE " + TABLE_RESTAURANTS
//                        + "("
//
////                + KEY_ID + " INTERGER PRIMARY KEY,"
//                        + KEY_USER_ID + " TEXT,"
//                        + KEY_RESTAURANTS_NAME + " TEXT,"
//                        + KEY_RESTAURANTADDRESS + " TEXT,"
//                        + ANSWER_01 + " TEXT,"
//                        + ANSWER_02 + " TEXT,"
//                        + ANSWER_03 + " TEXT,"
//                        + ANSWER_04 + " TEXT,"
//                        + ANSWER_05 + " TEXT,"
//                        + ANSWER_06 + " TEXT"
//
//                        + ")"
//                )
//        db?.execSQL(CREATE_RESTAURANTS_TABLE)
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS)
//        onCreate(db)
//    }
//
//    fun addRestaurant(restaurantClass : Restaurant): Long {
//        val db = this.writableDatabase
//
//        val contentValues = ContentValues()
//        contentValues.put(KEY_USER_ID, restaurantClass.userIDRanking)
//        contentValues.put(KEY_RESTAURANTS_NAME, restaurantClass.restaurantName)
//        contentValues.put(KEY_RESTAURANTADDRESS, restaurantClass.restaurantAddress)
//        contentValues.put(ANSWER_01, restaurantClass.goodRestaurantCleanliness)
//        contentValues.put(ANSWER_02, restaurantClass.goodRestaurantOrganization)
//        contentValues.put(ANSWER_03, restaurantClass.goodRestaurantPrice)
//        contentValues.put(ANSWER_04, restaurantClass.goodRestaurantStaff)
//        contentValues.put(ANSWER_05, restaurantClass.goodFoodTasty)
//        contentValues.put(ANSWER_06, restaurantClass.goodFoodQuantity)
//
//        val success = db.insert(TABLE_RESTAURANTS, null, contentValues)
//
//        db.close()
//        return success
//    }
//
//    fun viewRestaurants() : ArrayList<Restaurant> {
//        val restaurantList: ArrayList<Restaurant> = ArrayList<Restaurant>()
//
//        val selectQuery = "SELECT * FROM $TABLE_RESTAURANTS"
//
//        val db = this.readableDatabase
//        var cursor: Cursor? = null
//
//        try {
//            cursor = db.rawQuery(selectQuery, null)
//        } catch (e: SQLException) {
//            db.execSQL(selectQuery)
//            return ArrayList()
//        }
//
////        var id: Int
//        var userIDRanking: ByteArray
//        var restaurantName: String
//        var restaurantAddress: ByteArray
//        var answer01: String
//        var answer02: String
//        var answer03: String
//        var answer04: String
//        var answer05: String
//        var answer06: String
//
//        if (cursor.moveToFirst()) {
//            do {
////                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
//                userIDRanking = cursor.getBlob(cursor.getColumnIndex(KEY_USER_ID))
//                restaurantName = cursor.getString(cursor.getColumnIndex(KEY_RESTAURANTS_NAME))
//                restaurantAddress = cursor.getBlob(cursor.getColumnIndex(KEY_RESTAURANTADDRESS))
//                answer01 = cursor.getString(cursor.getColumnIndex(ANSWER_01))
//                answer02 = cursor.getString(cursor.getColumnIndex(ANSWER_02))
//                answer03 = cursor.getString(cursor.getColumnIndex(ANSWER_03))
//                answer04 = cursor.getString(cursor.getColumnIndex(ANSWER_04))
//                answer05 = cursor.getString(cursor.getColumnIndex(ANSWER_05))
//                answer06 = cursor.getString(cursor.getColumnIndex(ANSWER_06))
//
//                val restaurantFromSQLDatabase = Restaurant(userIDRanking,restaurantName, restaurantAddress, answer01, answer02, answer03, answer04, answer05, answer06)
//                restaurantList.add(restaurantFromSQLDatabase)
//            } while (cursor.moveToNext())
//        }
//        return restaurantList
//    }
//}