package com.bolasepak.event

import com.bolasepak.model.AllTeam
import com.bolasepak.model.Event

interface EventView{
    fun showLoading()

    fun hideLoading()

    fun showNotFound()

    fun showEventList(data1: List<Event>, data2: List<AllTeam>)
}