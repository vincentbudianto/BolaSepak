package com.bolasepak.subscribed

import com.bolasepak.model.AllTeam
import com.bolasepak.model.Event

interface SubscribedView{
    fun showLoading()

    fun hideLoading()

    fun showNotFound()

    fun showSubscribedList(data1: List<Event>, data2: List<AllTeam>)
}