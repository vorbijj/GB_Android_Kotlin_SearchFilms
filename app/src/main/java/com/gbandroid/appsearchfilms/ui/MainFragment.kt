package com.gbandroid.appsearchfilms.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gbandroid.appsearchfilms.R
import com.gbandroid.appsearchfilms.databinding.FragmentMainBinding
import com.gbandroid.appsearchfilms.util.MAIN_SERVICE_STRING_EXTRA
import com.gbandroid.appsearchfilms.util.MainMonitorIntentService
import com.gbandroid.appsearchfilms.util.showSnackBar
import com.gbandroid.appsearchfilms.viewmodel.ListFilmsKit
import com.gbandroid.appsearchfilms.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val BRACKET_LEFT = "("
private const val BRACKET_RIGHT = ")"


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(
            inflater, container, false
        )

        initRecyclerView(binding.topRecyclerViewLine)
        initRecyclerView(binding.newRecyclerViewLine)
        initRecyclerView(binding.comingSoonRecyclerViewLine)
        initRecyclerView(binding.fantasyRecyclerViewLine)
        initRecyclerView(binding.dramaRecyclerViewLine)

        return binding.root
    }


    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )

        when (recyclerView) {
            binding.topRecyclerViewLine -> {
                viewModel.getTopListFilms()

                viewModel.topValidationErrorLiveData.observe(viewLifecycleOwner) {
                    if (it) {
                        showErrorMsg()
                    } else {
                        val str = BRACKET_LEFT + viewModel.topListFilmsLiveData.value!!.size()
                            .toString() + BRACKET_RIGHT
                        binding.quantityTopTextView.text = str

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
            }

            binding.newRecyclerViewLine -> {
                viewModel.getNewListFilms()

                viewModel.newValidationErrorLiveData.observe(viewLifecycleOwner) {
                    if (it) {
                        showErrorMsg()
                    } else {
                        val str = BRACKET_LEFT + viewModel.newListFilmsLiveData.value!!.size()
                            .toString() + BRACKET_RIGHT
                        binding.quantityNewTextView.text = str

                        val adapter = FilmAdapter(viewModel.newListFilmsLiveData.value!!)
                        recyclerView.adapter = adapter

                        adapter.setOnItemClickListener(object : FilmAdapter.OnItemClickListener {
                            override fun onItemClick(view: View?, position: Int) {
                                viewModel.setCurrentCard(position, ListFilmsKit.LIST_NEW)
                                showCard()
                            }
                        })
                    }
                }
            }

            binding.comingSoonRecyclerViewLine -> {
                viewModel.getComingSoonListFilms()

                viewModel.comingSoonValidationErrorLiveData.observe(viewLifecycleOwner) {
                    if (it) {
                        showErrorMsg()
                    } else {
                        val str =
                            BRACKET_LEFT + viewModel.comingSoonListFilmsLiveData.value!!.size()
                                .toString() + BRACKET_RIGHT
                        binding.quantityComingSoonTextView.text = str

                        val adapter = FilmAdapter(viewModel.comingSoonListFilmsLiveData.value!!)
                        recyclerView.adapter = adapter

                        adapter.setOnItemClickListener(object : FilmAdapter.OnItemClickListener {
                            override fun onItemClick(view: View?, position: Int) {
                                viewModel.setCurrentCard(position, ListFilmsKit.LIST_COMING_SOON)
                                showCard()
                            }
                        })
                    }
                }
            }

            binding.fantasyRecyclerViewLine -> {
                viewModel.getFantasyListFilms()

                viewModel.fantasyValidationErrorLiveData.observe(viewLifecycleOwner) {
                    if (it) {
                        showErrorMsg()
                    } else {
                        val str = BRACKET_LEFT + viewModel.fantasyListFilmsLiveData.value!!.size()
                            .toString() + BRACKET_RIGHT
                        binding.quantityFantasyTextView.text = str

                        val adapter = FilmAdapter(viewModel.fantasyListFilmsLiveData.value!!)
                        recyclerView.adapter = adapter

                        adapter.setOnItemClickListener(object : FilmAdapter.OnItemClickListener {
                            override fun onItemClick(view: View?, position: Int) {
                                viewModel.setCurrentCard(position, ListFilmsKit.LIST_FANTASY)
                                showCard()
                            }
                        })
                    }
                }

            }

            binding.dramaRecyclerViewLine -> {
                viewModel.getDramaListFilms()

                viewModel.dramaValidationErrorLiveData.observe(viewLifecycleOwner) {
                    if (it) {
                        showErrorMsg()
                    } else {
                        val str = BRACKET_LEFT + viewModel.dramaListFilmsLiveData.value!!.size()
                            .toString() + BRACKET_RIGHT
                        binding.quantityDramaTextView.text = str

                        val adapter = FilmAdapter(viewModel.dramaListFilmsLiveData.value!!)
                        recyclerView.adapter = adapter

                        adapter.setOnItemClickListener(object : FilmAdapter.OnItemClickListener {
                            override fun onItemClick(view: View?, position: Int) {
                                viewModel.setCurrentCard(position, ListFilmsKit.LIST_DRAMA)
                                showCard()
                            }
                        })
                    }
                }

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
        launchingMonitorService()

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

    private fun launchingMonitorService() {
        val message = "Запуск фрагмента описания фильма"
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val currentDateandTime: String = sdf.format(Date())
        val intent = Intent(requireActivity(), MainMonitorIntentService::class.java)
        intent.putExtra(MAIN_SERVICE_STRING_EXTRA, "$currentDateandTime - $message\n")
        requireActivity().startService(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}