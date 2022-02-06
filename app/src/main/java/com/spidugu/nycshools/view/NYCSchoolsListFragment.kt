package com.spidugu.nycshools.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spidugu.nycshools.adapter.NYCListRecyclerAdapter
import com.spidugu.nycshools.databinding.FragmentNycSchoolsListBinding
import com.spidugu.nycshools.util.observeEventIfNotHandled
import com.spidugu.nycshools.viewmodels.NYCSchoolsListViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [NYCSchoolsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NYCSchoolsListFragment :
    BaseFragment<NYCSchoolsListViewModel, FragmentNycSchoolsListBinding>() {

    private val recyclerviewAdapter = NYCListRecyclerAdapter {
        vm.onClickItem(it)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNycSchoolsListBinding =
        FragmentNycSchoolsListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun observeViewModel() {
        vm.schoolsInfoListLiveData.observe(viewLifecycleOwner) {
            recyclerviewAdapter.updateData(it)
        }

        vm.showErrorInfoLiveData.observe(viewLifecycleOwner) {
            binding.errorView.container.isVisible = it
        }

        vm.showErrorInfoDataText.observe(viewLifecycleOwner) {
            it?.let {
                binding.errorView.errorTitle.text = it
            }
        }

        vm.showProgressBarLiveData.observe(viewLifecycleOwner) {
            binding.circularProgressBar.visibility = it
        }

        vm.showRefreshSpinner.observe(viewLifecycleOwner) {
            binding.swipeToRefresh.isRefreshing = it
        }

        vm.navigateToLiveData.observeEventIfNotHandled(viewLifecycleOwner) {
            findNavController().navigate(it)
        }
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            adapter = recyclerviewAdapter
        }

        //Swipe to refresh the list.
        binding.swipeToRefresh.setOnRefreshListener {
            vm.onRefreshClicked()
        }

        binding.errorView.retryButton.setOnClickListener {
            vm.onViewCreated()
        }
    }

    override fun navigateBack() {
        if (!findNavController().popBackStack())
            requireActivity().finish()
    }
}