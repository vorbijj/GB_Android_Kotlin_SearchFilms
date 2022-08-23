package com.gbandroid.appsearchfilms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.gbandroid.appsearchfilms.R
import com.gbandroid.appsearchfilms.databinding.FragmentDescriptionBinding
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoEntity
import com.gbandroid.appsearchfilms.util.showSnackBar
import com.gbandroid.appsearchfilms.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

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

        setHasOptionsMenu(true)
        requireActivity().toolbar.setNavigationIcon(
            getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24, null)
        )

        showProgress(true, false)
        initUi()
        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.setGroupVisible(R.id.group_menu, false)
    }

    private fun initUi() {
        val observerCurrent = Observer<TheMovieDBRepoEntity> { it ->
            val cardFilmEntity = viewModel.cardLiveData.value!!

            setImageOnView(cardFilmEntity.getImageUrl())
            binding.nameDescriptionTextView.text = cardFilmEntity.title
            binding.yearDescriptionTextView.text = cardFilmEntity.getYear()
            binding.ratingDescriptionTextView.text = cardFilmEntity.voteAverage.toString()
            binding.descDescriptionTextView.text = cardFilmEntity.overview

            showProgress(false, true)

            binding.root.showSnackBar("Фрагмент находится в разработке")
        }

        viewModel.cardLiveData.observe(viewLifecycleOwner, observerCurrent)

    }

    private fun showProgress(showProgressBar: Boolean, showDescriptionLinearLayout: Boolean) {
        binding.descriptionProgressBar.isVisible = showProgressBar
        binding.coverDescriptionImageView.isVisible = showDescriptionLinearLayout
        binding.nameDescriptionTextView.isVisible = showDescriptionLinearLayout
        binding.yearDescriptionTextView.isVisible = showDescriptionLinearLayout
        binding.ratingDescriptionTextView.isVisible = showDescriptionLinearLayout
        binding.descDescriptionTextView.isVisible = showDescriptionLinearLayout
    }

    private fun setImageOnView(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .into(binding.coverDescriptionImageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}