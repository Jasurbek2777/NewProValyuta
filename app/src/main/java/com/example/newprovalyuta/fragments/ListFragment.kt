package com.example.newprovalyuta.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.newprovalyuta.R
import com.example.newprovalyuta.adapters.RvAdapter
import com.example.newprovalyuta.databinding.ConvertorDialogBinding
import com.example.newprovalyuta.databinding.FragmentListBinding
import com.example.newprovalyuta.databinding.RvItemBinding
import com.example.newprovalyuta.models.Icons
import com.example.newprovalyuta.room.entity.ValyutaEntity
import com.example.newprovalyuta.utils.Status
import com.example.newprovalyuta.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class ListFragment : Fragment() {
    val mainViewModel: MainViewModel by viewModels()
    lateinit var binding: FragmentListBinding
    var connec: ConnectivityManager? = null
    private var param1: String? = "0"
    lateinit var adapter: RvAdapter
    var list = MutableLiveData<ArrayList<ValyutaEntity>>()
    lateinit var iconList: ArrayList<Icons>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connec = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        loadIcons()
        binding.refreshLayout.isRefreshing = true
        loadData()
        binding.refreshLayout.setOnRefreshListener {
            if (connec?.getNetworkInfo(0)?.state == NetworkInfo.State.CONNECTED ||
                connec?.getNetworkInfo(1)?.state == NetworkInfo.State.CONNECTED
            ) {
                binding.tv.visibility = View.INVISIBLE
                binding.refreshLayout.isRefreshing = true
                binding.rv.visibility = View.VISIBLE

            } else {
                binding.refreshLayout.isRefreshing = false
                binding.rv.visibility = View.INVISIBLE
                binding.tv.visibility = View.VISIBLE
            }

            binding.refreshLayout.isRefreshing = false
        }
        return binding.root
    }

    private fun setAdapter() {
        adapter = RvAdapter(
            list.value ?: arrayListOf(),
            object : RvAdapter.itemOnCLick {
                override fun itemClick(valyuta: ValyutaEntity, position: Int) {
                    val builder = AlertDialog.Builder(requireContext()).create()
                    val dialog = ConvertorDialogBinding.inflate(layoutInflater)
                    builder.setView(dialog.root)
                    builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    valyuta.icon.let { dialog.image1.setImageResource(it) }
                    valyuta.title.also { dialog.tv1.text = it }
//                    valyutas?.get(valyutas.size - 1)?.icon?.let { dialog.image2.setImageResource(it) }
//                    valyutas?.get(valyutas.size - 1)?.title.also { dialog.tv2.text = it }
                    dialog.et1.addTextChangedListener {
                        try {
                            if (it.toString().isNotEmpty()) {
                                dialog.et2.setText(
                                    (it.toString()
                                        .toDouble() * (valyuta.cb_price.toDouble() / 1)).toString()
                                )
                            }else{
                                dialog.et2.setText("0")
                            }
                        } catch (e: Exception) {
                            Toast.makeText(requireContext(), "Number only", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    var sumKursi =
//                        (1 / valyutas!![position].cb_price.toDouble()).toLong().toString()
//                    v2.icon = R.drawable.ic_uzbekistan_flag
//                    v2.title = "Ozbekiston sumi"
                        dialog.convert.setOnClickListener {
//                        var changer = position
//                        position = valyutas.size
//                        v2 = changer
//                        dialog.image2.setImageResource(v2.icon)
//                        dialog.image1.setImageResource(v1.icon)
//                        dialog.tv1.text = v1.title
//                        dialog.tv2.text = v2.title
                        }
                    builder.show()
                }

                override fun itemSaveClick(
                    valyuta: ValyutaEntity,
                    position: Int,
                    item: RvItemBinding
                ) {
                    valyuta.saved = !valyuta.saved
                    list.value?.set(position, valyuta)
                    if (!valyuta.saved) {
                        mainViewModel.dao().deleteById(valyuta.code)

                    } else {
                        mainViewModel.dao().add(valyuta)
                    }
                }
            },
            requireContext()
        )
        binding.rv.adapter = adapter
    }

    private fun loadIcons() {
        iconList = ArrayList()
        iconList.add(Icons("AED", R.drawable.ic_uaa_flag))
        iconList.add(Icons("AUD", R.drawable.ic_australia_flag))
        iconList.add(Icons("CAD", R.drawable.ic_canada_flag))
        iconList.add(Icons("CHF", R.drawable.ic_switzerland_flag))
        iconList.add(Icons("CNY", R.drawable.ic_china_flag))
        iconList.add(Icons("DKK", R.drawable.ic_denmark_flag))
        iconList.add(Icons("EGP", R.drawable.ic_egypt_flag))
        iconList.add(Icons("EUR", R.drawable.euro))
        iconList.add(Icons("GBP", R.drawable.ic_gb_flag))
        iconList.add(Icons("ISK", R.drawable.ic_islands_flag))
        iconList.add(Icons("JPY", R.drawable.ic_japan_flag))
        iconList.add(Icons("KRW", R.drawable.ic_korea_flag))
        iconList.add(Icons("KWD", R.drawable.ic_kuwait_flag))
        iconList.add(Icons("KZT", R.drawable.ic_kazakhstan_flag))
        iconList.add(Icons("LBP", R.drawable.ic_lebanon_flag))
        iconList.add(Icons("MYR", R.drawable.ic_malaysia_flag))
        iconList.add(Icons("NOK", R.drawable.ic_norway_flag))
        iconList.add(Icons("PLN", R.drawable.ic_poland_flag))
        iconList.add(Icons("RUB", R.drawable.ic_russia_flag))
        iconList.add(Icons("SEK", R.drawable.ic_sweden_flag))
        iconList.add(Icons("SGD", R.drawable.ic_singapore_flag))
        iconList.add(Icons("TRY", R.drawable.ic_turkey_flag))
        iconList.add(Icons("UAH", R.drawable.ic_ukraine_flag))
        iconList.add(Icons("USD", R.drawable.ic_usa_flag))
        iconList.add(Icons("UZS", R.drawable.ic_uzbekistan_flag))
    }

    fun loadData(): MutableLiveData<ArrayList<ValyutaEntity>> {
        mainViewModel.get().observe(viewLifecycleOwner, {
            binding.refreshLayout.isRefreshing = false
            when (it.status) {
                Status.STATUS_LOADING -> {
                    binding.tv.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.STATUS_ERROR -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.tv.visibility = View.INVISIBLE
                }
                Status.STATUS_SUCCESS -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.tv.visibility = View.INVISIBLE
                    list = MutableLiveData()
                    var all = ArrayList<ValyutaEntity>()

                    for (i in 0 until it.data?.size!!) {
                        val a = it?.data[i]
                        val obj = ValyutaEntity(
                            a.code,
                            a.date,
                            a.cb_price,
                            a.nbu_buy_price,
                            a.nbu_cell_price,
                            a.title,
                            iconList[i].icon,
                            false
                        )
                        val savedValyuta = mainViewModel.dao().getAll() as ArrayList<ValyutaEntity>
                        obj.saved = true
                        obj.saved = savedValyuta.contains(obj)
                        all.add(obj)
                    }
                    list.value = all
                    setAdapter()
                }
            }
        })
        return list
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}