package com.gbandroid.appsearchfilms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gbandroid.appsearchfilms.R
import com.gbandroid.appsearchfilms.databinding.FragmentSearchBinding
import com.gbandroid.appsearchfilms.util.showSnackBar
import com.gbandroid.appsearchfilms.viewmodel.ListFilmsKit
import com.gbandroid.appsearchfilms.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var searchText: String? = null
    private var adultPreferences by Delegates.notNull<Boolean>()

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: Boolean) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putBoolean(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            searchText = it.getString(ARG_PARAM1)
            adultPreferences = it.getBoolean(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(
            inflater, container, false
        )

        setHasOptionsMenu(true)
        requireActivity().toolbar.setNavigationIcon(
            getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24, null)
        )

        searchText?.let { binding.testText.text = it }

        initRecyclerView(binding.searchRecyclerViewLine)

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.setGroupVisible(R.id.group_menu, false)
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )

        viewModel.getTopListFilms(adultPreferences)

        viewModel.topValidationErrorLiveData.observe(viewLifecycleOwner) {
            if (it) {
                showErrorMsg()
            } else {
                val str = "(" + viewModel.topListFilmsLiveData.value!!.size().toString() + ")"
                binding.quantitySearchTextView.text = str

                val adapter = FilmAdapter(viewModel.topListFilmsLiveData.value!!)
                recyclerView.adapter = adapter

                adapter.setOnItemClickListener(object : FilmAdapter.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        viewModel.setCurrentCard(position, ListFilmsKit.LIST_TOP)
                        showCard()
                    }
                })
            }
        }

        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator))
        recyclerView.addItemDecoration(itemDecoration)
    }

    private fun showErrorMsg() {
        val onError = viewModel.onErrorLiveData.value
        binding.root.showSnackBar("Attention!!!\n $onError")
    }

    private fun showCard() {
        val fragmentManager = requireActivity().supportFragmentManager
        val currentFragment = fragmentManager.findFragmentById(R.id.container)
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (currentFragment != null) {
            fragmentTransaction.remove(currentFragment)
        }
        fragmentTransaction.add(R.id.container, DescriptionFragment.newInstance())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}