package com.example.beachtest

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper {
    class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
            private const val DATABASE_VERSION = 1
            private const val DATABASE_NAME = "MenuCycle.db"
            private const val TABLE_NAME = "MenuCycle"
            private const val COLUMN_ID = "id"
            private const val COLUMN_DAY = "day"
            private const val COLUMN_MEAL_TIME = "mealTime"
            private const val COLUMN_DINING_HALL = "diningHall"
            private const val COLUMN_MENU_ITEM = "menuItem"
        }

        override fun onCreate(db: SQLiteDatabase) {
            val createTableStatement = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DAY TEXT,
                $COLUMN_MEAL_TIME TEXT,
                $COLUMN_DINING_HALL TEXT,
                $COLUMN_MENU_ITEM TEXT
            )
        """.trimIndent()

            db.execSQL(createTableStatement)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }

        fun populateInitialData() {
            // Define a data class for menu items
            data class MenuItem(val day: String, val mealTime: String, val diningHall: String, val menuItem: String)

            val menuItems = listOf(
                MenuItem("Monday", "Breakfast", "Beachside", "Breakfast Sandwich"),
                MenuItem("Monday", "Breakfast", "Beachside", "Chorizo Patties"),
                MenuItem("Monday", "Breakfast", "Beachside", "Homestyle Potatoes"),
                MenuItem("Monday", "Breakfast", "Hillside", "Pancakes"),
                MenuItem("Monday", "Breakfast", "Hillside", "Bacon"),
                MenuItem("Monday", "Breakfast", "Hillside", "Hash Browns"),
                MenuItem("Monday", "Breakfast", "Parkside", "Confetti Pancakes"),
                MenuItem("Monday", "Breakfast", "Parkside", "Kielbasa"),
                MenuItem("Monday", "Breakfast", "Parkside", "Cajun Potatoes"),
                MenuItem("Monday", "Lunch", "Beachside", "Italian Wedding Soup"),
                MenuItem("Monday", "Lunch", "Beachside", "Beef Stroganoff"),
                MenuItem("Monday", "Lunch", "Beachside", "Kung Pao Cauliflower"),
                MenuItem("Monday", "Lunch", "Beachside", "Club Sandwich"),
                MenuItem("Monday", "Lunch", "Beachside", "Pepperoni and Cheese Pizza"),
                MenuItem("Monday", "Lunch", "Hillside", "Cream of Celery Soup"),
                MenuItem("Monday", "Lunch", "Hillside", "Chicken Noodle Soup"),
                MenuItem("Monday", "Lunch", "Hillside", "Caesar Salad"),
                MenuItem("Monday", "Lunch", "Hillside", "Pesto Turkey and Provolone on Ciabatta"),
                MenuItem("Monday", "Lunch", "Hillside", "Crispy Chicken Taquitos"),
                MenuItem("Monday", "Lunch", "Hillside", "Crispy Potato Taco"),
                MenuItem("Monday", "Lunch", "Hillside", "Build Your Own Grain Bowl"),
                MenuItem("Monday", "Lunch", "Hillside", "French Toast Sandwich"),
                MenuItem("Monday", "Lunch", "Parkside", "Pork Pozole"),
                MenuItem("Monday", "Lunch", "Parkside", "Broccoli Cheddar Soup"),
                MenuItem("Monday", "Lunch", "Parkside", "Lemon Herb Pasta Salad"),
                MenuItem("Monday", "Lunch", "Parkside", "Cheese Ravioli w/Creamy Tomato Sauce"),
                MenuItem("Monday", "Lunch", "Parkside", "Chicken Tenders"),
                MenuItem("Monday", "Lunch", "Parkside", "Vegan Lasagna"),
                MenuItem("Monday", "Lunch", "Parkside", "Pepperoni and Jalapeno"),
                MenuItem("Monday", "Dinner", "Beachside", "Italian Wedding Soup"),
                MenuItem("Monday", "Dinner", "Beachside", "Build Your Own Salad"),
                MenuItem("Monday", "Dinner", "Beachside", "Chicken Birria"),
                MenuItem("Monday", "Dinner", "Beachside", "Gardein Chile Verde"),
                MenuItem("Monday", "Dinner", "Beachside", "Carne Asada Nachos"),
                MenuItem("Monday", "Dinner", "Beachside", "Pepperoni and Cheese Pizza"),
                MenuItem("Monday", "Dinner", "Hillside", "Cream of Celery Soup"),
                MenuItem("Monday", "Dinner", "Hillside", "Chicken Noodle Soup"),
                MenuItem("Monday", "Dinner", "Hillside", "Caesar Salad"),
                MenuItem("Monday", "Dinner", "Hillside", "Chicken and Waffles"),
                MenuItem("Monday", "Dinner", "Hillside", "Texas Style Pork Ribs"),
                MenuItem("Monday", "Dinner", "Hillside", "Kung Pao Gardein Chicken"),
                MenuItem("Monday", "Dinner", "Hillside", "Build Your Own Grain Bowl"),
                MenuItem("Monday", "Dinner", "Hillside", "French Toast Sandwich"),
                MenuItem("Monday", "Dinner", "Parkside", "Pork Pozole"),
                MenuItem("Monday", "Dinner", "Parkside", "Broccoli Cheddar Soup"),
                MenuItem("Monday", "Dinner", "Parkside", "Lemon Herb Pasta Salad"),
                MenuItem("Monday", "Dinner", "Parkside", "Hamburger Bar"),
                MenuItem("Monday", "Dinner", "Parkside", "Battered Cheese Curd Bites"),
                MenuItem("Monday", "Dinner", "Parkside", "Tahini Chickpea Bowl"),
                MenuItem("Monday", "Dinner", "Parkside", "Pepperoni and Jalapeno Pizza"),

                MenuItem("Tuesday", "Breakfast", "Beachside", "Bacon Fried Rice"),
                MenuItem("Tuesday", "Breakfast", "Beachside", "Kielbasa"),
                MenuItem("Tuesday", "Breakfast", "Beachside", "Hash Brown Patties"),
                MenuItem("Tuesday", "Breakfast", "Hillside", "Biscuit and Country Gravy"),
                MenuItem("Tuesday", "Breakfast", "Hillside", "Chicken and Apple Sausage"),
                MenuItem("Tuesday", "Breakfast", "Hillside", "Country Potatoes"),
                MenuItem("Tuesday", "Breakfast", "Parkside", "Soyrizo Chorizo, Egg, Potato and Cheese Breakfast Burrito"),
                MenuItem("Tuesday", "Breakfast", "Parkside", "Bacon"),
                MenuItem("Tuesday", "Breakfast", "Parkside", "Hash Browns"),
                MenuItem("Tuesday", "Lunch", "Beachside", "Chicken Noodle Soup"),
                MenuItem("Tuesday", "Lunch", "Beachside", "Build Your Own Salad"),
                MenuItem("Tuesday", "Lunch", "Beachside", "Fried Chicken"),
                MenuItem("Tuesday", "Lunch", "Beachside", "Manicotti w/Pesto Kale Sauce"),
                MenuItem("Tuesday", "Lunch", "Beachside", "Taco Tuesday"),
                MenuItem("Tuesday", "Lunch", "Beachside", "Pepperoni and Cheese Pizza"),
                MenuItem("Tuesday", "Lunch", "Hillside", "Minestrone Soup"),
                MenuItem("Tuesday", "Lunch", "Hillside", "Potato and Bacon Soup"),
                MenuItem("Tuesday", "Lunch", "Hillside", "Spicy Fruit Cup"),
                MenuItem("Tuesday", "Lunch", "Hillside", "Grilled Thai Chicken"),
                MenuItem("Tuesday", "Lunch", "Hillside", "Carnitas Sopes"),
                MenuItem("Tuesday", "Lunch", "Hillside", "Pasta w/Gardein Meat Sauce"),
                MenuItem("Tuesday", "Lunch", "Hillside", "Build Your Own Grain Bowl"),
                MenuItem("Tuesday", "Lunch", "Hillside", "Kung Pao Chicken"),
                MenuItem("Tuesday", "Lunch", "Parkside", "Miso Soup"),
                MenuItem("Tuesday", "Lunch", "Parkside", "Italian Wedding Soup"),
                MenuItem("Tuesday", "Lunch", "Parkside", "Kale and Quinoa Salad"),
                MenuItem("Tuesday", "Lunch", "Parkside", "Beef Taquitos"),
                MenuItem("Tuesday", "Lunch", "Parkside", "Pineapple Thai Chicken Fried Rice"),
                MenuItem("Tuesday", "Lunch", "Parkside", "Pepperoni and Jalapeno Pizza"),
                MenuItem("Tuesday", "Dinner", "Beachside", "Chicken Noodle Soup"),
                MenuItem("Tuesday", "Dinner", "Beachside", "Build Your Own Sald"),
                MenuItem("Tuesday", "Dinner", "Beachside", "Swedish Meatballs"),
                MenuItem("Tuesday", "Dinner", "Beachside", "Gardein Fish and Chips"),
                MenuItem("Tuesday", "Dinner", "Beachside", "Buffalo Chicken Wrap"),
                MenuItem("Tuesday", "Dinner", "Beachside", "Pepperoni and Cheese Pizza"),
                MenuItem("Tuesday", "Dinner", "Hillside", "Minestrone Soup"),
                MenuItem("Tuesday", "Dinner", "Hillside", "Potato and Bacon Soup"),
                MenuItem("Tuesday", "Dinner", "Hillside", "Spicy Fruit Cup"),
                MenuItem("Tuesday", "Dinner", "Hillside", "Pork Chile Verde"),
                MenuItem("Tuesday", "Dinner", "Hillside", "Roasted Harissa Chicken"),
                MenuItem("Tuesday", "Dinner", "Hillside", "Brown Rice and Quinoa w/Roasted Tomatoes"),
                MenuItem("Tuesday", "Dinner", "Hillside", "Build Your Own Grain Bowl"),
                MenuItem("Tuesday", "Dinner", "Hillside", "Kung Pao Chicken"),
                MenuItem("Tuesday", "Dinner", "Parkside", "Miso Soup"),
                MenuItem("Tuesday", "Dinner", "Parkside", "Italian Wedding Soup"),
                MenuItem("Tuesday", "Dinner", "Parkside", "Kale and Quinoa Salad"),
                MenuItem("Tuesday", "Dinner", "Parkside", "Pasta w/Alfredo Sauce"),
                MenuItem("Tuesday", "Dinner", "Parkside", "Beef and Broccoli Stir Fry"),
                MenuItem("Tuesday", "Dinner", "Parkside", "Gardein Enchilada Scaloppine"),
                MenuItem("Tuesday", "Dinner", "Parkside", "Pepperoni and Jalapeno Pizza"),

                MenuItem("Wednesday", "Breakfast", "Beachside", "Breakfast Burrito"),
                MenuItem("Wednesday", "Breakfast", "Beachside", "Pork Links"),
                MenuItem("Wednesday", "Breakfast", "Beachside", "Country Potatoes"),
                MenuItem("Wednesday", "Breakfast", "Hillside", "Chorizo and Egg Burrito"),
                MenuItem("Wednesday", "Breakfast", "Hillside", "Turkey Sausage Links"),
                MenuItem("Wednesday", "Breakfast", "Hillside", "Potato O'Brien"),
                MenuItem("Wednesday", "Breakfast", "Parkside", "French Toast"),
                MenuItem("Wednesday", "Breakfast", "Parkside", "Turkey Sausage"),
                MenuItem("Wednesday", "Breakfast", "Parkside", "Hash Brown Patties"),
                MenuItem("Wednesday", "Lunch", "Beachside", "Corn Chowder"),
                MenuItem("Wednesday", "Lunch", "Beachside", "Build Your Own Salad"),
                MenuItem("Wednesday", "Lunch", "Beachside", "Beef Flank Steak w/Chimichurri Sauce"),
                MenuItem("Wednesday", "Lunch", "Beachside", "Cauliflower Mac and Cheese"),
                MenuItem("Wednesday", "Lunch", "Beachside", "Dijon Pork Chop Sandwich"),
                MenuItem("Wednesday", "Lunch", "Beachside", "Pepperoni and Cheese Pizza"),
                MenuItem("Wednesday", "Lunch", "Hillside", "Corn Chowder"),
                MenuItem("Wednesday", "Lunch", "Hillside", "Pork Pozole Rojo"),
                MenuItem("Wednesday", "Lunch", "Hillside", "Greek Salad"),
                MenuItem("Wednesday", "Lunch", "Hillside", "Baked Ziti"),
                MenuItem("Wednesday", "Lunch", "Hillside", "Grilled Beef Flank Steak w/Chimichurri Sauce"),
                MenuItem("Wednesday", "Lunch", "Hillside", "Gardein Orange Chicken"),
                MenuItem("Wednesday", "Lunch", "Hillside", "Build Your Own Grain Bowl"),
                MenuItem("Wednesday", "Lunch", "Hillside", "Chicken Mulitas"),
                MenuItem("Wednesday", "Lunch", "Parkside", "Potato and Bacon Soup"),
                MenuItem("Wednesday", "Lunch", "Parkside", "Chicken Noodle Soup"),
                MenuItem("Wednesday", "Lunch", "Parkside", "Spinach Salad"),
                MenuItem("Wednesday", "Lunch", "Parkside", "Corn Dog"),
                MenuItem("Wednesday", "Lunch", "Parkside", "Fish Tacos"),
                MenuItem("Wednesday", "Lunch", "Parkside", "Mushroom Stroganoff w/Pasta"),
                MenuItem("Wednesday", "Lunch", "Parkside", "Pepperoni and Jalapeno Pizza"),
                MenuItem("Wednesday", "Dinner", "Beachside", "Corn Chowder Soup"),
                MenuItem("Wednesday", "Dinner", "Beachside", "Build Your Own Salad"),
                MenuItem("Wednesday", "Dinner", "Beachside", "Tandoori Chicken"),
                MenuItem("Wednesday", "Dinner", "Beachside", "Moroccan Lentil"),
                MenuItem("Wednesday", "Dinner", "Beachside", "Chili Cheese Tater Tots"),
                MenuItem("Wednesday", "Dinner", "Beachside", "Pepperoni and Cheese Pizza"),
                MenuItem("Wednesday", "Dinner", "Hillside", "Corn Chowder Soup"),
                MenuItem("Wednesday", "Dinner", "Hillside", "Pork Pozole Rojo"),
                MenuItem("Wednesday", "Dinner", "Hillside", "Greek Salad"),
                MenuItem("Wednesday", "Dinner", "Hillside", "BBQ Chicken Sandwich"),
                MenuItem("Wednesday", "Dinner", "Hillside", "Meat Lasagna"),
                MenuItem("Wednesday", "Dinner", "Hillside", "Gardein Chicken Tenders"),
                MenuItem("Wednesday", "Dinner", "Hillside", "Build Your Own Grain Bowl"),
                MenuItem("Wednesday", "Dinner", "Hillside", "Chicken Mulitas"),
                MenuItem("Wednesday", "Dinner", "Parkside", "Potato and Bacon Soup"),
                MenuItem("Wednesday", "Dinner", "Parkside", "Chicken Noodle Soup"),
                MenuItem("Wednesday", "Dinner", "Parkside", "Spinach Salad"),
                MenuItem("Wednesday", "Dinner", "Parkside", "BBQ Chicken"),
                MenuItem("Wednesday", "Dinner", "Parkside", "Baked Cheese Manicotti"),
                MenuItem("Wednesday", "Dinner", "Parkside", "Thai Curry Tofu"),
                MenuItem("Wednesday", "Dinner", "Parkside", "Pepperoni and Jalapeno Pizza"),

                MenuItem("Thursday", "Breakfast", "Beachside", "Egg, Spinach and Cheese Sandwich on a Croissant"),
                MenuItem("Thursday", "Breakfast", "Beachside", "Bacon"),
                MenuItem("Thursday", "Breakfast", "Beachside", "Hash Browns"),
                MenuItem("Thursday", "Breakfast", "Hillside", "French Toast"),
                MenuItem("Thursday", "Breakfast", "Hillside", "Sausage Patties"),
                MenuItem("Thursday", "Breakfast", "Hillside", "Hash Brown Patties"),
                MenuItem("Thursday", "Breakfast", "Parkside", "Waffle Sticks"),
                MenuItem("Thursday", "Breakfast", "Parkside", "Sausage Patties"),
                MenuItem("Thursday", "Breakfast", "Parkside", "Tater Tots"),
                MenuItem("Thursday", "Lunch", "Beachside", "Potato Bacon Soup"),
                MenuItem("Thursday", "Lunch", "Beachside", "Build Your Own Salad"),
                MenuItem("Thursday", "Lunch", "Beachside", "Chicken Bacon Alfredo Pasta"),
                MenuItem("Thursday", "Lunch", "Beachside", "Pasta w/Gardein Meatballs"),
                MenuItem("Thursday", "Lunch", "Beachside", "Loaded Tenders"),
                MenuItem("Thursday", "Lunch", "Beachside", "Pepperoni and Cheese Pizza"),
                MenuItem("Thursday", "Lunch", "Hillside", "Chicken and Rice Soup"),
                MenuItem("Thursday", "Lunch", "Hillside", "Tomato Basil Soup"),
                MenuItem("Thursday", "Lunch", "Hillside", "Apple Walnut Salad"),
                MenuItem("Thursday", "Lunch", "Hillside", "Pasta Primavera"),
                MenuItem("Thursday", "Lunch", "Hillside", "Bacon Cheeseburger"),
                MenuItem("Thursday", "Lunch", "Hillside", "Gardein Beef W/Black Pepper Sauce"),
                MenuItem("Thursday", "Lunch", "Hillside", "Build Your Own Grain Bowl"),
                MenuItem("Thursday", "Lunch", "Hillside", "Sweet and Spicy Pork"),
                MenuItem("Thursday", "Lunch", "Parkside", "Tomato Basil Soup"),
                MenuItem("Thursday", "Lunch", "Parkside", "Caldo de Res"),
                MenuItem("Thursday", "Lunch", "Parkside", "Egg Salad Sandwich"),
                MenuItem("Thursday", "Lunch", "Parkside", "Meatball Sub Sandwich"),
                MenuItem("Thursday", "Lunch", "Parkside", "Chicken Taquitos"),
                MenuItem("Thursday", "Lunch", "Parkside", "Plant-Based Orange Chicken"),
                MenuItem("Thursday", "Lunch", "Parkside", "Pepperoni and Jalapeno Pizza"),
                MenuItem("Thursday", "Dinner", "Beachside", "Potato Bacon Soup"),
                MenuItem("Thursday", "Dinner", "Beachside", "Build Your Own Salad"),
                MenuItem("Thursday", "Dinner", "Beachside", "Teriyaki Chicken"),
                MenuItem("Thursday", "Dinner", "Beachside", "Gardein Beef Teriayaki"),
                MenuItem("Thursday", "Dinner", "Beachside", "Gyro Night"),
                MenuItem("Thursday", "Dinner", "Beachside", "Pepperoni and Cheese Pizza"),
                MenuItem("Thursday", "Dinner", "Hillside", "Chicken and Rice Soup"),
                MenuItem("Thursday", "Dinner", "Hillside", "Tomato Basil Soup"),
                MenuItem("Thursday", "Dinner", "Hillside", "Apple Walnut Salad"),
                MenuItem("Thursday", "Dinner", "Hillside", "Teriyaki Chicken"),
                MenuItem("Thursday", "Dinner", "Hillside", "Loaded Grilled Cheese Sandwich"),
                MenuItem("Thursday", "Dinner", "Hillside", "Vegetable Egg Rolls"),
                MenuItem("Thursday", "Dinner", "Hillside", "Build Your Own Grain Bowl"),
                MenuItem("Thursday", "Dinner", "Hillside", "Sweet and Spicy Pork"),
                MenuItem("Thursday", "Dinner", "Parkside", "Tomato Basil Soup"),
                MenuItem("Thursday", "Dinner", "Parkside", "Caldo de Res"),
                MenuItem("Thursday", "Dinner", "Parkside", "Egg Salad Sandwich"),
                MenuItem("Thursday", "Dinner", "Parkside", "Grilled Thai Chicken"),
                MenuItem("Thursday", "Dinner", "Parkside", "Beef Lomo Saltado"),
                MenuItem("Thursday", "Dinner", "Parkside", "Pasta w/Vegan Meatballs"),
                MenuItem("Thursday", "Dinner", "Parkside", "Pepperoni and Jalapeno Pizza"),

                MenuItem("Friday", "Breakfast", "Beachside", "Chilaquiles Verdes"),
                MenuItem("Friday", "Breakfast", "Beachside", "Grilled Breakfast Steak"),
                MenuItem("Friday", "Breakfast", "Beachside", "Papas Ala Mexicana"),
                MenuItem("Friday", "Breakfast", "Hillside", "Sausage, Egg and Cheese on English Muffin Sandwich"),
                MenuItem("Friday", "Breakfast", "Hillside", "Bacon"),
                MenuItem("Friday", "Breakfast", "Hillside", "Sweet Potato Hash"),
                MenuItem("Friday", "Breakfast", "Parkside", "Turkey, Egg and Cheese Sandwich on Croissant"),
                MenuItem("Friday", "Breakfast", "Parkside", "Bacon"),
                MenuItem("Friday", "Breakfast", "Parkside", "Potatoes O'Brien"),
                MenuItem("Friday", "Lunch", "Beachside", "Clam Chowder"),
                MenuItem("Friday", "Lunch", "Beachside", "Build Your Own Salad"),
                MenuItem("Friday", "Lunch", "Beachside", "Creole Style Shrimp, Chicken and Sausage Gumbo"),
                MenuItem("Friday", "Lunch", "Beachside", "Edamame Fried Rice"),
                MenuItem("Friday", "Lunch", "Beachside", "Crispy Onion Burger"),
                MenuItem("Friday", "Lunch", "Beachside", "Pepperoni and Cheese Pizza"),
                MenuItem("Friday", "Lunch", "Hillside", "Clam Chowder"),
                MenuItem("Friday", "Lunch", "Hillside", "Vegetable Noodle Soup"),
                MenuItem("Friday", "Lunch", "Hillside", "RYO California Sushi"),
                MenuItem("Friday", "Lunch", "Hillside", "Salmon Florentine"),
                MenuItem("Friday", "Lunch", "Hillside", "Spicy Miso Pork Ramen"),
                MenuItem("Friday", "Lunch", "Hillside", "Edamame Fried Rice"),
                MenuItem("Friday", "Lunch", "Hillside", "Build Your Own Grain Bowl"),
                MenuItem("Friday", "Lunch", "Hillside", "Fruit Crepes"),
                MenuItem("Friday", "Lunch", "Parkside", "French Onion Soup"),
                MenuItem("Friday", "Lunch", "Parkside", "Clam Chowder"),
                MenuItem("Friday", "Lunch", "Parkside", "Chicken Caesar Salad w/Cilantro Dressing"),
                MenuItem("Friday", "Lunch", "Parkside", "Beef and Cheese Enchiladas"),
                MenuItem("Friday", "Lunch", "Parkside", "Cajun Fish"),
                MenuItem("Friday", "Lunch", "Parkside", "Spicy Korean Tofu Bowl"),
                MenuItem("Friday", "Lunch", "Parkside", "Pepperoni and Jalapeno Pizza"),
                MenuItem("Friday", "Dinner", "Beachside", "Clam Chowder"),
                MenuItem("Friday", "Dinner", "Beachside", "Build Your Own Salad"),
                MenuItem("Friday", "Dinner", "Beachside", "Baked Salmon w/Tikka Masala Sauce"),
                MenuItem("Friday", "Dinner", "Beachside", "Tofu and Eggplant Stir Fry"),
                MenuItem("Friday", "Dinner", "Beachside", "Crunchy Tacos"),
                MenuItem("Friday", "Dinner", "Beachside", "Pepperoni and Cheese Pizza"),
                MenuItem("Friday", "Dinner", "Hillside", "Clam Chowder"),
                MenuItem("Friday", "Dinner", "Hillside", "Vegetable Noodle Soup"),
                MenuItem("Friday", "Dinner", "Hillside", "RYO California Sushi"),
                MenuItem("Friday", "Dinner", "Hillside", "Beef Red Chile Colorado"),
                MenuItem("Friday", "Dinner", "Hillside", "Fish and Chips"),
                MenuItem("Friday", "Dinner", "Hillside", "Eggplant and Tofu Ricotta Rollatini"),
                MenuItem("Friday", "Dinner", "Hillside", "Build Your Own Grain Bowl"),
                MenuItem("Friday", "Dinner", "Hillside", "Fruit Crepes"),
                MenuItem("Friday", "Dinner", "Parkside", "French Onion Soup"),
                MenuItem("Friday", "Dinner", "Parkside", "Clam Chowder"),
                MenuItem("Friday", "Dinner", "Parkside", "Chicken Caesar Salad w/Cilantro Dressing"),
                MenuItem("Friday", "Dinner", "Parkside", "Mac and Cheese"),
                MenuItem("Friday", "Dinner", "Parkside", "Wasabi Ginger Salmon"),
                MenuItem("Friday", "Dinner", "Parkside", "Ratatouille"),
                MenuItem("Friday", "Dinner", "Parkside", "Pepperoni and Jalapeno Pizza"),

                MenuItem("Saturday", "Breakfast", "Beachside", "French Toast"),
                MenuItem("Saturday", "Breakfast", "Beachside", "Grilled Ham"),
                MenuItem("Saturday", "Breakfast", "Hillside", "French Toast Sticks"),
                MenuItem("Saturday", "Breakfast", "Hillside", "Canadian Bacon"),
                MenuItem("Saturday", "Lunch", "Beachside", "Tater Tots"),
                MenuItem("Saturday", "Lunch", "Beachside", "Tomato Basil Soup"),
                MenuItem("Saturday", "Lunch", "Beachside", "Build Your Own Salad"),
                MenuItem("Saturday", "Lunch", "Beachside", "Herb Chicken"),
                MenuItem("Saturday", "Lunch", "Beachside", "Gardein Beef Fajitas"),
                MenuItem("Saturday", "Lunch", "Beachside", "Pepperoni and Cheese Pizza"),
                MenuItem("Saturday", "Lunch", "Hillside", "Tater Tots"),
                MenuItem("Saturday", "Lunch", "Hillside", "Italian Wedding Soup"),
                MenuItem("Saturday", "Lunch", "Hillside", "Broccoli Cheddar Soup"),
                MenuItem("Saturday", "Lunch", "Hillside", "Yogurt Parfait Bar"),
                MenuItem("Saturday", "Lunch", "Hillside", "Build Your Own Crepe"),
                MenuItem("Saturday", "Dinner", "Beachside", "Tomato Basil Soup"),
                MenuItem("Saturday", "Dinner", "Beachside", "Build Your Own Salad"),
                MenuItem("Saturday", "Dinner", "Beachside", "Chicken Fried Steak"),
                MenuItem("Saturday", "Dinner", "Beachside", "Cheese Ravioli"),
                MenuItem("Saturday", "Dinner", "Beachside", "Spicy Wings and French Fries"),
                MenuItem("Saturday", "Dinner", "Beachside", "Pepperoni and Cheese Pizza"),
                MenuItem("Saturday", "Dinner", "Hillside", "Italian Wedding Soup"),
                MenuItem("Saturday", "Dinner", "Hillside", "Broccoli Cheddar Soup"),
                MenuItem("Saturday", "Dinner", "Hillside", "Churros"),
                MenuItem("Saturday", "Dinner", "Hillside", "Chicken and Cheese Enchiladas"),
                MenuItem("Saturday", "Dinner", "Hillside", "Chicken Pesto Alfredo Pasta"),
                MenuItem("Saturday", "Dinner", "Hillside", "Mozzarella Sticks"),

                MenuItem("Sunday", "Breakfast", "Beachside", "Chocolate Chip Pancakes"),
                MenuItem("Sunday", "Breakfast", "Beachside", "Sausage Patties"),
                MenuItem("Sunday", "Breakfast", "Beachside", "Hash Brown Patties"),
                MenuItem("Sunday", "Breakfast", "Parkside", "French Toast Sticks"),
                MenuItem("Sunday", "Breakfast", "Parkside", "Sausage Links"),
                MenuItem("Sunday", "Breakfast", "Parkside", "Hash Brown Patties"),
                MenuItem("Sunday", "Lunch", "Beachside", "Beef and Barley Soup"),
                MenuItem("Sunday", "Lunch", "Beachside", "Build Your Own Salad"),
                MenuItem("Sunday", "Lunch", "Beachside", "Roasted Pork Loin w/Caramelized Onion"),
                MenuItem("Sunday", "Lunch", "Beachside", "Pasta with Gardein Marinara Sauce"),
                MenuItem("Sunday", "Lunch", "Beachside", "Pepperoni and Cheese Pizza"),
                MenuItem("Sunday", "Lunch", "Parkside", "Beef and Rice Soup"),
                MenuItem("Sunday", "Lunch", "Parkside", "Fresh Juice of the Day"),
                MenuItem("Sunday", "Lunch", "Parkside", "Pepperoni and Cheese Pizza"),
                MenuItem("Sunday", "Dinner", "Beachside", "Beef and Barley Soup"),
                MenuItem("Sunday", "Dinner", "Beachside", "Build Your Own Salad"),
                MenuItem("Sunday", "Dinner", "Beachside", "Lemongrass Chicken"),
                MenuItem("Sunday", "Dinner", "Beachside", "Curry Chickpea Vegetables"),
                MenuItem("Sunday", "Dinner", "Beachside", "Ramen Bowl"),
                MenuItem("Sunday", "Dinner", "Beachside", "Pepperoni and Cheese Pizza"),
                MenuItem("Sunday", "Dinner", "Parkside", "Beef and Rice Soup"),
                MenuItem("Sunday", "Dinner", "Parkside", "Plum Sauce Chicken"),
                MenuItem("Sunday", "Dinner", "Parkside", "Pasta w/Marinara Sauce"),
                MenuItem("Sunday", "Dinner", "Parkside", "The Buddha Bowl"),
                MenuItem("Sunday", "Dinner", "Parkside", "Pepperoni and Cheese Pizza"),
            )

            // Insert each menu item into the database
            val dbWritable = this.writableDatabase
            menuItems.forEach { menuItem ->
                val values = ContentValues().apply {
                    put(COLUMN_DAY, menuItem.day)
                    put(COLUMN_MEAL_TIME, menuItem.mealTime)
                    put(COLUMN_DINING_HALL, menuItem.diningHall)
                    put(COLUMN_MENU_ITEM, menuItem.menuItem)
                }
                dbWritable.insert(TABLE_NAME, null, values)
            }
            dbWritable.close()
        }

        fun getMenuItems(day: String, mealTime: String, diningHall: String): List<String> {
            val db = this.readableDatabase
            val menuItems = mutableListOf<String>()
            val cursor = db.query(
                true,
                TABLE_NAME,
                arrayOf(COLUMN_MENU_ITEM),
                "$COLUMN_DAY = ? AND $COLUMN_MEAL_TIME = ? AND $COLUMN_DINING_HALL = ?",
                arrayOf(day, mealTime, diningHall),
                null, null, null, null
            )

            if (cursor.moveToFirst()) {
                do {
                    val menuItem = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_ITEM))
                    menuItems.add(menuItem)
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return menuItems.distinct()
        }
    }
}