package com.example.shodai

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_cart.*
import org.json.JSONObject


class Cart : AppCompatActivity(){
    private lateinit var database: DatabaseReference
    private val uid = FirebaseAuth.getInstance().uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        database = FirebaseDatabase.getInstance().getReference("Cart/$uid")
        var title:String=""
        if (intent.hasExtra("product_name")&&intent.hasExtra("product_price")&&intent.hasExtra("product_photo")){
            title = intent.getStringExtra("product_name")?:""
            val price = intent.getDoubleExtra("product_price", 0.0)
            val purl = intent.getStringExtra("product_photo")?:""
            writepdata(title, purl, price)
        }

        val query : Query = FirebaseDatabase.getInstance().getReference("Cart/$uid")
        Log.w(TAG, "$uid")
        val options: FirebaseRecyclerOptions<Product> = FirebaseRecyclerOptions.Builder<Product>()
            .setQuery(query, Product::class.java)
            .build()
        val ad = CartAdapter(options)
        ad.startListening()
        productsRecyclerView.apply {
            layoutManager = GridLayoutManager(this@Cart, 1, RecyclerView.VERTICAL, false)
            adapter = ad
            progressBar2?.visibility = View.GONE
        }



        submit_order.setOnClickListener {

            ad.startListening()
            Checkout.preload(applicationContext)
            startpayment()

        }

    }




    private fun writedata(order: Product?) {
        val key = FirebaseDatabase.getInstance().getReference("Order").push().key
        if (key == null) {
            Log.w(TAG, "Couldn't get push key for posts")
            return
        }
        //val productValues = order!!.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "Order/$key" to (order!!.toMap()),
            "user-order/$uid/$key" to (order.toMap())
        )
        FirebaseDatabase.getInstance().reference.updateChildren(childUpdates)
    }


    private fun startpayment() {
        onPaymentSuccess()

    }





    private fun writepdata(title: String, photoUrl: String, price: Double){

        val key = database.child("Cart/$uid").push().key
        if (key == null) {
            Log.w(TAG, "Couldn't get push key for posts")
            return
        }

        val prodat = Product(title, photoUrl, price, "")
        val productValues = prodat.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "$key" to productValues
        )

        database.updateChildren(childUpdates)
        Toast.makeText(this@Cart, "Successfully Added Item $title to Cart", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "CART"
    }

     fun onPaymentSuccess() {
        Toast.makeText(this@Cart, "Payment Successful:  ",Toast.LENGTH_LONG).show()
        val query1 = FirebaseDatabase.getInstance().reference.child("Cart/$uid")
            .orderByChild("title")

        query1.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Couldn't get push key for posts")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val order = it.getValue(Product::class.java)
                    Log.d(TAG,order.toString())
                    writedata(order)
                    query1.removeEventListener(this)
                }


            }
        })
        FirebaseDatabase.getInstance().reference.child("Cart/$uid")
            .removeValue()
    }


}