package com.paavam.swiggyapp.core

class SwiggyAddressTask private constructor(
    val addressId: String,
    val action: SwiggyTaskAction
) {
    companion object {
        fun create(addressId: String) = SwiggyAddressTask(addressId, SwiggyTaskAction.CREATE)
        fun update(addressId: String) = SwiggyAddressTask(addressId, SwiggyTaskAction.UPDATE)
        fun delete(addressId: String) = SwiggyAddressTask(addressId, SwiggyTaskAction.DELETE)
    }
}

//class SwiggyCartTask private constructor(
//    val food: FoodEntity,
//    val action: SwiggyTaskAction
//) {
//    companion object {
//        fun create(noteId: String) = SwiggyCartTask(noteId, SwiggyTaskAction.CREATE)
//        fun update(noteId: String) = SwiggyCartTask(noteId, SwiggyTaskAction.UPDATE)
//        fun delete(noteId: String) = SwiggyCartTask(noteId, SwiggyTaskAction.DELETE)
//    }
//}

enum class SwiggyTaskAction {
    CREATE, UPDATE, DELETE
}