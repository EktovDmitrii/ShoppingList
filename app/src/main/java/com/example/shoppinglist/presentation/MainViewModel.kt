package com.example.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopListUseCase
import com.example.shoppinglist.domain.RemoveShopItemUseCase
import com.example.shoppinglist.domain.ShopItem

class MainViewModel: ViewModel() {

private val repository = ShopListRepositoryImpl

    private val getShopList = GetShopListUseCase(repository)
    private val removeShopItem = RemoveShopItemUseCase(repository)
    private val editShopItem = EditShopItemUseCase(repository)

    val shopList = MutableLiveData<List<ShopItem>>()


    fun getShopList() {
        val list = getShopList.getShopList()
        shopList.value = list
    }

    fun changeEnableState(shopItem: ShopItem){
        val newItem = shopItem.copy(enable = !shopItem.enable)
        editShopItem.editShopItem(newItem)
        getShopList()
    }

    fun removeShopItem(shopItem: ShopItem){
        removeShopItem.removeShopItem(shopItem)
        getShopList()
    }

}