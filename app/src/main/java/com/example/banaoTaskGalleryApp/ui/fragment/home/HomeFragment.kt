package com.example.banaoTaskGalleryApp.ui.fragment.home

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banaoTaskGalleryApp.R
import com.example.banaoTaskGalleryApp.databinding.FragmentHomeBinding
import com.example.banaoTaskGalleryApp.ui.base.BaseFragment
import com.example.banaoTaskGalleryApp.ui.fragment.adapter.PhotosRecyclerAdapter
import com.example.banaoTaskGalleryApp.utils.collectLatestLifeCycleFlow
import com.example.banaoTaskGalleryApp.utils.showErrorSnackBar
import com.example.banaoTaskGalleryApp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var photosAdapter: PhotosRecyclerAdapter

    val vm :HomeViewModel by viewModels()

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        super.initView()
        checkPagingStates()
        binding.rvPhotos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = photosAdapter
        }
    }

    override fun observe() {
        super.observe()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                vm.getRecentPhotos().collectLatest {
                    photosAdapter.submitData(it)
                }
            }
        }
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