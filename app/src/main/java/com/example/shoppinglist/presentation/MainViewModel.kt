package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopListUseCase
import com.example.shoppinglist.domain.RemoveShopItemUseCase
import com.example.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopList = GetShopListUseCase(repository)
    private val removeShopItem = RemoveShopItemUseCase(repository)
    private val editShopItem = EditShopItemUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.IO)

    val shopList = getShopList.getShopList()

    fun changeEnableState(shopItem: ShopItem) {
        scope.launch {
            val newItem = shopItem.copy(enable = !shopItem.enable)
            editShopItem.editShopItem(newItem)
        }
    }

    fun removeShopItem(shopItem: ShopItem) {
        scope.launch {
            removeShopItem.removeShopItem(shopItem)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}