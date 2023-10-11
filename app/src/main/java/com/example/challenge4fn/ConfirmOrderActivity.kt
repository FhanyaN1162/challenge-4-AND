package com.example.challenge4fn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge4fn.adapter.ConfirmOrderAdapter
import com.example.challenge4fn.databinding.ActivityConfirmOrderBinding
import com.example.challenge4fn.viewmodel.CartViewModel
import com.example.challenge4fn.viewmodel.CartViewModelFactory

class ConfirmOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmOrderBinding
    private lateinit var viewModel: CartViewModel
    private lateinit var confirmOrderAdapter: ConfirmOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmOrderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this, CartViewModelFactory(application)).get(
            CartViewModel::class.java)

        confirmOrderAdapter = ConfirmOrderAdapter()

        setupRecyclerView()
        observeCartItems()

        binding.btnBack2.setOnClickListener{
            finish()
        }

        binding.btnPlaceOrder.setOnClickListener {
            val deliveryMethod = if (binding.btnPickUp.isChecked) {
                binding.btnPickUp.text.toString()
            } else if (binding.btnDelivery.isChecked){
                binding.btnDelivery.text.toString()
            } else {
                ""
            }

            val paymentMethod = if (binding.btnCash.isChecked) {
                binding.btnCash.text.toString()
            } else if (binding.btnDigital.isChecked){
                binding.btnDigital.text.toString()
            }else{
                ""
            }

            val totalPrice = confirmOrderAdapter.calculateTotalPrice()
            val orderSummary = "Pesanan seharga: Rp. $totalPrice\n" +
                    "Metode Pengiriman: $deliveryMethod\n" +
                    "Metode Pembayaran: $paymentMethod"

            if (deliveryMethod.isEmpty() || paymentMethod.isEmpty()){
                Toast.makeText(this@ConfirmOrderActivity, "Metode masih ada yang kosong!", Toast.LENGTH_SHORT).show()
            }else{
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("Order Summary")
                    .setMessage(orderSummary)
                    .setPositiveButton("Berhasil") { _, _ ->
                        // Delete all items from the database
                        viewModel.deleteAllCartItems()
                        // Navigate back to the home page
                        startActivity(Intent(this, MainActivity::class.java))
                        finishAffinity() // Finish activity ini agar tidak bisa kembali dengan back button
                    }
                    .create()
                alertDialog.show()
            }



        }



    }
    private fun setupRecyclerView() {

        binding.recyclerView.apply {
            adapter = confirmOrderAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
    private fun observeCartItems() {
        viewModel.allCartItems.observe(this, Observer { cartItems ->
            confirmOrderAdapter.submitList(cartItems)
            val totalPrice = confirmOrderAdapter.calculateTotalPrice()
            binding.txtTotalPayment.text = "Total Price: Rp. $totalPrice"
        })

    }
}