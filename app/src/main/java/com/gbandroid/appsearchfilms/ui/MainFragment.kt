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
import com.gbandroid.appsearchfilms.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

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
        initRecyclerView(binding.fantasticRecyclerViewLine)

        return binding.root
    }


    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )

        val adapter = FilmAdapter(viewModel.getCardsSource())
        recyclerView.adapter = adapter

        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator))
        recyclerView.addItemDecoration(itemDecoration)

        adapter.setOnItemClickListener(object : FilmAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                launchingMonitorService()

                viewModel.setCurrentCard(position)
                Thread.sleep(500)

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
        })
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