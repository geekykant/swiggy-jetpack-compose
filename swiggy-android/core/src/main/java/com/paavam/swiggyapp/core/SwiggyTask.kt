package com.paavam.swiggyapp.core

import com.paavam.swiggyapp.core.data.model.Food

//class SwiggyAddressTask private constructor(
//    val addressId: String,
//    val action: SwiggyTaskAction
//) {
//    companion object {
//        fun create(addressId: String) = SwiggyAddressTask(addressId, SwiggyTaskAction.CREATE)
//        fun update(addressId: String) = SwiggyAddressTask(addressId, SwiggyTaskAction.UPDATE)
//        fun delete(addressId: String) = SwiggyAddressTask(addressId, SwiggyTaskAction.DELETE)
//    }
//}

class SwiggyCartTask private constructor(
    val foodId: Long,
    val food: Food,
    val action: SwiggyTaskAction
) {
    companion object {
        fun create(food: Food) = SwiggyCartTask(0, food, SwiggyTaskAction.CREATE)
        fun update(foodId: Long, food: Food) =
            SwiggyCartTask(foodId, food, SwiggyTaskAction.UPDATE)

        fun delete(foodId: Long, food: Food) = SwiggyCartTask(foodId, food, SwiggyTaskAction.DELETE)
    }
}

enum class SwiggyTaskAction {
    CREATE, UPDATE, DELETE
}