package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ItemShopDisabledBinding
import com.example.shoppinglist.databinding.ItemShopEnabledBinding
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter :
    ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {


    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_DISABLE -> R.layout.item_shop_disabled
            VIEW_TYPE_ENABLE -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown ViewType $viewType ")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding)

    }


    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = viewHolder.binding

       when (binding){
           is ItemShopDisabledBinding ->{
               binding.shopItem = shopItem
           }
           is ItemShopEnabledBinding ->{
               binding.shopItem = shopItem
           }
       }


        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enable) {
            VIEW_TYPE_ENABLE
        } else {
            VIEW_TYPE_DISABLE
        }
    }


    companion object {
        const val VIEW_TYPE_ENABLE = 1
        const val VIEW_TYPE_DISABLE = 0
        const val MAX_PULL_SIZE = 15
    }
}