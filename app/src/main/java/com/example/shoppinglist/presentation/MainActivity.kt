package com.example.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecycleView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.shopList = it
        }
    }
private fun setupRecycleView(){
    val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
    with(rvShopList) {
        shopListAdapter = ShopListAdapter()
        adapter = shopListAdapter
        recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_ENABLE,
            ShopListAdapter.MAX_PULL_SIZE
        )
        recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_DISABLE,
            ShopListAdapter.MAX_PULL_SIZE
        )
    }
}

}
