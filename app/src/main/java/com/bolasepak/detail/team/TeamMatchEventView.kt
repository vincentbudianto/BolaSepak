package com.bolasepak.detail.team

import com.bolasepak.model.TeamMatchEvent

interface TeamMatchEventView{
    fun showLoading()

    fun hideLoading()

    fun showNotFound()

    fun showEventList(data: List<TeamMatchEvent>?)
}