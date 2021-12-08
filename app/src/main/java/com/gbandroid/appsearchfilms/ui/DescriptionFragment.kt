package com.gbandroid.appsearchfilms.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.gbandroid.appsearchfilms.R
import com.gbandroid.appsearchfilms.data.CardFilm
import com.gbandroid.appsearchfilms.viewmodel.MainViewModel

class DescriptionFragment : Fragment() {

    companion object {
        fun newInstance() = DescriptionFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_description, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initUi()
        return view
    }

    private fun initUi() {
        val nameView: TextView = requireView().findViewById(R.id.name_text_view)
        nameView.text = viewModel.CardLiveData.value?.name ?: "Тут пока пусто"
    }
}