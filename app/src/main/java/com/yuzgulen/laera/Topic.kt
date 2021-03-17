package com.example.laera

class Topic {
    var name: String? = null
    var image: Int? = null
    var progress: String? = null

    constructor(name: String, image: Int, progress: String?) {
        this.name = name
        this.image = image
        this.progress = progress
    }
}