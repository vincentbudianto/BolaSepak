package com.bolasepak.event

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bolasepak.R
import com.bolasepak.model.AllTeam
import com.bolasepak.model.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_event.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.SimpleDateFormat

class EventAdapter(private val context: Context, private val events: List<Event>, private val teams: List<AllTeam>, private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<EventViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, parent, false))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindItem(events[position], teams, listener)
    }

}

class EventViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bindItem(events: Event, teams: List<AllTeam>, listener: (Event) -> Unit){
        val homeId = events.idHome
        val awayId = events.idAway

        val home = teams.indexOfFirst { it.teamId == homeId }

        events.indexTeam = home.toString()

        val away = teams.indexOfFirst { it.teamId == awayId }

        if (events.scoreHome.isNullOrEmpty() && events.scoreAway.isNullOrEmpty()) {
            itemView.skor.text = "VS"
        } else {
            itemView.skor.text = events.scoreHome+" VS "+events.scoreAway
        }

        var date = SimpleDateFormat("EEE, d MMM yyyy")
                .format(SimpleDateFormat("yyyy-MM-dd")
                        .parse(events.eventDate))

        itemView.date.text = date
        itemView.home.text = events.teamHome
        itemView.away.text = events.teamAway

        if (home >= 0) {
            val homeBadge = teams[home].teamBadge
              Picasso.get().load(homeBadge).into(itemView.img_home)
        }

        if (away >= 0) {
            val awayBadge = teams[away].teamBadge
            Picasso.get().load(awayBadge).into(itemView.img_away)
        }

        itemView.onClick { listener(events) }
    }
}
