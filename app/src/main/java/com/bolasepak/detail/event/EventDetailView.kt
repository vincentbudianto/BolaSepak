package com.bolasepak.detail.event

import com.bolasepak.model.AllTeam
import com.bolasepak.model.EventDetail
import com.bolasepak.model.Weather

interface EventDetailView{
    fun showLoading()

    fun hideLoading()

    fun showEventDetail(data1: List<EventDetail>, data2: List<AllTeam>)
}