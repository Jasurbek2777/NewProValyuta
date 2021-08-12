package com.example.newprovalyuta.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newprovalyuta.R
import com.example.newprovalyuta.adapters.RvAdapter
import com.example.newprovalyuta.databinding.FragmentSavedBinding
import com.example.newprovalyuta.databinding.RvItemBinding
import com.example.newprovalyuta.room.entity.ValyutaEntity
import com.example.newprovalyuta.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class SavedFragment : Fragment() {
    lateinit var binding: FragmentSavedBinding
    lateinit var adapter: RvAdapter
    val mainViewModel: MainViewModel by viewModels()
    private var param1: String? = null
    private var param2: String? = null
    var list = MutableLiveData<ArrayList<ValyutaEntity>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBinding.inflate(inflater, container, false)
        setAdapter()
        return binding.root
    }

    private fun setAdapter() {
        adapter = RvAdapter(loadData().value!!, object : RvAdapter.itemOnCLick {
            override fun itemClick(valyuta: ValyutaEntity, position: Int) {
                Toast.makeText(requireContext(), "saved item clicked", Toast.LENGTH_SHORT).show()
            }

            override fun itemSaveClick(valyuta: ValyutaEntity, position: Int, item: RvItemBinding) {
                valyuta.saved = !valyuta.saved
                list.value?.set(position, valyuta)
                if (!valyuta.saved) {
                    try {
                        mainViewModel.dao().deleteById(valyuta.code)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    mainViewModel.dao().add(valyuta)
                }
            }
        }, requireContext())
        binding.rv.adapter = adapter
    }

    private fun loadData(): MutableLiveData<ArrayList<ValyutaEntity>> {
        list.value = mainViewModel.dao().getAll() as ArrayList<ValyutaEntity>
        if (list.value!!.size == 0) {
            binding.tv.visibility = View.VISIBLE
            binding.rv.visibility = View.INVISIBLE
        } else {
            binding.tv.visibility = View.INVISIBLE
            binding.rv.visibility = View.VISIBLE

        }
        return list
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SavedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        setAdapter()
    }
}