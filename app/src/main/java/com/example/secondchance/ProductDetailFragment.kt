package com.example.secondchance

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.secondchance.databinding.FragmentProductDetailBinding
import com.example.secondchance.databinding.FragmentProductListBinding

class ProductDetailFragment : Fragment() {


    private var _binding : FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private var isImageFullScreen = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        val product = arguments?.getParcelable<Product>("product")

        product?.let {
            binding.tvProductName.text = it.name
            binding.tvProductPrice.text = it.price
            binding.tvProductDescription.text = it.description

            if (!it.imageUri.isNullOrEmpty()) {
                Glide.with(requireContext())
                    .load(Uri.parse(it.imageUri))
                    .centerCrop()
                    .into(binding.ivProductImage)
            } else {
                Glide.with(requireContext())
                    .load(it.imageRes)
                    .centerCrop()
                    .into(binding.ivProductImage)
            }
        }

        binding.ivProductImage.setOnClickListener {
            showImageFullScreen(product?.imageRes)
        }

        return binding.root
    }
    private fun showImageFullScreen(imageUrl: Int?) {
        imageUrl ?: return

        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.window?.attributes?.windowAnimations = android.R.style.Animation_Dialog

        dialog.setContentView(R.layout.dialog_fullscreen_image)

        val imageView = dialog.findViewById<ImageView>(R.id.fullscreenImageView)
        Glide.with(requireContext())
            .load(imageUrl)
            .into(imageView)

        imageView.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = arguments?.getParcelable<Product>("product")

        product?.let {
            binding.tvProductName.text = it.name
            binding.tvProductPrice.text = it.price
            binding.tvProductDescription.text = it.description ?: "אין תיאור"
            if (!it.imageUri.isNullOrEmpty()) {
                Glide.with(requireContext())
                    .load(Uri.parse(it.imageUri))
                    .centerCrop()
                    .into(binding.ivProductImage)
            } else {
                Glide.with(requireContext())
                    .load(it.imageRes)
                    .centerCrop()
                    .into(binding.ivProductImage)
        }
        binding.ivProductImage.setOnClickListener {
            showImageFullScreen(product?.imageRes)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}
}


