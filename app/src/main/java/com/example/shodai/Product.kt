package com.example.shodai

import com.google.firebase.database.Exclude

data class Product(
    var title:String,
    val photoUrl: String ,
    val price: Double,
    val category: String
)
{
    constructor():this("","",0.0, "")
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title,
            "photoUrl" to photoUrl,
            "price" to price,
            "category" to category
        )
    }
    fun geturl(): String? {
        return photoUrl
    }

    fun getname(): String? {
        return title
    }

    fun getPprice(): Double? {
        return price
    }

}