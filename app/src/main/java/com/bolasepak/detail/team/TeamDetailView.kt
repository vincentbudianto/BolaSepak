package com.bolasepak.detail.team

import com.bolasepak.model.Event
import com.bolasepak.model.TeamDetail

interface TeamDetailView {
    fun showLoading()

    fun hideLoading()

    fun showNotFound()

    fun showTeamDetail(data: List<TeamDetail>)
}