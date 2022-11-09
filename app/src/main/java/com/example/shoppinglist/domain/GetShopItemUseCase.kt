package com.example.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem(ShopItemId: Int): ShopItem {
        return shopListRepository.getShopItem(ShopItemId)
    }
}
