package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun addShopItem(ShopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(ShopItemId: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>

    fun removeShopItem(shopItem: ShopItem)
}