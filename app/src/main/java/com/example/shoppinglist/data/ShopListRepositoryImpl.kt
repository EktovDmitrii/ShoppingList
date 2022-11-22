package com.example.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
   private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
) : ShopListRepository {


    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))

    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }
//Медиатор лайв дата, посзволяет перехватить лайв дату и сделать с ней какие-то операции:
    //задать условия, поменять значение, или тип данных, если нам необходимо только
    //сделать преобразование, то вызываем метод map из Transformations класса.
//    override suspend fun getShopList(): LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>().apply {
//        addSource(shopListDao.getShopList()) {
//           value = mapper.mapListDbModelToListEntity(it)
//            }
//        }

    override fun getShopList(): LiveData<List<ShopItem>> = Transformations.map(
        shopListDao.getShopList()
    ) {
        mapper.mapListDbModelToListEntity(it)
    }

    override suspend fun removeShopItem(shopItem: ShopItem) {
        shopListDao.removeShopItem(shopItem.id)
    }
}