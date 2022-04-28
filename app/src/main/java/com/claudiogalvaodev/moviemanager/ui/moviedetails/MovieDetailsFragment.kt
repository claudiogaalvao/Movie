package com.claudiogalvaodev.moviemanager.ui.moviedetails

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSaved
import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import com.claudiogalvaodev.moviemanager.data.model.Company
import com.claudiogalvaodev.moviemanager.data.model.Employe
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.data.model.Provider
import com.claudiogalvaodev.moviemanager.databinding.CustomBottomsheetBinding
import com.claudiogalvaodev.moviemanager.databinding.FragmentMovieDetailsBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.CircleAdapter
import com.claudiogalvaodev.moviemanager.ui.adapter.MyListsAdapter
import com.claudiogalvaodev.moviemanager.ui.adapter.SimpleOptionsAdapter
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterWithTitleAdapter
import com.claudiogalvaodev.moviemanager.utils.format.formatUtils
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.roundToInt

class MovieDetailsFragment : Fragment() {

    private lateinit var viewModel: MovieDetailsViewModel
    private val binding by lazy {
        FragmentMovieDetailsBinding.inflate(layoutInflater)
    }

    private val args: MovieDetailsFragmentArgs by navArgs()
    private val movieId by lazy {
        args.movieId
    }
    private val releaseDate by lazy {
        args.releaseDate
    }
    private lateinit var moviesSaved: List<MovieSaved>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel { parametersOf(movieId) }

        (activity as MovieDetailsActivity).setToolbarTitle("")

        setObservables()
        setListeners()
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
                    binding.fragmentMovieDetailsHeader.fragmentMovieDetailsRelease.text = formatUtils
                        .dateFromAmericanFormatToDateWithMonthName(if (releaseDate.isBlank()) it.release_date else releaseDate)

                    val genresAdapter = SimpleOptionsAdapter()
                    binding.fragmentMovieDetailsHeader.genreRecyclerview.adapter = genresAdapter
                    genresAdapter.submitList(it.getGenresStringList())

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

                    if(it.overview.isBlank()) {
                        binding.fragmentMovieDetailsOverviewLabel.visibility = View.GONE
                    } else {
                        binding.fragmentMovieDetailsOverview.text = it.overview
                    }

