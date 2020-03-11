package com.bolasepak.detail.event

import com.bolasepak.model.EventDetail

interface EventDetailView{
    fun showLoading()

    fun hideLoading()

    fun showEventDetail(data: List<EventDetail>)
}