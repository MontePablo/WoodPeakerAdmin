package com.example.woodpeakeradmin.models

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Product {

    var title=""
    var price=""
    var features=ArrayList<String>()
    var description=""
    var images=ArrayList<String>()
    var ratings=ArrayList<HashMap<String, Objects>>()
    //   map["name"] map["date"] map["comment"] map["image"] map["rating"]

}