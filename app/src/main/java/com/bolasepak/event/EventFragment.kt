package com.bolasepak.event

import android.os.Bundle
import android.text.TextUtils
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
import com.bolasepak.util.invisible
import com.bolasepak.util.visible
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class EventFragment : Fragment(), EventView {
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var presenter: EventPresenter
    private lateinit var adapter: EventAdapter
    private lateinit var searchView: SearchView
    private var events: MutableList<Event> = mutableListOf()
    var event: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_event, container, false)

        listEvent = view.findViewById(R.id.list_event)
        progressBar = view.findViewById(R.id.progress_bar)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        setHasOptionsMenu(true)
        event = arguments?.getString("event")

        adapter = EventAdapter(ctx, events){
            startActivity<EventDetailActivity>(
                    "id" to "${it.eventId}",
                    "idhome" to "${it.idHome}",
                    "idaway" to "${it.idAway}"
            )
        }

        listEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        presenter = EventPresenter(this, request, gson)
        presenter.getEventList("4328", event)

        swipeRefresh.onRefresh {
            presenter.getEventList("4328", event)
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

    override fun showEventList(data: List<Event>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(event: String?): EventFragment {
            val fragment = EventFragment()
            val args = Bundle()

            args.putString("event",event)
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
                    presenter.getEventList("4328", event)
                } else {
                    presenter.getEventSearch(newText?.replace(" ", "_"))
                }

                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

        })
    }
}
