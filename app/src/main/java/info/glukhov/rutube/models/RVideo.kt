package info.glukhov.rutube.models

import ru.rutube.models.context.TrackInfoModelImp
import ru.rutube.models.video.RutubeVideoStream

class RVideo() {

    constructor(trackInfo: TrackInfoModelImp, quality: List<RutubeVideoStream>) : this() {
        this.trackInfo = trackInfo
        this.quality = quality
    }

    lateinit var quality: List<RutubeVideoStream>
    lateinit var trackInfo: TrackInfoModelImp
}