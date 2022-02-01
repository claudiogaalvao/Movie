package com.claudiogalvaodev.moviemanager.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.claudiogalvaodev.moviemanager.R
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
    private val movieId by lazy {
        args.movieId.toInt()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    binding.fragmentMovieDetailsHeader.fragmentMovieDetailsTitle.text = it.title
                    binding.fragmentMovieDetailsHeader.fragmentMovieDetailsRelease.text = formatUtils.dateFromAmericanFormatToDateWithMonthName(it.release_date)
                    binding.fragmentMovieDetailsHeader.fragmentMovieDetailsGenre.text = it.getGenres()

                    if(it.runtime == 0) {
                        binding.fragmentMovieDetailsHeader.fragmentMovieDetailsDuration.visibility = View.GONE
                    } else {
                        binding.fragmentMovieDetailsHeader.fragmentMovieDetailsDuration.text = it.getDuration()
                    }

                    if(it.vote_average == 0.0) {
                        binding.fragmentMovieDetailsHeader.fragmentMovieDetailsRate.visibility = View.GONE
                        binding.fragmentMovieDetailsHeader.fragmentMovieDetailsImdbLogo.visibility = View.GONE
                    } else {
                        binding.fragmentMovieDetailsHeader.fragmentMovieDetailsRate.text = rate
                    }

                    Picasso.with(binding.root.context).load(it.getPoster()).into(binding.fragmentMovieDetailsHeader.fragmentMovieDetailsCover)

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
                stars?.let { allEmployes ->
                    configStarsList(allEmployes, allEmployes.take(calcCountStarsImage()))
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
            binding.fragmentMovieDetailsAvailableOnRecyclerview.visibility = View.GONE
            binding.fragmentMovieDetailsAvailableOnMessage.visibility = View.VISIBLE
            return
        }
        binding.fragmentMovieDetailsAvailableOnRecyclerview.visibility = View.VISIBLE
        binding.fragmentMovieDetailsAvailableOnMessage.visibility = View.GONE

        val circleAdapter = generateInstanceOfCircleAdapter()
        binding.fragmentMovieDetailsAvailableOnRecyclerview.apply {
            adapter = circleAdapter
        }
        circleAdapter.submitList(provider)
    }

    private fun configDirectorsList(employe: List<Employe>) {
        if(employe.isEmpty()) {
            binding.fragmentMovieDetailsDirectors.text = resources.getString(R.string.movie_details_directors_message)
            binding.fragmentMovieDetailsDirectors.maxLines = 2
            return
        }
        binding.fragmentMovieDetailsDirectors.maxLines = 1
        binding.fragmentMovieDetailsDirectors.text = viewModel.getDirectorsName()

        val circleAdapter = generateInstanceOfCircleAdapter()
        binding.fragmentMovieDetailsDirectorsRecyclerview.apply {
            adapter = circleAdapter
        }
        circleAdapter.submitList(employe)
    }

    private fun configStarsList(allEmployes: List<Employe>, employesToShowFirst: List<Employe>) {
        if(employesToShowFirst.isEmpty()) {
            binding.fragmentMovieDetailsStarsName.text = resources.getString(R.string.movie_details_stars_message)
            binding.fragmentMovieDetailsStarsName.maxLines = 2
            return
        }
        binding.fragmentMovieDetailsStarsName.maxLines = 1
        binding.fragmentMovieDetailsStarsName.text = viewModel.getStarsName()

        val circleAdapter = generateInstanceOfCircleAdapter()
        binding.fragmentMovieDetailsStarsRecyclerview.apply {
            adapter = circleAdapter
        }
        circleAdapter.submitList(employesToShowFirst)
        setupShowAllStars(allEmployes, employesToShowFirst)
    }

    private fun setupShowAllStars(
        allEmployes: List<Employe>,
        employesToShowFirst: List<Employe>
    ) {
        if (allEmployes.size > employesToShowFirst.size) {
            binding.fragmentMovieDetailsStarsSeeMore.visibility = View.VISIBLE
            viewModel.stars.value?.let { employesCompleteList ->
                binding.fragmentMovieDetailsStarsSeeMore.setOnClickListener {
                    goToPeopleAndCompanies(employesCompleteList)
                }
            }

        } else {
            binding.fragmentMovieDetailsStarsSeeMore.visibility = View.GONE
        }
    }

    private fun configCompaniesList(companies: List<Company>) {
        if(companies.isEmpty()) {
            binding.fragmentMovieDetailsCompanies.text = resources.getString(R.string.movie_details_companies_message)
            binding.fragmentMovieDetailsCompanies.maxLines = 2
            return
        }
        binding.fragmentMovieDetailsCompanies.maxLines = 1
        binding.fragmentMovieDetailsCompanies.text = viewModel.getCompaniesName()

        val circleAdapter = generateInstanceOfCircleAdapter()
        binding.fragmentMovieDetailsCompaniesRecyclerview.apply {
            adapter = circleAdapter
        }
        circleAdapter.submitList(companies)
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
            val simplePosterAdapter = SimplePosterWithTitleAdapter().apply {
                onItemClick = { movie ->
                    if(movie.id != movieId) goToMovieDetails(movie)
                }
            }
            adapter = simplePosterAdapter
            simplePosterAdapter.submitList(collection)
        }
    }

    private fun generateInstanceOfCircleAdapter(): CircleAdapter {
        val circleAdapter = CircleAdapter()
        circleAdapter.onClickListener = { employeSelected ->
            goToPeopleDetails(employeSelected)
        }
        circleAdapter.onLongClickListener = { imageDescription ->
            Toast.makeText(context, imageDescription, Toast.LENGTH_LONG).show()
        }
        return circleAdapter
    }

    private fun calcCountStarsImage(): Int {
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        val spaceBetween = 12
        val marginStart = 16
        val marginEnd = 16
        val spaceSeeAll = 50
        val widthEachImage = 50

        var countImages = dpWidth - marginStart - spaceSeeAll - marginEnd
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

    private fun goToPeopleDetails(employe: Employe) {
        val directions = MovieDetailsFragmentDirections
            .actionMovieDetailsFragmentToPeopleDetailsFragment(employe)
        findNavController().navigate(directions)
    }
}