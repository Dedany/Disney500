package com.anacaballero.disney.presentation.amiiboList

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anacaballero.disney.R
import com.anacaballero.disney.data.dataSources.amiibo.remote.AmiiboRemoteDataSource
import com.anacaballero.disney.data.dataSources.amiibo.remote.AmiiboRemoteDataSourceImpl
import com.anacaballero.disney.data.repositories.characters.AmiiboRepositoryImpl
import com.anacaballero.disney.databinding.ActivityAmiiboListBinding
import com.anacaballero.disney.domain.repositories.characters.CharactersRepository
import com.anacaballero.disney.domain.useCases.characters.CharactersUseCase
import com.anacaballero.disney.domain.useCases.characters.CharactersUseCaseImpl
import com.anacaballero.disney.presentation.main.adapter.CharactersListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AmiiboListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAmiiboListBinding
    private lateinit var viewModel: AmiiboListViewModel

    private var charactersAdapter: CharactersListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAmiiboListBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AmiiboListViewModel::class.java]
        setContentView(binding.root)

        setAdapters()
        setObservers()
        //viewModel.loadCharacters()
    }

    private fun setObservers() {
        viewModel.characters.observe(this) { characters ->
            charactersAdapter?.submitList(characters)
        }
    }

    private fun setAdapters() {
        charactersAdapter = CharactersListAdapter()
        binding.recyclerCharacters.adapter = charactersAdapter
        binding.recyclerCharacters.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
    }
}