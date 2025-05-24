package com.example.shodai

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class categories : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories)
    }

    fun fruits(view: View) {
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("category", "Fruits")
        startActivity(intent)
    }
    fun grains(view: View) {
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("category", "Grains")
        startActivity(intent)
    }
    fun veg(view: View) {
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("category", "Vegetables")
        startActivity(intent)
    }
    fun snacks(view: View) {
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("category", "Snacks")
        startActivity(intent)
    }
    fun oil(view: View) {
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("category", "Dairy Oil")
        startActivity(intent)
    }
    fun mf(view: View) {
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("category", "Meat and Fish")
        startActivity(intent)
    }
    fun coke(view: View) {
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("category", "Beverages")
        startActivity(intent)
    }
}