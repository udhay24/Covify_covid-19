package com.udhay.helfy.ui.help

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.udhay.helfy.R
import com.udhay.helfy.data.model.HelplineModel
import kotlinx.android.synthetic.main.helpline_item.view.*


class HelpLineAdapter(
    private val statesList: List<HelplineModel.State>
): RecyclerView.Adapter<HelpLineAdapter.HelpLineViewModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpLineViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.helpline_item, parent, false)
        return HelpLineViewModel(view)
    }

    override fun getItemCount(): Int = statesList.size

    override fun onBindViewHolder(holder: HelpLineViewModel, position: Int) {
        holder.populateDetails(state = statesList[position], position = position)
    }

    inner class HelpLineViewModel(private val view: View): RecyclerView.ViewHolder(view) {
        fun populateDetails(state: HelplineModel.State, position: Int) {

            view.let {

                it.state.text = state.name
                it.helpline1.text = state.helpline[0]
                it.setOnClickListener {
                    if (ActivityCompat.checkSelfPermission(
                            view.context,
                            Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data = Uri.parse("tel:${state.helpline[0]}")
                        view.context.startActivity(callIntent)
                    }
                }
                if(state.helpline.size>1) {
                    it.helpline2.visibility = View.VISIBLE
                    it.helpline2.text = state.helpline[1]

                    it.setOnClickListener {
                        if (ActivityCompat.checkSelfPermission(
                                view.context,
                                Manifest.permission.CALL_PHONE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            val callIntent = Intent(Intent.ACTION_CALL)
                            callIntent.data = Uri.parse("tel:${state.helpline[1]}")
                            view.context.startActivity(callIntent)
                        }
                    }

                }

            }
        }
    }
}