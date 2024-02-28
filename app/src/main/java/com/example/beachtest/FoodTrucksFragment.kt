import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class FoodTrucksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food_trucks, container, false)

        view.findViewById<Button>(R.id.foodTrucksButton).setOnClickListener {
            val action = FoodTrucksFragmentDirections.actionFoodTrucksFragmentToVendorDetailsFragment("Food Trucks")
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.farmersMarketButton).setOnClickListener {
            val action = FoodTrucksFragmentDirections.actionFoodTrucksFragmentToVendorDetailsFragment("Farmers Market Vendors")
            findNavController().navigate(action)
        }

        return view
    }
}
