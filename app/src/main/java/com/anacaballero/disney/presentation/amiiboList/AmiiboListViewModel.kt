package com.anacaballero.disney.presentation.amiiboList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anacaballero.disney.domain.entities.Character
import com.anacaballero.disney.domain.useCases.characters.CharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AmiiboListViewModel @Inject constructor(
    private val useCase: CharactersUseCase
): ViewModel() {

    private val _characters: MutableLiveData<List<Character>> = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> = _characters

    fun loadCharacters() {
        viewModelScope.launch {
            try {
                val newCharacters: List<Character> = useCase.getCharacters()

                _characters.value = newCharacters
            } catch (e: Exception) {
                Log.e("ERROR", e.stackTraceToString())
            }
        }
    }
}