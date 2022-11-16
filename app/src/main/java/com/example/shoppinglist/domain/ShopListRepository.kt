package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    suspend fun addShopItem(ShopItem: ShopItem)

    suspend  fun editShopItem(shopItem: ShopItem)

    suspend fun getShopItem(ShopItemId: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>

    suspend  fun removeShopItem(shopItem: ShopItem)
}