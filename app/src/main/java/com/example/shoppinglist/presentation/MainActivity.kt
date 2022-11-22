package com.example.shoppinglist.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.domain.ShopItem
import javax.inject.Inject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as ShopItemApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecycleView()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }
        binding.buttonAddShopItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
        thread {
            val cursor = contentResolver.query(
                Uri.parse("content://com.example.shoppinglist/shop_items"),
                null,
                null,
                null,
                null,
                null,
            )
            while (cursor?.moveToNext() == true) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val count = cursor.getInt(cursor.getColumnIndexOrThrow("count"))
                val enable = cursor.getInt(cursor.getColumnIndexOrThrow("enable")) > 0
                val shopItem = ShopItem(
                    id = id,
                    name = name,
                    count= count,
                    enable = enable
                )
Log.d("MainActivityy", shopItem.toString())
            }
        }
    }


override fun onEditingFinished() {
    Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
    supportFragmentManager.popBackStack()
}

private fun setupRecycleView() {
    with(binding.rvShopList) {
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
    setupLongClickListener()

    setupClickListener()

    setupSwipeListener(binding.rvShopList)
}


private fun isOnePaneMode(): Boolean {
    return binding.shopItemContainer == null
}

private fun launchFragment(fragment: Fragment) {
    supportFragmentManager.popBackStack()
    supportFragmentManager.beginTransaction()
        .replace(R.id.shop_item_container, fragment)
        .addToBackStack(null)
        .commit()
}


private fun setupSwipeListener(rvShopList: RecyclerView) {
    val callback = object : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val item = shopListAdapter.currentList[viewHolder.adapterPosition]
            viewModel.removeShopItem(item)
        }
    }
    val itemTouchHelper = ItemTouchHelper(callback)
    itemTouchHelper.attachToRecyclerView(rvShopList)
}

private fun setupClickListener() {
    shopListAdapter.onShopItemClickListener = {
        if (isOnePaneMode()) {
            val intent = ShopItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        } else {
            launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
        }
    }
}

private fun setupLongClickListener() {
    shopListAdapter.onShopItemLongClickListener = {
        viewModel.changeEnableState(it)
    }
}
}
