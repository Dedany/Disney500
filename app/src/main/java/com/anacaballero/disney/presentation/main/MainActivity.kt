package com.anacaballero.disney.presentation.main

import android.os.Bundle
import android.text.InputType
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anacaballero.disney.R
import com.anacaballero.disney.data.dataSources.amiibo.remote.AmiiboRemoteDataSource
import com.anacaballero.disney.data.dataSources.amiibo.remote.AmiiboRemoteDataSourceImpl
import com.anacaballero.disney.data.dataSources.characters.remote.CharacterRemoteDataSource
import com.anacaballero.disney.data.dataSources.characters.remote.CharacterRemoteDataSourceImpl
import com.anacaballero.disney.data.dataSources.characters.remote.CharacterRemoteMockDataSourceImpl
import com.anacaballero.disney.data.repositories.characters.AmiiboRepositoryImpl
import com.anacaballero.disney.data.repositories.characters.CharacterRepositoryImpl
import com.anacaballero.disney.databinding.ActivityMainBinding
import com.anacaballero.disney.domain.repositories.characters.CharactersRepository
import com.anacaballero.disney.domain.useCases.characters.CharactersUseCase
import com.anacaballero.disney.domain.useCases.characters.CharactersUseCaseImpl
import com.anacaballero.disney.presentation.main.adapter.CharactersListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val viewModel: MainViewModel by viewModels()
    private var characterAdapter: CharactersListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        setAdapters()
        setObservers()
        setListeners()

        viewModel.loadCharacters()

    }

    private fun setAdapters() {
        characterAdapter = CharactersListAdapter()
        binding?.recyclerCharacters?.adapter = characterAdapter
    }

    private fun setListeners() {
        binding?.recyclerCharacters?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleIntemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstItemPositon = layoutManager.findFirstVisibleItemPosition()

                val visibleItems = visibleIntemCount + firstItemPositon

                val positionToLoadMore = totalItemCount - 5

                if (firstItemPositon >= 0 && visibleItems >= positionToLoadMore) {
                    loadMoreItems()
                }
            }

        })
        binding?.switchSearchType?.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onSearchTypeCheckedChange(isChecked)

            binding?.editTextName?.text?.clear()

            binding?.editTextName?.inputType = if (isChecked) {
                InputType.TYPE_CLASS_NUMBER
            } else {
                InputType.TYPE_CLASS_TEXT
            }
        }
    }

    private fun loadMoreItems() {
        lifecycleScope.launch {
            viewModel.loadMore()
        }
    }

    private fun setObservers() {
        viewModel.characters.observe(this) { characters ->
            binding?.textViewResults?.text =
                if (characters.isEmpty()) getString(R.string.no_results)
                else "Hay ${characters.size} personajes"
            characterAdapter?.submitList(characters)
        }
        viewModel.isLoading.observe(this) { isLoading: Boolean ->
            if (isLoading) {
                //lo que queremos hacer mientras se cargan los datos
                if (binding?.viewSwicherCharacters?.currentView?.id == R.id.layout_characters_container) {
                    binding?.viewSwicherCharacters?.showPrevious()
                }
            } else {
                //si no está cargando, lo que queremos hacer
                if (binding?.viewSwicherCharacters?.currentView?.id == R.id.progress_bar_item) {
                    binding?.viewSwicherCharacters?.showNext()
                }
            }
        }

        binding?.editTextName?.doOnTextChanged { text, _, _, _ ->
            viewModel.search(text.toString())
        }
    }
}