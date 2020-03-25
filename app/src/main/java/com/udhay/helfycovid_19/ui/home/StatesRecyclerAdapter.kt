package com.udhay.helfycovid_19.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udhay.helfycovid_19.R
import com.udhay.helfycovid_19.data.model.StateModel
import kotlinx.android.synthetic.main.state_recycler_view_item.view.*
import kotlinx.android.synthetic.main.state_recycler_view_item.view.cases_text_view

class StatesRecyclerAdapter(
    private val statesList: List<StateModel.StateModelItem>,
    private val stateClickListener: StateClickListener
): RecyclerView.Adapter<StatesRecyclerAdapter.StatesViewModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatesViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.state_recycler_view_item, parent, false)
        return StatesViewModel(view)
    }

    override fun getItemCount(): Int = statesList.size

    override fun onBindViewHolder(holder: StatesViewModel, position: Int) {
        holder.populateDetails(state = statesList[position])
    }

    inner class StatesViewModel(private val view: View): RecyclerView.ViewHolder(view) {
        fun populateDetails(state: StateModel.StateModelItem) {
            view.let {
                it.state_title_text_view.text = state.state
                it.cases_text_view.text = state.confirmed_cases.toString()
                it.death_text_view.text = state.deaths.toString()
            }
            view.detail_button.setOnClickListener {
                stateClickListener.stateClicked(state)
            }
        }
    }

    interface StateClickListener {
        fun stateClicked(state: StateModel.StateModelItem)
    }
}