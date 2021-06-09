package com.yuzgulen.laera

class Topic {
    var id: String? = null
    var name: String? = null
    var image: Int? = null
    var progress: String? = null

    constructor(id: String, name: String, image: Int, progress: String?) {
        this.id = id
        this.name = name
        this.image = image
        this.progress = progress
    }
}