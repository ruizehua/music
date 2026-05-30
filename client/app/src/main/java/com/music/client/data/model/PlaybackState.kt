package com.music.client.data.model

enum class PlayMode {
    SEQUENTIAL,
    REPEAT_ONE,
    SHUFFLE
}

enum class PlaybackState {
    IDLE,
    PLAYING,
    PAUSED,
    BUFFERING,
    ERROR
}
