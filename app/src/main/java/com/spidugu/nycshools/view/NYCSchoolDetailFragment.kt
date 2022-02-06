package com.spidugu.nycshools.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.spidugu.nycshools.R
import com.spidugu.nycshools.databinding.FragmentNycSchoolDetailBinding
import com.spidugu.nycshools.viewmodels.NYCSchoolDetailViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [NYCSchoolDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NYCSchoolDetailFragment :
    BaseFragment<NYCSchoolDetailViewModel, FragmentNycSchoolDetailBinding>() {

    private val args: NYCSchoolDetailFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNycSchoolDetailBinding =
        FragmentNycSchoolDetailBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        vm.onViewCreated(args)
    }

    override fun navigateBack() {
       findNavController().navigate(NYCSchoolDetailFragmentDirections.actionDetailFragmentToLandingFragment())
    }

    private fun observeViewModel() {
        vm.schoolNameValueLiveData.observe(viewLifecycleOwner) {
            binding.schoolName.text = it
        }

        vm.dbnValueLiveData.observe(viewLifecycleOwner) {
            binding.dbn.text = it
        }
        vm.satMathAvgValueLiveData.observe(viewLifecycleOwner) {
            binding.mathAvg.text = getString(R.string.math_avg, it)
        }

        vm.satWritingAvgValueLiveData.observe(viewLifecycleOwner) {
            binding.writingAvg.text = getString(R.string.writing_avg, it)
        }

        vm.satTestTakersValueLiveData.observe(viewLifecycleOwner) {
            binding.totalTestTakers.text = getString(R.string.people, it)
        }

        vm.satReadingAvgValueLiveData.observe(viewLifecycleOwner) {
            binding.readingAvg.text = getString(R.string.reading, it)
        }
    }
}