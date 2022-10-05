package com.example.moviestv.presentation.details

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.app.DetailsSupportFragmentBackgroundController
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ClassPresenterSelector
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import androidx.leanback.widget.FullWidthDetailsOverviewSharedElementHelper
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.OnActionClickedListener
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.Row
import androidx.leanback.widget.RowPresenter
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

import com.example.moviestv.*
import com.example.moviestv.data.repository.MoviesTvRepositoryImpl
import com.example.moviestv.domain.models.MovieRow
import com.example.moviestv.presentation.main.*
import com.example.moviestv.presentation.vediopreview.PlaybackActivity
import com.example.moviestv.utils.Constants
import com.example.moviestv.utils.Constants.PRE_VIDEO_BASE_URL

/**
 * A wrapper fragment for leanback details screens.
 * It shows a detailed view of video and its metadata plus related videos.
 */
class VideoDetailsFragment : DetailsSupportFragment() {

    private var mSelectedMovie: MovieRow? = null
    private lateinit var mViewModel : VideoDetailsViewModel
    private lateinit var posterUri:String
    private lateinit var movieUrl:String


    private lateinit var mDetailsBackground: DetailsSupportFragmentBackgroundController
    private lateinit var mPresenterSelector: ClassPresenterSelector
    private lateinit var mAdapter: ArrayObjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate DetailsFragmentgfgf${mSelectedMovie.toString()}")
        super.onCreate(savedInstanceState)
        val viewModelFactory = VideoDetailsViewModelFactory(MoviesTvRepositoryImpl())
        mViewModel =ViewModelProvider(this,viewModelFactory).get(VideoDetailsViewModel::class.java)

        mDetailsBackground = DetailsSupportFragmentBackgroundController(this)

        mSelectedMovie = requireActivity().intent.getSerializableExtra(DetailsActivity.MOVIE) as MovieRow
        posterUri= Constants.POSTER_BASE_URL +mSelectedMovie?.moviePosterUrl
        if (mSelectedMovie != null) {
            mViewModel.getVideos(mSelectedMovie!!.id.toLong())
            mPresenterSelector = ClassPresenterSelector()
            mAdapter = ArrayObjectAdapter(mPresenterSelector)
            setupDetailsOverviewRow()
            setupDetailsOverviewRowPresenter()
           // setupRelatedMovieListRow()
            adapter = mAdapter
           initializeBackground(mSelectedMovie)
            onItemViewClickedListener = ItemViewClickedListener()
        } else {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initializeBackground(movie: MovieRow?) {
        mDetailsBackground.enableParallax()
        Glide.with(requireContext())
            .asBitmap()
            .centerCrop()
            .error(R.drawable.default_background)
            .load(posterUri)
            .into<SimpleTarget<Bitmap>>(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(
                    bitmap: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    mDetailsBackground.coverBitmap = bitmap
                    mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size())
                }
            })
    }

    private fun setupDetailsOverviewRow() {
        Log.d(TAG, "doInBackground: " + mSelectedMovie?.toString())
        val row = DetailsOverviewRow(mSelectedMovie)
        row.imageDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.default_background)
        val width = convertDpToPixel(requireContext(), DETAIL_THUMB_WIDTH)
        val height = convertDpToPixel(requireContext(), DETAIL_THUMB_HEIGHT)
        Glide.with(requireContext())
            .load(posterUri)
            .centerCrop()
            .error(R.drawable.default_background)
            .into<SimpleTarget<Drawable>>(object : SimpleTarget<Drawable>(width, height) {
                override fun onResourceReady(
                    drawable: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    Log.d(TAG, "details overview card image url ready: " + drawable)
                    row.imageDrawable = drawable
                    mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size())
                }
            })

        val actionAdapter = ArrayObjectAdapter()

        actionAdapter.add(
            Action(
                ACTION_WATCH_TRAILER,
                resources.getString(R.string.watch_trailer_1),
            )
        )

        row.actionsAdapter = actionAdapter

        mAdapter.add(row)
    }

    private fun setupDetailsOverviewRowPresenter() {
        // Set detail background.
        val detailsPresenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())
        detailsPresenter.backgroundColor =
            ContextCompat.getColor(requireContext(), R.color.selected_background)

        // Hook up transition element.
        val sharedElementHelper = FullWidthDetailsOverviewSharedElementHelper()
        sharedElementHelper.setSharedElementEnterTransition(
            activity, DetailsActivity.SHARED_ELEMENT_NAME
        )
        detailsPresenter.setListener(sharedElementHelper)
        detailsPresenter.isParticipatingEntranceTransition = true

        detailsPresenter.onActionClickedListener = OnActionClickedListener { action ->
            if (action.id == ACTION_WATCH_TRAILER) {
               val intent = Intent(requireContext(), PlaybackActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE, mSelectedMovie)
                mViewModel.TrailMovieURL.observe(viewLifecycleOwner, Observer {
                    movieUrl=PRE_VIDEO_BASE_URL +it
                })
                intent.putExtra("Title", mSelectedMovie?.movieName)
                intent.putExtra("Subtitle", mSelectedMovie?.release_date)
                intent.putExtra("VideoUrl", movieUrl)
                startActivity(intent)
              }
        }
        mPresenterSelector.addClassPresenter(DetailsOverviewRow::class.java, detailsPresenter)
    }

       private fun convertDpToPixel(context: Context, dp: Int): Int {
        val density = context.applicationContext.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder,
            row: Row
        ) {
            if (item is MovieRow) {
                Log.d(TAG, "Item: " + item.toString())
                val intent = Intent(requireContext(), DetailsActivity::class.java)





                val bundle =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        requireActivity(),
                        (itemViewHolder?.view as ImageCardView).mainImageView,
                        DetailsActivity.SHARED_ELEMENT_NAME
                    )
                        .toBundle()
                startActivity(intent, bundle)
            }
        }
    }

    companion object {
        private val TAG = "VideoDetailsFragment"

        private val ACTION_WATCH_TRAILER = 1L


        private val DETAIL_THUMB_WIDTH = 274
        private val DETAIL_THUMB_HEIGHT = 274

        private val NUM_COLS = 10
    }
}