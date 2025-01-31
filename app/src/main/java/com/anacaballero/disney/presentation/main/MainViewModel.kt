package com.anacaballero.disney.presentation.main

import android.util.Log
import com.anacaballero.disney.domain.entities.Character
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anacaballero.disney.R
import com.anacaballero.disney.domain.useCases.characters.CharactersUseCase
import com.anacaballero.disney.domain.useCases.characters.CharactersUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer

import java.util.TimerTask
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: CharactersUseCase
) : ViewModel() {

    private val _characters: MutableLiveData<List<Character>> = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> = _characters

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private var timer: Timer? = null
    private var job: Job? = null
    
    private var isSearchingByLimit: Boolean = false

    fun onSearchTypeCheckedChange(isChecked: Boolean) {
        isSearchingByLimit = isChecked

    }

    fun loadCharacters() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val newCharacters: List<Character> = useCase.getCharacters()

                _characters.value = newCharacters
                _isLoading.value = false
            } catch (e: Exception) {
                Log.e("ERROR", e.stackTraceToString())
            }
        }
    }

    private fun loadCharactersByPageSize(pageSize: String) {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                val newCharacters: List<Character> = useCase.getCharactersByPageSize(pageSize)

                _characters.value = newCharacters
                _isLoading.value = false
            } catch (e: Exception) {
                Log.e("ERROR", e.stackTraceToString())
            }
        }
    }

    private fun loadCharactersByName(name: String) {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                val newCharacters =  useCase.getCharactersByName(name)

                _characters.value = newCharacters
                _isLoading.value = false
            } catch (e: Exception) {
                Log.e("ERROR", e.stackTraceToString())
            }
        }
    }

    fun search(query: String) {
        _isLoading.value = true
        timer?.cancel()
        timer = Timer()
        timer?.schedule(
            object : TimerTask() {
                override fun run() {
                    if (isSearchingByLimit) {
                        loadCharactersByPageSize(query)
                    } else {
                        loadCharactersByName(query)
                    }
                }
            }, 1500L
        )
    }
}