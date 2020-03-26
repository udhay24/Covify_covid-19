package com.udhay.helfycovid_19.ui.guidelines

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.synnapps.carouselview.ViewListener
import com.udhay.helfycovid_19.R
import kotlinx.android.synthetic.main.fragment_who_guide_lines.*


/**
 * A simple [Fragment] subclass.
 */
class WHOGuideLinesFragment : Fragment() {

    var guidelinesText = listOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_who_guide_lines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        guidelinesText = listOf(getString(R.string.guideline_1), getString(R.string.guideline_2),
        getString(R.string.guideline_3), getString(R.string.guideline_4), getString(R.string.guideline_5))
        guide_lines_recyler_view.setViewListener(viewListener)
        guide_lines_recyler_view.pageCount = 5

        val swc= "https://www.mohfw.gov.in/coronvavirushelplinenumber.pdf"
        link1.setOnClickListener {
            launchWebsite("https://www.mohfw.gov.in/coronvavirushelplinenumber.pdf")
        }
        link2.setOnClickListener {
            launchWebsite("https://www.mohfw.gov.in")
        }
        link3.setOnClickListener {
            launchWebsite("https://www.who.int/emergencies/diseases/novel-coronavirus-2019")
        }
        link4.setOnClickListener {
            launchWebsite("https://coronavirus.thebaselab.com")
        }

    }

    val viewListener = ViewListener { position ->
        val view = layoutInflater.inflate(R.layout.guidelines_view_holder, null)
        val text = view.findViewById<TextView>(R.id.guide_line_text_view)
        text.text = guidelinesText[position]
        view
    }

    private fun launchWebsite(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        builder.addDefaultShareMenuItem()
        builder.setShowTitle(true)
        builder.setCloseButtonIcon(BitmapFactory.decodeResource(resources, android.R.drawable.ic_menu_close_clear_cancel))
        builder.setExitAnimations(requireContext(), android.R.anim.fade_in, android.R.anim.fade_out)
        builder.build().launchUrl(requireContext(), Uri.parse(url))
    }

}

