package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.beachtest.databinding.FragmentUserMealsBinding
import android.widget.TextView
import android.widget.Button



// Define a data class for Meal
data class Meal(
    val date: String = "",
    val mealDescription: String = "",
    val mealType: String = "",
    val time: String = "",
    val id: String = ""
)

class UserMealsFragment : Fragment() {

    private var _binding: FragmentUserMealsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mealAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadUserMeals()
    }

    private fun setupRecyclerView() {
        mealAdapter = MealAdapter(emptyList()) { mealId ->
            deleteMeal(mealId)
        }
        binding.mealsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mealAdapter
        }
    }

    private fun loadUserMeals() {
        if (!isUserLoggedIn()) {
            Toast.makeText(context, "Please log in to view your scheduled meals.", Toast.LENGTH_LONG).show()
            // Handle navigation to login page if necessary
            return
        }

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseFirestore.getInstance()
        db.collection("scheduledMeals")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                val mealsList = documents.map { document ->
                    document.toObject(Meal::class.java).copy(id = document.id)
                }
                mealAdapter.updateMeals(mealsList)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error getting documents: $exception", Toast.LENGTH_LONG).show()
            }
    }

    private fun deleteMeal(mealId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("scheduledMeals").document(mealId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Meal deleted successfully", Toast.LENGTH_SHORT).show()
                loadUserMeals() // Refresh the meal list
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error deleting meal: $e", Toast.LENGTH_LONG).show()
            }
    }

    private fun isUserLoggedIn(): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        return user != null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class MealAdapter(private var meals: List<Meal>, private val deleteMealCallback: (String) -> Unit)
    : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewMealDescription: TextView = itemView.findViewById(R.id.textViewMealDescription)
        val textViewMealDate: TextView = itemView.findViewById(R.id.textViewMealDate)
        val textViewMealTime: TextView = itemView.findViewById(R.id.textViewMealTime)
        val textViewMealType: TextView = itemView.findViewById(R.id.textViewMealType)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meal_list_item, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        with(holder) {
            textViewMealDescription.text = meal.mealDescription
            textViewMealDate.text = meal.date
            textViewMealTime.text = meal.time
            textViewMealType.text = meal.mealType
            deleteButton.setOnClickListener { deleteMealCallback(meal.id) }
        }
    }

    override fun getItemCount() = meals.size

    fun updateMeals(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }
}
