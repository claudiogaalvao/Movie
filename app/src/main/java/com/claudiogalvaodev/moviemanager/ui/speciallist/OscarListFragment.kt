package com.claudiogalvaodev.moviemanager.ui.speciallist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.claudiogalvaodev.moviemanager.databinding.FragmentOscarNominationBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterWithTitleAdapter
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsFragmentDirections
import com.claudiogalvaodev.moviemanager.utils.enums.ItemType
import com.claudiogalvaodev.moviemanager.utils.enums.OscarCategory
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel

class OscarListFragment: Fragment() {

    private val viewModel: SpecialListViewModel by viewModel()
    private val binding by lazy {
        FragmentOscarNominationBinding.inflate(layoutInflater)
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

    private fun configBestPictureAdapter() {
        bestPictureAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialListBestPictureRecyclerview.adapter = bestPictureAdapter
    }

    private fun configBestForeignLanguageAdapter() {
        bestForeignLanguageAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialListBestForeignLanguageFilmRecyclerview.adapter =
            bestForeignLanguageAdapter
    }

    private fun configBestDirectingAdapter() {
        bestDirectingAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialListBestDirectingRecyclerview.adapter = bestDirectingAdapter
    }

    private fun configBestActressAdapter() {
        bestActressAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialListBestActressRecyclerview.adapter = bestActressAdapter
    }

    private fun configBestActorAdapter() {
        bestActorAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialBestActorRecyclerview.adapter = bestActorAdapter
    }

    private fun configBestSupportingActressAdapter() {
        bestSupportingActressAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialListBestSupportingActressRecyclerview.adapter = bestSupportingActressAdapter
    }

    private fun configBestSupportingActorAdapter() {
        bestSupportingActorAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialBestSupportingActorRecyclerview.adapter = bestSupportingActorAdapter
    }

    private fun configBestAdaptedScreenplayAdapter() {
        bestAdaptedScreenplayAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialListBestAdaptedScreenplayRecyclerview.adapter = bestAdaptedScreenplayAdapter
    }

    private fun configBestOriginalScreenplayAdapter() {
        bestOriginalScreenplayAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialBestOriginalScreenplayRecyclerview.adapter = bestOriginalScreenplayAdapter
    }

    private fun configBestCostumeDesignAdapter() {
        bestCostumeDesignAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialListBestCostumeDesignRecyclerview.adapter = bestCostumeDesignAdapter
    }

    private fun configBestOriginalScoreAdapter() {
        bestOriginalScoreAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialBestOriginalScoreRecyclerview.adapter = bestOriginalScoreAdapter
    }

    private fun configBestAnimatedFeatureFilmAdapter() {
        bestAnimatedFeatureFilmAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialListBestAnimatedFeatureFilmRecyclerview.adapter = bestAnimatedFeatureFilmAdapter
    }

    private fun configBestAnimatedShortAdapter() {
        bestAnimatedShortAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialBestAnimatedShortRecyclerview.adapter = bestAnimatedShortAdapter
    }

    private fun configBestLiveActionShortAdapter() {
        bestLiveActionShortAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialListBestLiveActionShortRecyclerview.adapter = bestLiveActionShortAdapter
    }

    private fun configBestDocumentaryFeatureAdapter() {
        bestDocumentaryFeatureAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialBestDocumentaryFeatureRecyclerview.adapter = bestDocumentaryFeatureAdapter
    }

    private fun configBestDocumentaryShortAdapter() {
        bestDocumentaryShortAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialListBestDocumentaryShortRecyclerview.adapter = bestDocumentaryShortAdapter
    }

    private fun configBestSoundMixingAdapter() {
        bestSoundMixingAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialBestSoundMixingRecyclerview.adapter = bestSoundMixingAdapter
    }

    private fun configBestOriginalSongAdapter() {
        bestOriginalSongAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialListBestOriginalSongRecyclerview.adapter = bestOriginalSongAdapter
    }

    private fun configBestMakeupAndHairstylingAdapter() {
        bestMakeupAndHairstylingAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialBestMakeupAndHairstylingRecyclerview.adapter = bestMakeupAndHairstylingAdapter
    }

    private fun configBestVisualEffectsAdapter() {
        bestVisualEffectsAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialListBestVisualEffectsRecyclerview.adapter = bestVisualEffectsAdapter
    }

    private fun configBestCinematographyAdapter() {
        bestCinematographyAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialBestBestCinematographyRecyclerview.adapter = bestCinematographyAdapter
    }

    private fun configBestEditingAdapter() {
        bestEditingAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialListBestEditingRecyclerview.adapter = bestEditingAdapter
    }

    private fun configProductionDesignAdapter() {
        bestProductionDesignAdapter = SimplePosterWithTitleAdapter().apply {
            onItemClick = { itemId, type, leastOneMovieId -> onItemClickListener(itemId, type, leastOneMovieId) }
        }
        binding.fragmentSpecialBestProductionDesignRecyclerview.adapter = bestProductionDesignAdapter
    }

    private fun setObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.oscarNomination.collectLatest { oscarNomination ->
                bestPictureAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_PICTURE) })
                bestForeignLanguageAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_FOREIGN_LANGUAGE_FILM) })
                bestDirectingAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_DIRECTING) })
                bestActressAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_ACTRESS) })
                bestActorAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_ACTOR) })
                bestSupportingActressAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_SUPPORTING_ACTRESS) })
                bestSupportingActorAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_SUPPORTING_ACTOR) })
                bestAdaptedScreenplayAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_ADAPTED_SCREENPLAY) })
                bestOriginalScreenplayAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_ORIGINAL_SCREENPLAY) })
                bestCostumeDesignAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_COSTUME_DESIGN) })
                bestOriginalScoreAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_ORIGINAL_SCORE) })
                bestAnimatedFeatureFilmAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_ANIMATED_FEATURE_FILM) })
                bestAnimatedShortAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_ANIMATED_SHORT) })
                bestLiveActionShortAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_LIVE_ACTION_SHORT) })
                bestDocumentaryFeatureAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_DOCUMENTARY_FEATURE) })
                bestDocumentaryShortAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_DOCUMENTARY_SHORT) })
                bestSoundMixingAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_SOUND_MIXING) })
                bestOriginalSongAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_ORIGINAL_SONG) })
                bestMakeupAndHairstylingAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_MAKEUP_AND_HAIRSTYLING) })
                bestVisualEffectsAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_VISUAL_EFFECTS) })
                bestCinematographyAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_CINEMATOGRAPHY) })
                bestEditingAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_EDITING) })
                bestProductionDesignAdapter.submitList(oscarNomination.filter { nomination ->
                    nomination.categories.contains(OscarCategory.BEST_PRODUCTION_DESIGN) })
                if(oscarNomination.isNotEmpty()) showAllMovieNominationsTitle()
            }
        }
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