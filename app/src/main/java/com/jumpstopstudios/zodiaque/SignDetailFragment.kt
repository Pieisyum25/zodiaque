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
import com.jumpstopstudios.zodiaque.model.Sign
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


        binding.signDetailRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SiteAdapter(viewModel.horoscope, viewLifecycleOwner)
        }

        viewModel.apply {
            generateHoroscope(args.sign.name.lowercase(Locale.getDefault()))
            status.observe(viewLifecycleOwner) { status ->
                binding.signDetailStatus.text = status
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}