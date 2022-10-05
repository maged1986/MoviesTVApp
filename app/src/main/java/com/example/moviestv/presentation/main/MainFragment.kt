package com.example.moviestv.presentation.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.moviestv.R
import com.example.moviestv.data.repository.MoviesTvRepositoryImpl
import com.example.moviestv.domain.mappers.toMovieRow
import com.example.moviestv.domain.models.MovieRow
import com.example.moviestv.presentation.details.DetailsActivity
import com.example.moviestv.utils.Constants.POSTER_BASE_URL
import dagger.hilt.android.AndroidEntryPoint


/**
 * Loads a grid of cards with movies to browse.
 */
@AndroidEntryPoint
class MainFragment : BrowseSupportFragment() {

    private lateinit var mViewModel :MainViewModel
    var moviePresenter= CardPresenter()
    val windowAdapter = ArrayObjectAdapter(ListRowPresenter())
    val adapterForTopRatingMovies = ArrayObjectAdapter(moviePresenter)
    val adapterForNowPlayingMovies = ArrayObjectAdapter(moviePresenter)
    val adapterForPopularMovies = ArrayObjectAdapter(moviePresenter)
    private lateinit var mBackgroundManager: BackgroundManager
    private lateinit var mMetrics: DisplayMetrics


    private  val TAG = "MainFragment"



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //  Log.i(TAG, "onCreate")
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory = MainViewModelFactory(MoviesTvRepositoryImpl())
        mViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        createUI()
        prepareBackgroundManager()
        setupEventListeners()

    }


    // Creates the data rows objects
    private fun createUI() {
        title = getString(R.string.browse_title) // our In-App title will be shown in top-right corner

        headersState = BrowseSupportFragment.HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true

        // set fastLane (or headers) background color
        brandColor = ContextCompat.getColor(requireContext(), R.color.fastlane_background)
        createRows()
    }
    private fun prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager.attach(requireActivity().window)
     //   mDefaultBackground = ContextCompat.getDrawable(context!!, R.drawable.default_background)
        mMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(mMetrics)
    }

    private fun setupEventListeners() {
        onItemViewClickedListener = ItemViewClickedListener()
        onItemViewSelectedListener = ItemViewSelectedListener()
    }

    // Creates the rows and sets up the adapter of the fragment
    private fun createRows() {
        val TopRatingMovies = HeaderItem(0, "TopRatingMovies")
        val NowPlayingMovies = HeaderItem(1, "NowPlayingMovies")
        val PopularMovies = HeaderItem(2, "PopularMovies")
        subscribeToObservables(adapterForTopRatingMovies,adapterForNowPlayingMovies,adapterForPopularMovies)
        windowAdapter.add(ListRow(TopRatingMovies, adapterForTopRatingMovies))
        windowAdapter.add(ListRow(NowPlayingMovies, adapterForNowPlayingMovies))
        windowAdapter.add(ListRow(PopularMovies, adapterForPopularMovies))
        adapter = windowAdapter
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder,
            item: Any,
            rowViewHolder: RowPresenter.ViewHolder,
            row: Row
        ) {

            if (item is MovieRow) {
                Log.d(TAG, "Item: " + item.toString())
                val intent = Intent(requireContext(), DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE, item)

                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    (itemViewHolder.view as ImageCardView).mainImageView,
                    DetailsActivity.SHARED_ELEMENT_NAME
                )
                    .toBundle()
                startActivity(intent, bundle)
            }
        }
    }

    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?, item: Any?,
            rowViewHolder: RowPresenter.ViewHolder, row: Row
        ) {
            if (item is MovieRow) {
                val posterUri=POSTER_BASE_URL+item.moviePosterUrl
                updateBackground(posterUri)
            }
        }
    }
    private fun updateBackground(uri: String?) {
        val width = mMetrics.widthPixels
        val height = mMetrics.heightPixels
        Glide.with(requireActivity())
            .load(uri)
            .centerCrop()
            .into<SimpleTarget<Drawable>>(
                object : SimpleTarget<Drawable>(width, height) {
                    override fun onResourceReady(
                        drawable: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        mBackgroundManager.drawable = drawable
                    }
                })
    }

    private fun subscribeToObservables(topRatingMovies : ArrayObjectAdapter,
                                       nowPlayingMovies : ArrayObjectAdapter,
                                       popularMovies :ArrayObjectAdapter){
        mViewModel.TopRatingMovies.observe(viewLifecycleOwner, Observer {
            it.forEach {
                if (it != null) { // Avoid showing movie without posters
                    var movieRow=it.toMovieRow(it)
                    topRatingMovies.add(movieRow)
                } }
            val posterUri=POSTER_BASE_URL+it.get(0).poster_path
            updateBackground(posterUri)
        })
        mViewModel.NowPlayingMovies.observe(viewLifecycleOwner, Observer {
            it.forEach {
                if (it != null) { // Avoid showing movie without posters
                    var movieRow=it.toMovieRow(it)
                    nowPlayingMovies.add(movieRow)
                } } })
        mViewModel.PopularMovies.observe(viewLifecycleOwner, Observer {
            it.forEach {
                if (it != null) { // Avoid showing movie without posters
                    var movieRow=it.toMovieRow(it)
                    popularMovies.add(movieRow)
                } } })
    }

}
