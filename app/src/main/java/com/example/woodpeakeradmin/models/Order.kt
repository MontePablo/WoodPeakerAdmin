package com.example.woodpeakeradmin.models

import android.widget.ArrayAdapter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Order {
    var discountPercent=""
    var price=""
    var productId=""
    var title=""
    var dateTime=""
    var image=""
    var status=""
    var clientId=""
    var shape=""
    var paymentId=""
    var lengths=ArrayList<String>()
    var roomImages=RoomImages()
    var addons=ArrayList<Addon>()
    var pack="Customer"
    var totalPrice=""
    var finalPriceAftrDiscnt=""
    var instruction=""
    var address=""


}