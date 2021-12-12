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
import com.gbandroid.appsearchfilms.databinding.FragmentDescriptionBinding
import com.gbandroid.appsearchfilms.util.showSnackBar
import com.gbandroid.appsearchfilms.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_description.*

class DescriptionFragment : Fragment() {

    companion object {
        fun newInstance() = DescriptionFragment()
    }

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(
            inflater, container, false
        )
        initUi()
        return binding.root
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

    override fun onStart() {
        super.onStart()
        binding.root.showSnackBar("Фрагмент находится в разработке")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}