package com.example.moviestv.presentation.vediopreview

import android.net.Uri
import android.os.Bundle
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.MediaPlayerAdapter
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.widget.PlaybackControlsRow


/** Handles video playback with media controls. */
class PlaybackVideoFragment : VideoSupportFragment() {

    private lateinit var mTransportControlGlue: PlaybackTransportControlGlue<MediaPlayerAdapter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val glueHost = VideoSupportFragmentGlueHost(this@PlaybackVideoFragment)
        val playerAdapter = MediaPlayerAdapter(context)
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE)

        mTransportControlGlue = PlaybackTransportControlGlue(getActivity(), playerAdapter)
        mTransportControlGlue.host = glueHost
        mTransportControlGlue.playWhenPrepared()

        playerAdapter.setDataSource(
            Uri.parse("https://rr1---sn-uvt5-ajwe.googlevideo.com/videoplayback?expire=1664910944&ei=ADI8Y9jTJYfi7ASKi7TgCw&ip=46.246.122.163&id=o-AN4-JEWxFXasLhCi7-L-LJIskHR1G7FY1wns2FIoCPuK&itag=244&aitags=133%2C134%2C135%2C160%2C242%2C243%2C244%2C278%2C394%2C395%2C396%2C397&source=youtube&requiressl=yes&spc=yR2vp5StBh7QXmSr9Gs8BIax4YN0JrM&vprv=1&mime=video%2Fwebm&ns=HIphhJkVgLaRej-VwBcnG0gI&gir=yes&clen=9845153&dur=421.454&lmt=1551224705597764&keepalive=yes&fexp=24001373,24007246&c=WEB&txp=5532432&n=8r4cRfixWWM3uw&sparams=expire%2Cei%2Cip%2Cid%2Caitags%2Csource%2Crequiressl%2Cspc%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cdur%2Clmt&sig=AOq0QJ8wRAIgWf6Vqmvuv0yzMqiNXVxCx8N4OOmUe6oLoxvaQFdB4GACIDSVLkqIslN1GWM9OM979BKymVbVHSLTZty_JTYeZzRD&redirect_counter=1&rm=sn-5gos77e&req_id=72a86aefcfb1a3ee&cms_redirect=yes&ipbypass=yes&mh=y_&mip=31.182.204.219&mm=31&mn=sn-uvt5-ajwe&ms=au&mt=1664889914&mv=m&mvi=1&pcm2cms=yes&pl=18&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pcm2cms,pl&lsig=AG3C_xAwRgIhAMmHo1SPYPNkdbf0HGQ8ghu4NFPxUQU156NonggkKvL-AiEAksfm8phSChe5gR8Qh2OUNtUzfduBwAhTYfqh57uhrv0%3D"))
    }

    override fun onPause() {
        super.onPause()
        mTransportControlGlue.pause()
    }
}