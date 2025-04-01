package br.pucpr.app.libmangesys

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform