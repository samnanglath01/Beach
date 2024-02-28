package com.example.beachtest

// Data class representing a vendor at the beach.
data class Vendor(
    val logo: Int, // Resource ID for the logo
    val name: String, // Name of the vendor
    val location: String, // Location of the vendor's stall
    val schedulePrices: String // Schedule and prices offered by the vendor
)
