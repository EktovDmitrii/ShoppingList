package com.example.shoppinglist.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
fun addShopItem(ShopItem: ShopItem){
shopListRepository.addShopItem(ShopItem)
}
}