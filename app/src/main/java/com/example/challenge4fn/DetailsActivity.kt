package com.example.challenge4fn

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.challenge4fn.databinding.ActivityDetailsBinding
import com.example.challenge4fn.items.CartItem
import com.example.challenge4fn.viewmodel.CartViewModel
import com.example.challenge4fn.viewmodel.CartViewModelFactory

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var googleMapsUrl: String
    private var quantity = 0 // Menyimpan nilai quantity
    private var pricePerItem = 0 // Harga per item
    private var totalPrice = 0 // Total harga
    private lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(
            this,
            CartViewModelFactory(application)
        )[CartViewModel::class.java]

        val bundle = intent.extras
        val name = bundle?.getString("name")
        val price = bundle?.getInt("price")
        val description = bundle?.getString("description")
        val imageRes = bundle?.getInt("imageRes")
        val restaurantAddress = bundle?.getString("restaurantAddress")
        googleMapsUrl = bundle!!.getString("googleMapsUrl", "")

        if (binding.btnAddToCart2.text == "Tambah Ke Keranjang - Rp.0"){
            binding.btnAddToCart2.text = "Tambah Ke Keranjang - Rp. $price"
        }

        pricePerItem = price!!

        imageRes?.let { updateUI(name, price.toString(), description, it, restaurantAddress) }


        binding.btnBack.setOnClickListener{
            finish()
        }

        // Tambahkan onClickListener untuk tombol tambah
        binding.btnIncrease2.setOnClickListener {
            quantity++
            updateQuantity(quantity)
        }

        // Tambahkan onClickListener untuk tombol kurang
        binding.btnDecrease2.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateQuantity(quantity)
            }
        }

         //Tambahkan onClickListener untuk tombol "Tambah Ke Keranjang"
        binding.btnAddToCart2.setOnClickListener {
            // Ambil data makanan untuk disimpan ke dalam keranjang
            val cartItem = CartItem(
                foodName = name.toString(),
                totalPrice = totalPrice,
                price = price,
                quantity = quantity,
                imageResourceId = imageRes!! // Gunakan image sebagai Integer
            )
            if (quantity > 0){
                // Simpan item ke dalam keranjang menggunakan CartItemDao
                viewModel.insertCartItem(cartItem)

                // Tampilkan pesan berhasil menambahkan ke keranjang
                Toast.makeText(this@DetailsActivity, "Item ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@DetailsActivity, "Jumlah item tidak boleh 0!", Toast.LENGTH_SHORT).show()

            }



        }

        binding.txtGoogleMaps.setOnClickListener {
            // Memeriksa apakah URL Google Maps tidak kosong
            if (googleMapsUrl.isNotEmpty()) {
                // Membuka tautan Google Maps di browser
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsUrl))
                startActivity(intent)
            }
        }
    }

    private fun updateQuantity(quantity: Int) {
        binding.txtItemQuantity2.text = quantity.toString()
        totalPrice = quantity * pricePerItem
        binding.btnAddToCart2.text = "Tambah Ke Keranjang - Rp. $totalPrice"

    }


    private fun updateUI(name: String?, price: String?, description: String?, imageRes: Int, restaurantAddress: String?) {
        binding.apply {
            imgFood.setImageResource(imageRes)
            txtFoodName.text = name
            txtFoodPrice.text = "Rp. $price"
            txtFoodDescription.text = description
            txtLocation.text = restaurantAddress
            txtGoogleMaps.text = googleMapsUrl
        }
    }
}