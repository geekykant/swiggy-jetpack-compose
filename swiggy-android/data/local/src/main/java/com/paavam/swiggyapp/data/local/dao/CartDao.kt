package com.paavam.swiggyapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paavam.swiggyapp.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CartDao {

    @Query("SELECT * FROM Cart")
    abstract fun getAllCartItems(): Flow<List<CartEntity>>

    @Query("SELECT * FROM Cart WHERE cart_food_id = :foodId")
    abstract fun getCartItemById(foodId: Long): Flow<CartEntity>

    @Insert
    abstract fun addCartItem(cartEntity: CartEntity)

    @Insert
    abstract fun addCartItems(cartEntities: List<CartEntity>)

    @Query("DELETE FROM Cart WHERE cart_food_id = :cartItemId ")
    abstract fun deleteCartItemById(cartItemId: Long)

    @Query("DELETE FROM Cart")
    abstract fun deleteAllCartItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateCartItemById(cartItem: CartEntity)
}