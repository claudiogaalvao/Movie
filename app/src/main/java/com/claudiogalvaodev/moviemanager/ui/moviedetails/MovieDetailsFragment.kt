package com.claudiogalvaodev.moviemanager.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.claudiogalvaodev.moviemanager.databinding.FragmentMovieDetailsBinding
import com.claudiogalvaodev.moviemanager.model.Company
import com.claudiogalvaodev.moviemanager.model.Employe
import com.claudiogalvaodev.moviemanager.model.Movie
import com.claudiogalvaodev.moviemanager.model.Provider
import com.claudiogalvaodev.moviemanager.ui.adapter.CircleAdapter
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterWithTitleAdapter
import com.claudiogalvaodev.moviemanager.utils.format.formatUtils
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class MovieDetailsFragment : Fragment() {
    private val viewModel: MovieDetailsViewModel by viewModel()
    private val binding by lazy {
        FragmentMovieDetailsBinding.inflate(layoutInflater)
    }

    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = args.movieId.toInt()

        (activity as MovieDetailsActivity).setToolbarTitle("")

        viewModel.getMovieDetails(movieId)
        viewModel.getMovieCredits(movieId)
        viewModel.getProviders(movieId)
        setObservables()
    }

    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            viewModel.movie.collectLatest { movie ->
                val rate = "${movie?.vote_average.toString()}/10"

                movie?.let {
                    movie.belongs_to_collection?.let { collection ->
                        viewModel.getMovieCollection(collection.id)
                    }
                    binding.fragmentMovieDetailsTitle.text = it.title
                    binding.fragmentMovieDetailsRelease.text = formatUtils.dateFromAmericanFormatToDateWithMonthName(it.release_date)
                    binding.fragmentMovieDetailsGender.text = it.getGenres()

                    if(it.runtime == 0) {
                        binding.fragmentMovieDetailsDuration.visibility = View.GONE
                    } else {
                        binding.fragmentMovieDetailsDuration.text = it.getDuration()
                    }

                    if(it.vote_average == 0.0) {
                        binding.fragmentMovieDetailsRate.visibility = View.GONE
                        binding.fragmentMovieDetailsImdbLogo.visibility = View.GONE
                    } else {
                        binding.fragmentMovieDetailsRate.text = rate
                    }

                    Picasso.with(binding.root.context).load(it.getPoster()).into(binding.fragmentMovieDetailsCover)

                    binding.fragmentMovieDetailsOverview.text = it.overview
                    if(it.budget == 0) {
                        binding.fragmentMovieDetailsBudgetLabel.visibility = View.GONE
                        binding.fragmentMovieDetailsBudget.visibility = View.GONE
                    } else {
                        binding.fragmentMovieDetailsBudget.text = formatUtils.unformattedNumberToCurrency(it.budget.toLong())
                    }

                }


            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.streamProviders.collectLatest { stream ->
                stream?.let {
                    configStreamProvidersList(it)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.directors.collectLatest { directors ->
                directors?.let {
                    configDirectorsList(it)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.stars.collectLatest { stars ->
                stars?.let {
                    configStarsList(it.take(calcCountStarsImage()))
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.companies.collectLatest { companies ->
                companies?.let {
                    configCompaniesList(it)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.collection.collectLatest { collection ->
                collection?.let {
                    configCollectionList(it)
                }
            }
        }
    }

    private fun configStreamProvidersList(provider: List<Provider>) {
        if(provider.isEmpty()) {
            binding.fragmentMovieDetailsAvailableOnLabel.visibility = View.GONE
            binding.fragmentMovieDetailsAvailableOnRecyclerview.visibility = View.GONE
            return
        }
        binding.fragmentMovieDetailsAvailableOnLabel.visibility = View.VISIBLE
        binding.fragmentMovieDetailsAvailableOnRecyclerview.visibility = View.VISIBLE
        binding.fragmentMovieDetailsAvailableOnRecyclerview.apply {
            adapter = CircleAdapter(provider)
        }
    }

    private fun configDirectorsList(employe: List<Employe>) {
        binding.fragmentMovieDetailsDirectors.text = viewModel.getDirectorsName()
        binding.fragmentMovieDetailsDirectorsRecyclerview.apply {
            adapter = CircleAdapter(employe)
        }
    }

    private fun configStarsList(employes: List<Employe>) {
        binding.fragmentMovieDetailsStars.text = viewModel.getStarsName()
        binding.fragmentMovieDetailsStarsRecyclerview.apply {
            adapter = CircleAdapter(employes)
        }

        viewModel.stars.value?.let { employesCompleteList ->
            binding.fragmentMovieDetailsStarsSeeMore.setOnClickListener {
                goToPeopleAndCompanies(employesCompleteList)
            }
        }

    }

    private fun configCompaniesList(companies: List<Company>) {
        binding.fragmentMovieDetailsCompanies.text = viewModel.getCompaniesName()
        binding.fragmentMovieDetailsCompaniesRecyclerview.apply {
            adapter = CircleAdapter(companies)
        }
    }

    private fun configCollectionList(collection: List<Movie>) {
        if(collection.isEmpty()) {
            binding.fragmentMovieDetailsCollectionSequenceLabel.visibility = View.GONE
            binding.fragmentMovieDetailsCollectionSequenceRecyclerview.visibility = View.GONE
            return
        }
        binding.fragmentMovieDetailsCollectionSequenceLabel.visibility = View.VISIBLE
        binding.fragmentMovieDetailsCollectionSequenceRecyclerview.visibility = View.VISIBLE

        binding.fragmentMovieDetailsCollectionSequenceRecyclerview.apply {
            adapter = SimplePosterWithTitleAdapter(collection).apply {
                onItemClick = { movie ->
                    goToMovieDetails(movie)
                }
            }
        }
    }

    private fun calcCountStarsImage(): Int {
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        val spaceBetween = 12
        val marginStart = 16
        val spaceSeeAll = 50
        val widthEachImage = 50

        var countImages = dpWidth - marginStart - spaceSeeAll
        countImages /= (widthEachImage+spaceBetween)
        return countImages.roundToInt()
    }

    private fun goToMovieDetails(movie: Movie) {
        val directions = MovieDetailsFragmentDirections
            .actionMovieDetailsFragmentToMovieDetailsFragment(movie.id.toString())
        findNavController().navigate(directions)
    }

    private fun goToPeopleAndCompanies(actors: List<Employe>) {
        val directions = MovieDetailsFragmentDirections
            .actionMovieDetailsFragmentToPeopleAndCompaniesFragment(actors.toTypedArray())
        findNavController().navigate(directions)
    }
}