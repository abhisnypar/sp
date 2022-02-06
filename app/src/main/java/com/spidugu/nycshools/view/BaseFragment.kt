package com.spidugu.nycshools.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass


abstract class BaseFragment<V : ViewModel, VB : ViewBinding> : Fragment() {
    private var _binding: ViewBinding? = null

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    protected val vm by viewModel(clazz = clazz)

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    private val clazz: KClass<V>
        get() = ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<V>).kotlin

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        onBackPressed()
        return requireNotNull(_binding).root
    }

    private fun onBackPressed() {
        // This callback will only be called when MyFragment is at least Started.
        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    navigateBack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    abstract fun navigateBack()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}