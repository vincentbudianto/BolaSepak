package com.bolasepak.event

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bolasepak.R
import com.bolasepak.model.Event
import kotlinx.android.synthetic.main.item_event.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.SimpleDateFormat

class EventAdapter(private val context: Context, private val events: List<Event>, private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<EventViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, parent, false))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

}

class EventViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bindItem(events: Event, listener: (Event) -> Unit){
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
        itemView.onClick { listener(events) }
    }
}
