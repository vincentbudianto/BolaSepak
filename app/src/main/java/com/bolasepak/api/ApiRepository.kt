package com.bolasepak.api

import java.net.URL

class ApiRepository{
    fun request(url: String): String{
        return URL(url).readText()
    }
}