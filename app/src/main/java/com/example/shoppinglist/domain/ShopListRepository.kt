package com.example.shoppinglist.domain

interface ShopListRepository {

    fun addShopItem(ShopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(ShopItemId: Int): ShopItem

    fun getShopList(): List<ShopItem>

    fun removeShopItem(shopItem: ShopItem)
}