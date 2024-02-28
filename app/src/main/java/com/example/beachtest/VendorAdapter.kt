class VendorAdapter(private val vendorsList: List<Vendor>) : RecyclerView.Adapter<VendorAdapter.VendorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vendor, parent, false)
        return VendorViewHolder(view)
    }

    override fun onBindViewHolder(holder: VendorViewHolder, position: Int) {
        val currentVendor = vendorsList[position]
        // Bind data to your views
        holder.brandNameTextView.text = currentVendor.brandName
        holder.logoImageView.setImageResource(currentVendor.logoResId)
        holder.locationTextView.text = currentVendor.location
        holder.schedulePricesTextView.text = currentVendor.schedulePrices
    }

    override fun getItemCount() = vendorsList.size

    class VendorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandNameTextView: TextView = itemView.findViewById(R.id.vendorBrandName) // Replace with your actual TextView ID
        val logoImageView: ImageView = itemView.findViewById(R.id.vendorLogo) // Replace with your actual ImageView ID
        val locationTextView: TextView = itemView.findViewById(R.id.vendorLocation) // Replace with your actual TextView ID
        val schedulePricesTextView: TextView = itemView.findViewById(R.id.vendorSchedulePrices) // Replace with your actual TextView ID
    }
}
