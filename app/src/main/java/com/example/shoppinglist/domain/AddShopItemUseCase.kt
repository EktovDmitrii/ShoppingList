package com.example.shoppinglist.domain

import javax.inject.Inject

class AddShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {
    suspend fun addShopItem(ShopItem: ShopItem){
shopListRepository.addShopItem(ShopItem)
}
}