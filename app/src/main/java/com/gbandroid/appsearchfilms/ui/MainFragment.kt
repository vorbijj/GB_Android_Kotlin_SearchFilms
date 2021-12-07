package com.gbandroid.appsearchfilms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gbandroid.appsearchfilms.R
import com.gbandroid.appsearchfilms.data.CardsSource
import com.gbandroid.appsearchfilms.data.CardsSourceImpl
import com.gbandroid.appsearchfilms.viewmodel.FilmAdapter
import com.gbandroid.appsearchfilms.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val topRecyclerView = view.findViewById<RecyclerView>(R.id.top_recycler_view_line)
        val newRecyclerView = view.findViewById<RecyclerView>(R.id.new_recycler_view_line)
        val fantasyRecyclerView = view.findViewById<RecyclerView>(R.id.fantastic_recycler_view_line)
        val data: CardsSource = CardsSourceImpl()
        initRecyclerView(topRecyclerView, data)
        initRecyclerView(newRecyclerView, data)
        initRecyclerView(fantasyRecyclerView, data)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun initRecyclerView(recyclerView: RecyclerView, data: CardsSource) {
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        val adapter = FilmAdapter(data)
        recyclerView.adapter = adapter

        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator))
        recyclerView.addItemDecoration(itemDecoration)

        adapter.setOnItemClickListener(object : FilmAdapter.OnItemClickListener{
            override fun onItemClick(view: View?, position: Int) {
                Toast.makeText(
                    context, "Yeap!!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}