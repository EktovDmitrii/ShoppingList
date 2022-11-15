package com.example.shoppinglist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.FragmentShopItemBinding
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout
import java.util.zip.Inflater

class ShopItemFragment : Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
    get() = _binding ?: throw RuntimeException("FragmentShopItemBinding == null")

    private lateinit var viewModel: ShopItemViewModel

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw java.lang.RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
        Log.d("FRAGMENT", "onCreateCaused")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
_binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
        Log.d("FRAGMENT", "onViewCreatedCaused")

    }

    override fun onStart() {
        super.onStart()
        Log.d("FRAGMENT", "onStartCaused")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FRAGMENT", "onResumeCaused")
    }

    override fun onPause() {
        super.onPause()
        Log.d("FRAGMENT", "onPauseCaused")
    }

    override fun onStop() {
        super.onStop()
        Log.d("FRAGMENT", "onStopCaused")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("FRAGMENT", "onDestroyViewCaused")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FRAGMENT", "onDestroyCaused")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("FRAGMENT", "onDetachCaused")
    }




    private fun observeViewModel() {

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> lanchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addTextChangeListeners() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun lanchEditMode() {
        viewModel.getShopItem(shopItemId)
        binding.saveButton.setOnClickListener {
            viewModel.editShopItem(
                binding.etName.text?.toString(),
                binding.etCount.text?.toString()
            )
        }
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            viewModel.addShopItem(
                binding.etName.text?.toString(),
                binding.etCount.text?.toString()
            )
        }
    }

    private fun parseParam() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown Screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param ShopItemId is absent")
            }
            shopItemId = args.getInt(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_ADD)
                }
            }
        }

//       полная версия кода
//        fun newInstanceAddItem(): ShopItemFragment {
//            val args = Bundle()
//            args.putString(EXTRA_SCREEN_MODE, MODE_ADD)
//            val fragment = ShopItemFragment()
//            fragment.arguments = args
//            return fragment
//        }

       //Передаем параметры во фрагмент
        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
                }
            }
        }

//        fun newIntentAddItem(context: Context): Intent {
//            val intent = Intent(context, ShopItemActivity::class.java)
//            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
//            return intent
//        }
//
//        fun newIntentEditItem(context: Context, shopItemID: Int): Intent {
//            val intent = Intent(context, ShopItemActivity::class.java)
//            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
//            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemID)
//
//            return intent
    }
}

