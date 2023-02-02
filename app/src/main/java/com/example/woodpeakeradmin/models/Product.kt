package com.example.woodpeakeradmin.models

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

 class Product {
    var title=""
    var price=""
    var shape=""
    var features=ArrayList<String>()
    var description=""
    var images=HashMap<String,Objects>()
    var ratings=ArrayList<HashMap<String, Objects>>()
    //   map["name"] map["date"] map["comment"] map["image"] map["rating"]
    var addons=ArrayList<HashMap<String,Objects>>()
}