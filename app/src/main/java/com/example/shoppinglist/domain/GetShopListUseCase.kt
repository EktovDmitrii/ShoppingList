package com.example.shoppinglist.domain

import java.util.function.ToDoubleBiFunction

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopList(): List<ShopItem> {
        return shopListRepository.getShopList()
    }
}
