package com.example.challenge4fn.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge4fn.R
import com.example.challenge4fn.adapter.MenuAdapter
import com.example.challenge4fn.databinding.FragmentHomeBinding
import com.example.challenge4fn.items.MenuItem


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var sharedPrefs: SharedPreferences
    private val PREF_NAME = "MyPrefs"
    private val IS_GRID_LAYOUT_KEY = "isGridLayout"
    private lateinit var layoutManagerGrid: GridLayoutManager
    private lateinit var layoutManagerLinear: LinearLayoutManager
    private lateinit var currentLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupMenu()

        sharedPrefs = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)




        // Inisialisasi LayoutManager untuk Grid dan Linear
        layoutManagerGrid = GridLayoutManager(requireContext(), 2)
        layoutManagerLinear = LinearLayoutManager(requireContext())

        // Inisialisasi LayoutManager saat aplikasi pertama kali dibuka
        val savedLayout = sharedPrefs.getString("layout", "grid") // "grid" adalah nilai default jika tidak ada yang tersimpan

        if (savedLayout == "grid") {
            currentLayoutManager = layoutManagerGrid
            binding.changeLayout.setImageResource(R.drawable.categories)
        } else {
            currentLayoutManager = layoutManagerLinear
            binding.changeLayout.setImageResource(R.drawable.list)
        }

        binding.recyclerViewMenuGrid.layoutManager = currentLayoutManager


        binding.changeLayout.setOnClickListener {
            // Mengubah tata letak saat tombol diklik

            if (currentLayoutManager == layoutManagerGrid) {
                currentLayoutManager = layoutManagerLinear
                binding.changeLayout.setImageResource(
                    R.drawable.list
                )
                sharedPrefs.edit().putString("layout", "linear").apply()

            } else {
                currentLayoutManager = layoutManagerGrid
                binding.changeLayout.setImageResource(
                    R.drawable.categories
                )
                sharedPrefs.edit().putString("layout", "grid").apply()
            }
            binding.recyclerViewMenuGrid.layoutManager = currentLayoutManager

        }


        return binding.root
    }
    private fun setupMenu() {
        val menuItems = getMenuItems()

        adapter = MenuAdapter(menuItems) { selectedItem ->
            val bundle = Bundle()
            bundle.putString("name", selectedItem.name)
            bundle.putInt("price", selectedItem.price)
            bundle.putString("description", selectedItem.description)
            bundle.putInt("imageRes", selectedItem.imageRes)
            bundle.putString("restaurantAddress", selectedItem.restaurantAddress)
            bundle.putString("googleMapsUrl", selectedItem.googleMapsUrl)

            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.detailsActivity, bundle)
        }
        binding.recyclerViewMenuGrid.adapter = adapter

    }

    private fun saveLayoutPreference(isGrid: Boolean) {
        val editor = sharedPrefs.edit()
        editor.putBoolean(IS_GRID_LAYOUT_KEY, isGrid)
        editor.apply()
    }

    private fun getMenuItems(): List<MenuItem> {
        val menuItems = mutableListOf<MenuItem>()
        menuItems.add(MenuItem(
            "Nasi Goreng",
            20000,
            "Nasi goreng spesial dengan telur dan ayam",
            R.drawable.nasi_goreng,
            "Kitchen Yard Mercure BSD City",
            "https://maps.app.goo.gl/Kd1hbopN2DhnY4DQ9"
        ))

        menuItems.add(MenuItem(
            "Mie Goreng",
            18000,
            "Mie goreng lezat dengan udang dan sayuran",
            R.drawable.mie_goreng,
            "Jittlada",
            "https://maps.app.goo.gl/2yxKvmefahrrB9u19"
        ))

        menuItems.add(MenuItem(
            "Ayam Goreng",
            25000,
            "Ayam goreng yang gurih dan nikmat",
            R.drawable.ayam_goreng,
            "Butler's Steak",
            "https://maps.app.goo.gl/eQdfDWJbbu2GFCMm9"
        ))

        menuItems.add(MenuItem(
            "Sate Ayam",
            15000,
            "Sate ayam dengan bumbu kacang yang khas",
            R.drawable.sate_ayam,
            "Gubug Makan Mang Engking BSD",
            "https://maps.app.goo.gl/5pN2U8kR9y3QDjBE6"
        ))

        menuItems.add(MenuItem(
            "Burger Cheese",
            35000,
            "Burger dengan keju leleh yang lezat",
            R.drawable.burger_cheese,
            "Sinar Djaya",
            "https://maps.app.goo.gl/vNGTFJRV1FFeVko1A"
        ))

        menuItems.add(MenuItem(
            "Sushi Roll (8 potong)",
            40000,
            "Sushi roll dengan berbagai pilihan rasa",
            R.drawable.sushi_roll,
            "Katsukita, The Breeze BSD",
            "https://maps.app.goo.gl/cMSAqxLjtVUTNP2Z9"
        ))

        menuItems.add(MenuItem(
            "Kopi Hitam",
            10000,
            "Kopi hitam segar tanpa gula",
            R.drawable.kopi_hitam,
            "Fire Prawn resto",
            "https://maps.app.goo.gl/8VeZmmZgeRdm68xy5"
        ))

        menuItems.add(MenuItem(
            "Jus Jeruk Segar",
            15000,
            "Jus jeruk segar tanpa tambahan gula",
            R.drawable.jus_jeruk_segar,
            "Sate Khas Senayan",
            "https://maps.app.goo.gl/okiH6dwwbig3YZmJ6"
        ))

        menuItems.add(MenuItem(
            "Air Mineral",
            3000,
            "Air mineral dalam kemasan botol",
            R.drawable.air_mineral,
            "Sushi Tei",
            "https://maps.app.goo.gl/Cx7sxvzhxWFid449A"
        ))

        menuItems.add(MenuItem(
            "Teh Manis Dingin",
            5000,
            "Teh manis dingin dengan es batu",
            R.drawable.teh_manis_dingin,
            "Ootoya",
            "https://maps.app.goo.gl/zbizStdmKQD1w9z67"
        ))

        return menuItems
    }

}
