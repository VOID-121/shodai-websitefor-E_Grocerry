package com.example.shodai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shodai.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart.productsRecyclerView
import kotlinx.android.synthetic.main.activity_cart.progressBar2
import kotlinx.android.synthetic.main.activity_yrproducts.*
import kotlinx.android.synthetic.main.activity_yrproducts.view.*

class yrproducts : AppCompatActivity() {
    //private lateinit var database: DatabaseReference
    private val uid = FirebaseAuth.getInstance().uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yrproducts)
        progressBaR?.visibility = View.GONE
        val query : Query = FirebaseDatabase.getInstance().getReference("user-products/$uid")
        val options: FirebaseRecyclerOptions<Product> = FirebaseRecyclerOptions.Builder<Product>()
            .setQuery(query, Product::class.java)
            .build()
        val ad = CartAdapter(options)
        ad.startListening()
        productsR.apply {
            layoutManager = GridLayoutManager(this@yrproducts,1, RecyclerView.VERTICAL,false)
            adapter = ad

        }
    }

}