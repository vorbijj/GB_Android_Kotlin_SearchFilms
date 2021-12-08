package com.gbandroid.appsearchfilms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gbandroid.appsearchfilms.R
import com.gbandroid.appsearchfilms.data.CardFilm
import com.gbandroid.appsearchfilms.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_description.*

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
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        initUi()
        return view
    }


    private fun initUi() {
        val nameObserver = Observer<CardFilm> { cardFilm ->
            cover_description_image_view.setImageResource(R.drawable.ic_baseline_camera_75)
            name_description_text_view.text = cardFilm.name
            year_description_text_view.text = cardFilm.year
            rating_description_text_view.text = cardFilm.rating
        }

        viewModel.getCurrentCard().observe(viewLifecycleOwner, nameObserver)

    }
}