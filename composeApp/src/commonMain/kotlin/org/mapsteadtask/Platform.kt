package org.mapsteadtask

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform