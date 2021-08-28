package com.paavam.swiggyapp.repository.mapper

import com.paavam.swiggyapp.core.data.user.model.UserAddress
import com.paavam.swiggyapp.data.remote.model.request.UserAddressRequest

object UserAddressMapper {

//    fun mapUserAddressToEntity(type: UserAddress): UserAddressEntity =
//        UserAddressEntity(
//            fullAddress = type.fullAddress,
//            type = AddressType.valueOf(type.type.toString()),
//            friendlyLabel = type.friendlyLabel,
//            landmark = type.landmark
//        )

    fun mapUserAddressToRequest(type: UserAddress): UserAddressRequest =
        UserAddressRequest(
            fullAddress = type.fullAddress,
            type = com.paavam.swiggyapp.data.remote.model.request.AddressType.valueOf(type.type.toString()),
            friendlyLabel = type.friendlyLabel,
            landmark = type.landmark
        )

}