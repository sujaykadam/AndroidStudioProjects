package com.example.kiweysrecepies

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _categorieState = mutableStateOf(RecepieState())
    val categoriesState: State<RecepieState> = _categorieState

    init {
        fetchCategories()
    }

    private fun fetchCategories(){
        viewModelScope.launch {
            try {
                val response = recepieService.getCategories()
                _categorieState.value =  _categorieState.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null
                )
            } catch (e: Exception){
                _categorieState.value = _categorieState.value.copy(
                    loading = false,
                    error = "Error Fetching Categories ${e.message}"
                )
            }
        }
    }

    data class RecepieState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )

}