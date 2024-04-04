package com.example.beachtest

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Written by Sebastian Tadeo - Group: Beach Eats
class IngredientsDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "IngredientsDatabase.db"
        private const val TABLE_NAME = "Ingredients"
        private const val COLUMN_ID = "id"
        private const val COLUMN_DISH_NAME = "dishName"
        private const val COLUMN_INGREDIENT = "ingredient"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableStatement = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DISH_NAME TEXT,
                $COLUMN_INGREDIENT TEXT
            )
        """.trimIndent()

        db.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addIngredient(dishName: String, ingredient: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_DISH_NAME, dishName)
            put(COLUMN_INGREDIENT, ingredient)
        }
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    fun getIngredientsForDish(dishName: String): List<String> {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_INGREDIENT),
            "$COLUMN_DISH_NAME = ?",
            arrayOf(dishName),
            null, null, null, null
        )

        val ingredients = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val ingredient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENT))
            ingredients.add(ingredient)
        }
        cursor.close()
        db.close()
        return ingredients
    }
}