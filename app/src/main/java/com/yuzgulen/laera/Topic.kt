package com.yuzgulen.laera

class Topic {
    var id: String? = null
    var name: String? = null
    var image: String? = null
    var progress: String? = null

    constructor(id: String, name: String, image: String, progress: String?) {
        this.id = id
        this.name = name
        this.image = image
        this.progress = progress
    }
}