                    if(it.budget == 0L) {
                        binding.fragmentMovieDetailsBudgetLabel.visibility = View.GONE
                        binding.fragmentMovieDetailsBudget.visibility = View.GONE
                    } else {
                        binding.fragmentMovieDetailsBudget.text = formatUtils.unformattedNumberToCurrency(it.budget)
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

        lifecycleScope.launchWhenStarted {
            viewModel.moviesSaved.collectLatest { movies ->
                moviesSaved = movies
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isMovieSaved.collectLatest { isSaved ->
                if (isSaved) {
                    binding.fragmentMovieDetailsHeader.addToListIcon.setImageResource(R.drawable.ic_done)
                    binding.fragmentMovieDetailsHeader.addToListText.text = resources.getString(R.string.saved_to_list)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.videos.collectLatest { videos ->
                Log.i("videos", videos.size.toString())
            }
        }
    }

    private fun setListeners() {
        binding.fragmentMovieDetailsHeader.addToListParent.setOnClickListener {
            showMyListsOptions()
        }
    }

    private fun showMyListsOptions() {
        context?.let {
            val dialog = Dialog(it)
            val dialogBinding = CustomBottomsheetBinding.inflate(layoutInflater)

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(dialogBinding.root)

            dialogBinding.customBottomsheetCreateNewlist.setOnClickListener {
                openCreateNewListDialog()
                dialog.hide()
            }

            setupMyListsRecyclerView(dialogBinding, dialog)
            dialog.show()
            dialog.window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.windowAnimations = R.style.DialogAnimation
                setGravity(Gravity.BOTTOM)
            }
        }
    }

    private fun setupMyListsRecyclerView(dialogBinding: CustomBottomsheetBinding, dialog: Dialog) {
        val dialogAdapter = createMyListsAdapter(dialog)
        dialogBinding.customBottomsheetRecyclerview.adapter = dialogAdapter

        lifecycleScope.launchWhenStarted {
            viewModel.myLists.collectLatest { myLists ->
                dialogAdapter.submitList(myLists)
            }
        }

    }

    private fun createMyListsAdapter(dialog: Dialog): MyListsAdapter {
        val filteredMoviesSaved = moviesSaved.filter { movie -> movie.movieId == viewModel.movieId }
        val dialogAdapter = MyListsAdapter(filteredMoviesSaved).apply {
            onItemClick = { listSelected, action ->
                dialog.hide()
                when(action) {
                    MyListsAdapter.Companion.Action.INSERT -> saveMovieToMyList(listSelected)
                    MyListsAdapter.Companion.Action.REMOVE -> removeMovieFromMyList(listSelected)
                }

            }
        }
        return dialogAdapter
    }

    private fun saveMovieToMyList(listSelected: MyList) {
        lifecycleScope.launch {
            viewModel.saveMovieToMyList(listSelected.id).collectLatest { successfullySaved ->
                if(successfullySaved) {
                    viewModel.checkIsMovieSaved()
                    Toast.makeText(context,
                        "${getString(R.string.movie_saved_successfully_message)} ${listSelected.name}",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun removeMovieFromMyList(listSelected: MyList) {
        lifecycleScope.launch {
            viewModel.removeMovieFromMyList(listSelected.id)
            Toast.makeText(context,
                "${getString(R.string.movie_removed_successfully_message)} ${listSelected.name}",
                Toast.LENGTH_LONG).show()
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
            viewModel.stars.value?.let { _ ->
                binding.fragmentMovieDetailsStarsSeeMore.setOnClickListener {
                    goToPeopleAndCompanies()
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
                onItemClick = { itemId, _, _, releaseDate ->
                    if(itemId != viewModel.movieId) goToMovieDetails(itemId, releaseDate)
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

    private fun openCreateNewListDialog() {
        context?.let {
            val builder = AlertDialog.Builder(it, R.style.MyDialogTheme)

            val dialogView = layoutInflater.inflate(R.layout.custom_dialog_mylists_form, null)
            val myListEditText = dialogView?.findViewById<EditText>(R.id.my_lists_form_edittext)

            builder.setTitle(getString(R.string.new_list_dialog_title))
                .setView(dialogView)
                .setPositiveButton(getString(R.string.new_list_dialog_button)) { _, _ ->
                    val newListName = myListEditText?.text
                    createNewList(MyList(id = 0, name = newListName.toString()))
                }
                .setNegativeButton(resources.getString(R.string.filter_alertdialog_negative), null)
                .show()
        }
    }

    private fun createNewList(newList: MyList) {
        lifecycleScope.launch {
            viewModel.createNewList(newList).collectLatest { myListId ->
                if(myListId != 0) {
                    saveMovieToMyList(MyList(id = myListId, name = newList.name, posterPath = newList.posterPath))
                }
            }
        }
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

    private fun goToMovieDetails(movieId: Int, releaseDate: String) {
        val directions = MovieDetailsFragmentDirections
            .actionMovieDetailsFragmentToMovieDetailsFragment(movieId.toLong(), releaseDate)
        findNavController().navigate(directions)
    }

    private fun goToPeopleAndCompanies() {
        viewModel.movie.value?.let { movie ->
            val directions = MovieDetailsFragmentDirections
                .actionMovieDetailsFragmentToPeopleAndCompaniesFragment(movie.id.toLong())
            findNavController().navigate(directions)
        }
    }

    private fun goToPeopleDetails(employe: Employe) {
        viewModel.movie.value?.let { movie ->
            val directions = MovieDetailsFragmentDirections
                .actionMovieDetailsFragmentToPeopleDetailsFragment(employe.id, movie.id.toLong())
            findNavController().navigate(directions)
        }
    }
}