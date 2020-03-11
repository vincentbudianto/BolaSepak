package com.bolasepak.event

import com.bolasepak.model.Event

interface EventView{
    fun showLoading()

    fun hideLoading()

    fun showEventList(data: List<Event>)
}