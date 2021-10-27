package com.jumpstopstudios.zodiaque

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jumpstopstudios.zodiaque.databinding.FragmentSignDetailBinding
import com.jumpstopstudios.zodiaque.model.MainViewModel
import com.jumpstopstudios.zodiaque.horoscope.SiteAdapter
import com.jumpstopstudios.zodiaque.model.LoadingStatus
import java.util.*

class SignDetailFragment : Fragment() {

    private var _binding: FragmentSignDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private val args: SignDetailFragmentArgs by navArgs()

    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signDetailItem.apply {
            // If orientation is landscape:
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                signItem.visibility = View.GONE // hide sign item
            }
            else {
                // Apply information:
                args.sign.apply {
                    signItemHeader.text = name
                    signItemImage.setImageResource(imageResId)
                    signItemFooter.text = datesRange
                }
                // Give it animation:
                val bobAnimation = AnimationUtils.loadAnimation(context, R.anim.bob);
                signItemCard.startAnimation(bobAnimation)
            }
        }

        // Animate and rotate zodiac circle at bottom:
        val blinkAnimation = AnimationUtils.loadAnimation(context, R.anim.blink);
        binding.signDetailZodiacCircle.zodiacCircle.apply {
            startAnimation(blinkAnimation)
            rotation = -30f * args.sign.position
        }

        // Init recyclerview:
        binding.signDetailRecyclerview.apply {
            visibility = View.INVISIBLE
            layoutManager = LinearLayoutManager(context)
            adapter = SiteAdapter(viewModel.horoscope, viewLifecycleOwner)
        }

        // Set status text:
        updateStatus()
        viewModel.apply {
            generateHoroscope(args.sign.name.lowercase(Locale.getDefault()))
            status.observe(viewLifecycleOwner) { updateStatus() }
            sectionsLoaded.observe(viewLifecycleOwner) { updateStatus() }
        }
    }

    /**
     * Update status text based on viewModel loading status:
     */
    private fun updateStatus(){
        binding.apply {
            viewModel.apply {
                when (status.value) {
                    LoadingStatus.SUCCESSFUL -> {
                        signDetailStatus.text =
                            "Loading Complete! (Sections: ${sectionsLoaded.value}/$sectionsTotal)"
                        // If recyclerview is invisible, fade it in:
                        if (signDetailRecyclerview.visibility != View.VISIBLE) {
                            signDetailRecyclerview.visibility = View.VISIBLE
                            val fadeInAnimation =
                                AnimationUtils.loadAnimation(context, R.anim.fade_in);
                            signDetailRecyclerview.startAnimation(fadeInAnimation)
                        }
                    }
                    LoadingStatus.FAILED -> signDetailStatus.text =
                        "Your horoscope is currently unavailable."
                    LoadingStatus.LOADING -> signDetailStatus.text =
                        "Loading... (Sections: ${sectionsLoaded.value}/$sectionsTotal)"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}