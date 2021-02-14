package com.example.githubrepos.ui

import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingComponent

interface AppBindingComponent : DataBindingComponent {
    override fun getAppBindingAdapter(): AppBindingAdapter
}

class AppBindingAdapter() {

    @BindingAdapter("bind_onSearchClick")
    fun setSearchClickListener(editText: EditText, onSearchClick: (query: String) -> Unit) {

        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSearchClick.invoke(editText.text.trim().toString())
                (ContextCompat.getSystemService(
                    editText.context,
                    InputMethodManager::class.java
                ) as InputMethodManager).hideSoftInputFromWindow(
                    editText.windowToken,
                    0
                )
                true
            } else {
                false
            }
        }
    }
}