package com.claudiogalvaodev.moviemanager.ui.speciallist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.claudiogalvaodev.moviemanager.data.model.SpecialItem
import com.claudiogalvaodev.moviemanager.databinding.FragmentOscarNominationBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterWithTitleAdapter
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import com.claudiogalvaodev.moviemanager.utils.enums.ItemType
import com.claudiogalvaodev.moviemanager.utils.enums.OscarCategory
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class OscarListFragment: Fragment() {

    private lateinit var viewModel: SpecialListViewModel
    private val binding by lazy {
        FragmentOscarNominationBinding.inflate(layoutInflater)
    }

    private val args: OscarListFragmentArgs by navArgs()
    private val eventId by lazy {
        args.eventId
    }

    private lateinit var bestPictureAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestForeignLanguageAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestDirectingAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestActressAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestActorAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestSupportingActressAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestSupportingActorAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestAdaptedScreenplayAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestOriginalScreenplayAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestCostumeDesignAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestOriginalScoreAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestAnimatedFeatureFilmAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestAnimatedShortAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestLiveActionShortAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestDocumentaryFeatureAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestDocumentaryShortAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestSoundMixingAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestOriginalSongAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestMakeupAndHairstylingAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestVisualEffectsAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestCinematographyAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestEditingAdapter: SimplePosterWithTitleAdapter
    private lateinit var bestProductionDesignAdapter: SimplePosterWithTitleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel { parametersOf(eventId) }

        setupAdapter()
        setObservers()
    }

    private fun setupAdapter() {
        configBestPictureAdapter()
        configBestForeignLanguageAdapter()
        configBestDirectingAdapter()
        configBestActressAdapter()
        configBestActorAdapter()
        configBestSupportingActressAdapter()
        configBestSupportingActorAdapter()
        configBestAdaptedScreenplayAdapter()
        configBestOriginalScreenplayAdapter()
        configBestCostumeDesignAdapter()
        configBestOriginalScoreAdapter()
        configBestAnimatedFeatureFilmAdapter()
        configBestAnimatedShortAdapter()
        configBestLiveActionShortAdapter()
        configBestDocumentaryFeatureAdapter()
        configBestDocumentaryShortAdapter()
        configBestSoundMixingAdapter()
        configBestOriginalSongAdapter()
        configBestMakeupAndHairstylingAdapter()
        configBestVisualEffectsAdapter()
        configBestCinematographyAdapter()
        configBestEditingAdapter()
        configProductionDesignAdapter()
    }

    private fun onItemClickListener(itemId: Int, type: ItemType, leastOneMovieId: Int) {
        when (type) {
            ItemType.MOVIE -> {
                goToMovieDetails(itemId)
            }
            ItemType.PERSON -> {
                goToPeopleDetails(itemId, leastOneMovieId)
            }
        }
    }

    private fun setObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.oscarNomination.collectLatest { oscarNomination ->
                bestPictureAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_PICTURE))
                bestForeignLanguageAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_FOREIGN_LANGUAGE_FILM))
                bestDirectingAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_DIRECTING))
                bestActressAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_ACTRESS))
                bestActorAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_ACTOR))
                bestSupportingActressAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_SUPPORTING_ACTRESS))
                bestSupportingActorAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_SUPPORTING_ACTOR))
                bestAdaptedScreenplayAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_ADAPTED_SCREENPLAY))
                bestOriginalScreenplayAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_ORIGINAL_SCREENPLAY))
                bestCostumeDesignAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_COSTUME_DESIGN))
                bestOriginalScoreAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_ORIGINAL_SCORE))
                bestAnimatedFeatureFilmAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_ANIMATED_FEATURE_FILM))
                bestAnimatedShortAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_ANIMATED_SHORT))
                bestLiveActionShortAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_LIVE_ACTION_SHORT))
                bestDocumentaryFeatureAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_DOCUMENTARY_FEATURE))
                bestDocumentaryShortAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_DOCUMENTARY_SHORT))
                bestSoundMixingAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_SOUND_MIXING))
                bestOriginalSongAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_ORIGINAL_SONG))
                bestMakeupAndHairstylingAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_MAKEUP_AND_HAIRSTYLING))
                bestVisualEffectsAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_VISUAL_EFFECTS))
                bestCinematographyAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_CINEMATOGRAPHY))
                bestEditingAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_EDITING))
                bestProductionDesignAdapter.submitList(returnItemsSorted(oscarNomination, OscarCategory.BEST_PRODUCTION_DESIGN))
                if(oscarNomination.isNotEmpty()) showAllMovieNominationsTitle()
            }
        }
    }

    private fun returnItemsSorted(items: List<SpecialItem>, filterByOscarCategory: OscarCategory): List<SpecialItem> {
        val itemsByCurrentCategory = items.filter { item ->
            item.categories.contains(filterByOscarCategory.name)
        }.toMutableList()
        val winnerFromCurrentCategory = itemsByCurrentCategory.filter { item ->
            item.categoriesWinner.contains(filterByOscarCategory.name)
        }
        itemsByCurrentCategory.sortBy { it.title }
        if(winnerFromCurrentCategory.isNotEmpty()) {
            itemsByCurrentCategory.reverse()
            itemsByCurrentCategory.remove(winnerFromCurrentCategory.first())
            itemsByCurrentCategory.add(winnerFromCurrentCategory.first())
            itemsByCurrentCategory.reverse()
        }
        return itemsByCurrentCategory
    }

    private fun showAllMovieNominationsTitle() {
        binding.fragmentSpecialListBestPictureTitle.visibility = View.VISIBLE
        binding.fragmentSpecialListBestForeignLanguageFilmTitle.visibility = View.VISIBLE
        binding.fragmentSpecialListBestDirectingTitle.visibility = View.VISIBLE
        binding.fragmentSpecialListBestActressTitle.visibility = View.VISIBLE
        binding.fragmentSpecialBestActorTitle.visibility = View.VISIBLE
        binding.fragmentSpecialListBestSupportingActressTitle.visibility = View.VISIBLE
        binding.fragmentSpecialBestSupportingActorTitle.visibility = View.VISIBLE
        binding.fragmentSpecialListBestAdaptedScreenplayTitle.visibility = View.VISIBLE
        binding.fragmentSpecialBestOriginalScreenplayTitle.visibility = View.VISIBLE
        binding.fragmentSpecialListBestCostumeDesignTitle.visibility = View.VISIBLE
        binding.fragmentSpecialBestOriginalScoreTitle.visibility = View.VISIBLE
        binding.fragmentSpecialListBestAnimatedFeatureFilmTitle.visibility = View.VISIBLE
        binding.fragmentSpecialBestAnimatedShortTitle.visibility = View.VISIBLE
        binding.fragmentSpecialListBestLiveActionShortTitle.visibility = View.VISIBLE
        binding.fragmentSpecialBestDocumentaryFeatureTitle.visibility = View.VISIBLE
        binding.fragmentSpecialListBestDocumentaryShortTitle.visibility = View.VISIBLE
        binding.fragmentSpecialBestSoundMixingTitle.visibility = View.VISIBLE
        binding.fragmentSpecialListBestOriginalSongTitle.visibility = View.VISIBLE
        binding.fragmentSpecialBestMakeupAndHairstylingTitle.visibility = View.VISIBLE
        binding.fragmentSpecialListBestVisualEffectsTitle.visibility = View.VISIBLE
        binding.fragmentSpecialBestCinematographyTitle.visibility = View.VISIBLE
        binding.fragmentSpecialListBestEditingTitle.visibility = View.VISIBLE
        binding.fragmentSpecialBestProductionDesignTitle.visibility = View.VISIBLE
    }

    private fun configBestPictureAdapter() {
        bestPictureAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_PICTURE
        }
        binding.fragmentSpecialListBestPictureRecyclerview.adapter = bestPictureAdapter
    }

    private fun configBestForeignLanguageAdapter() {
        bestForeignLanguageAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_FOREIGN_LANGUAGE_FILM
        }
        binding.fragmentSpecialListBestForeignLanguageFilmRecyclerview.adapter =
            bestForeignLanguageAdapter
    }

    private fun configBestDirectingAdapter() {
        bestDirectingAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_DIRECTING
        }
        binding.fragmentSpecialListBestDirectingRecyclerview.adapter = bestDirectingAdapter
    }

    private fun configBestActressAdapter() {
        bestActressAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_ACTRESS
        }
        binding.fragmentSpecialListBestActressRecyclerview.adapter = bestActressAdapter
    }

    private fun configBestActorAdapter() {
        bestActorAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_ACTOR
        }
        binding.fragmentSpecialBestActorRecyclerview.adapter = bestActorAdapter
    }

    private fun configBestSupportingActressAdapter() {
        bestSupportingActressAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_SUPPORTING_ACTRESS
        }
        binding.fragmentSpecialListBestSupportingActressRecyclerview.adapter = bestSupportingActressAdapter
    }

    private fun configBestSupportingActorAdapter() {
        bestSupportingActorAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_SUPPORTING_ACTOR
        }
        binding.fragmentSpecialBestSupportingActorRecyclerview.adapter = bestSupportingActorAdapter
    }

    private fun configBestAdaptedScreenplayAdapter() {
        bestAdaptedScreenplayAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_ADAPTED_SCREENPLAY
        }
        binding.fragmentSpecialListBestAdaptedScreenplayRecyclerview.adapter = bestAdaptedScreenplayAdapter
    }

    private fun configBestOriginalScreenplayAdapter() {
        bestOriginalScreenplayAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_ORIGINAL_SCREENPLAY
        }
        binding.fragmentSpecialBestOriginalScreenplayRecyclerview.adapter = bestOriginalScreenplayAdapter
    }

    private fun configBestCostumeDesignAdapter() {
        bestCostumeDesignAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_COSTUME_DESIGN
        }
        binding.fragmentSpecialListBestCostumeDesignRecyclerview.adapter = bestCostumeDesignAdapter
    }

    private fun configBestOriginalScoreAdapter() {
        bestOriginalScoreAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_ORIGINAL_SCORE
        }
        binding.fragmentSpecialBestOriginalScoreRecyclerview.adapter = bestOriginalScoreAdapter
    }

    private fun configBestAnimatedFeatureFilmAdapter() {
        bestAnimatedFeatureFilmAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_ANIMATED_FEATURE_FILM
        }
        binding.fragmentSpecialListBestAnimatedFeatureFilmRecyclerview.adapter = bestAnimatedFeatureFilmAdapter
    }

    private fun configBestAnimatedShortAdapter() {
        bestAnimatedShortAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_ANIMATED_SHORT
        }
        binding.fragmentSpecialBestAnimatedShortRecyclerview.adapter = bestAnimatedShortAdapter
    }

    private fun configBestLiveActionShortAdapter() {
        bestLiveActionShortAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_LIVE_ACTION_SHORT
        }
        binding.fragmentSpecialListBestLiveActionShortRecyclerview.adapter = bestLiveActionShortAdapter
    }

    private fun configBestDocumentaryFeatureAdapter() {
        bestDocumentaryFeatureAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_DOCUMENTARY_FEATURE
        }
        binding.fragmentSpecialBestDocumentaryFeatureRecyclerview.adapter = bestDocumentaryFeatureAdapter
    }

    private fun configBestDocumentaryShortAdapter() {
        bestDocumentaryShortAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_DOCUMENTARY_SHORT
        }
        binding.fragmentSpecialListBestDocumentaryShortRecyclerview.adapter = bestDocumentaryShortAdapter
    }

    private fun configBestSoundMixingAdapter() {
        bestSoundMixingAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_SOUND_MIXING
        }
        binding.fragmentSpecialBestSoundMixingRecyclerview.adapter = bestSoundMixingAdapter
    }

    private fun configBestOriginalSongAdapter() {
        bestOriginalSongAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_ORIGINAL_SONG
        }
        binding.fragmentSpecialListBestOriginalSongRecyclerview.adapter = bestOriginalSongAdapter
    }

    private fun configBestMakeupAndHairstylingAdapter() {
        bestMakeupAndHairstylingAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_MAKEUP_AND_HAIRSTYLING
        }
        binding.fragmentSpecialBestMakeupAndHairstylingRecyclerview.adapter = bestMakeupAndHairstylingAdapter
    }

    private fun configBestVisualEffectsAdapter() {
        bestVisualEffectsAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_VISUAL_EFFECTS
        }
        binding.fragmentSpecialListBestVisualEffectsRecyclerview.adapter = bestVisualEffectsAdapter
    }

    private fun configBestCinematographyAdapter() {
        bestCinematographyAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_CINEMATOGRAPHY
        }
        binding.fragmentSpecialBestBestCinematographyRecyclerview.adapter = bestCinematographyAdapter
    }

    private fun configBestEditingAdapter() {
        bestEditingAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_EDITING
        }
        binding.fragmentSpecialListBestEditingRecyclerview.adapter = bestEditingAdapter
    }

    private fun configProductionDesignAdapter() {
        bestProductionDesignAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
            oscarCategory = OscarCategory.BEST_PRODUCTION_DESIGN
        }
        binding.fragmentSpecialBestProductionDesignRecyclerview.adapter = bestProductionDesignAdapter
    }

    private fun goToMovieDetails(movieId: Int) {
        context?.let {
            startActivity(MovieDetailsActivity.newInstance(it, movieId))
        }
    }

    private fun goToPeopleDetails(personId: Int, leastOneMovieId: Int) {
        val directions = OscarListFragmentDirections
            .actionOscarListFragmentToPeopleDetailsFragment(personId.toLong(), leastOneMovieId.toLong())
        findNavController().navigate(directions)
    }

}