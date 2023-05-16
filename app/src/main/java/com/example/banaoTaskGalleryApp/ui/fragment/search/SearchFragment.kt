package com.example.banaoTaskGalleryApp.ui.fragment.search

import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banaoTaskGalleryApp.R
import com.example.banaoTaskGalleryApp.databinding.FragmentShopBinding
import com.example.banaoTaskGalleryApp.ui.base.BaseFragment
import com.example.banaoTaskGalleryApp.ui.fragment.adapter.PhotosRecyclerAdapter
import com.example.banaoTaskGalleryApp.utils.collectLatestLifeCycleFlow
import com.example.banaoTaskGalleryApp.utils.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentShopBinding>() {
    val vm:SearchViewModel by viewModels()
    @Inject
    lateinit var photosAdapter: PhotosRecyclerAdapter

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_shop
    }

    override fun initView() {
        super.initView()

        checkPagingStates()
        binding.rvSearchPhotos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = photosAdapter
        }

        binding.searchBarPhotos.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED)
                    {
                        vm.getSearchPhotos(p0).collectLatest {
                            photosAdapter.submitData(it)
                        }
                    }
                }
               return true
            }

        })

    }


    override fun observe() {
        super.observe()

    }

    private fun checkPagingStates()
    {
        collectLatestLifeCycleFlow(photosAdapter.loadStateFlow){loadState->

            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading)
                binding.progressBar.isVisible = true
            else {
                binding.progressBar.isVisible = false
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    showErrorSnackBar(it.error.message?:"Unknown Error"){
                        photosAdapter.retry()
                    }
                }

            }

        }
    }


}