package com.paavam.swiggyapp.repository.local

//class SwiggyLocalUserAddressRepository @Inject constructor(
//    private val userAddressDao: UserAddressDao
//) : SwiggyUserAddressRepository {
//
//    override suspend fun getAddressById(addressId: Int): Flow<ResponseResult<UserAddress>> = flow {
//        userAddressDao
//            .getAddressById(addressId)
//            .map {
//                UserAddress(
//                    it.addressId,
//                    it.fullAddress,
//                    AddressType.valueOf(it.type.toString()),
//                    it.friendlyLabel,
//                    it.landmark
//                )
//            }.transform { address -> emit(ResponseResult.success(address)) }
//    }
//
//    override suspend fun getAllAddresses(): Flow<ResponseResult<List<UserAddress>>> {
//        return userAddressDao
//            .getAllAddresses().map { address ->
//                address.map {
//                    UserAddress(
//                        it.addressId,
//                        it.fullAddress,
//                        AddressType.valueOf(it.type.toString()),
//                        it.friendlyLabel,
//                        it.landmark
//                    )
//                }
//            }
//            .transform { addresses -> emit(ResponseResult.success(addresses)) }
//            .catch { emit(ResponseResult.success(emptyList())) }
//    }
//
//    override suspend fun addAddress(userAddress: UserAddress): ResponseResult<String> =
//        runCatching {
//            userAddressDao.addAddress(
//                UserAddressEntity(
//                    0,
//                    userAddress.fullAddress,
//                    com.paavam.swiggyapp.data.local.entity.AddressType.valueOf(userAddress.type.toString()),
//                    userAddress.friendlyLabel,
//                    userAddress.landmark
//                )
//            ).let { ResponseResult.success("Success") }
//        }.getOrDefault(ResponseResult.error("unable to add address!"))
//
//
//    override suspend fun deleteAddressById(addressId: Int): ResponseResult<String> = runCatching {
//        userAddressDao.deleteAddressById(addressId)
//        ResponseResult.success(addressId.toString())
//    }.getOrDefault(ResponseResult.error("Unable to delete address!"))
//
//    override suspend fun deleteAllAddresses() {
//        userAddressDao.deleteAllAddresses()
//    }
//
//    override suspend fun updateAddressById(addressId: String, userAddress: UserAddress) {
//        userAddressDao.updateAddressById(
//            addressId, UserAddressEntity(
//                0,
//                userAddress.fullAddress,
//                com.paavam.swiggyapp.data.local.entity.AddressType.valueOf(userAddress.type.toString()),
//                userAddress.friendlyLabel,
//                userAddress.landmark
//            )
//        )
//    }
//}