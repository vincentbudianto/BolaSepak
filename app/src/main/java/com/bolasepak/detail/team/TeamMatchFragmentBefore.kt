package com.bolasepak.event

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.bolasepak.R
import com.bolasepak.api.ApiRepository
import com.bolasepak.model.Event
import com.bolasepak.detail.event.EventDetailActivity
import com.bolasepak.detail.team.TeamMatchEventView
import com.bolasepak.detail.team.TeamMatchPresenter
import com.bolasepak.model.AllTeam
import com.bolasepak.model.TeamMatchEvent
import com.bolasepak.util.invisible
import com.bolasepak.util.visible
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class TeamMatchFragmentBefore() : Fragment(), TeamMatchEventView {
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var eventPresenter: TeamMatchPresenter
    private lateinit var adapter: TeamMatchAdapter
    private lateinit var searchView: SearchView
    private var events: MutableList<TeamMatchEvent> = mutableListOf()
    private var teams: MutableList<AllTeam> = mutableListOf()
    var event: String? = "eventspastleague"
    var teamID: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_event, container, false)

        Log.d("Fragment Team ID", teamID)
        listEvent = view.findViewById(R.id.list_event)
        progressBar = view.findViewById(R.id.progress_bar)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        setHasOptionsMenu(true)
        event = arguments?.getString("event")

        adapter = TeamMatchAdapter(ctx, events, teams){
            startActivity<EventDetailActivity>(
                    "id" to "${it.eventId}",
                    "idhome" to "${it.idHome}",
                    "idaway" to "${it.idAway}",
                    "location" to "${teams[it.indexTeam!!.toInt()].teamStadiumLoc}"
            )
        }

        listEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        eventPresenter = TeamMatchPresenter(this, request, gson, "sebelum")
        eventPresenter.getTeamMatches(teamID)

        swipeRefresh.onRefresh {
            eventPresenter.getTeamMatches(teamID)
        }

        return view
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showNotFound() {
        swipeRefresh.isRefreshing = false
        events.clear()
        adapter.notifyDataSetChanged()
    }

    override fun showEventList(data: List<TeamMatchEvent>?, data2: List<AllTeam>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        if (data != null) {
            events.addAll(data)
        }
        teams.addAll(data2)

        adapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(teamID: String): TeamMatchFragmentBefore {
            val fragment = TeamMatchFragmentBefore()
            val args = Bundle()

            args.putString("teamID", teamID)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.dashboard, menu)

        val searchItem = menu?.findItem(R.id.action_search)

        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search Match"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextChange(newText: String?): Boolean {
                if (TextUtils.isEmpty(newText)) {
                    eventPresenter.getTeamMatches(teamID)
                } else {
                    eventPresenter.getTeamMatches(newText?.replace(" ", "_"))
                }

                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

        })
    }
}
