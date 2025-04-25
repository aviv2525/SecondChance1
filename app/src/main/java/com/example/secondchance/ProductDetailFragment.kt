package com.example.secondchance

import Product
import android.app.Dialog
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
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = arguments?.getParcelable<Product>("product")

        product?.let {
            binding.tvProductName.text = it.name
            binding.tvProductPrice.text = it.price

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
        //        binding.ivProductImage.setOnClickListener {
//            if (!isImageFullScreen) {
//                binding.ivProductImage.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
//                binding.ivProductImage.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
//                binding.ivProductImage.scaleType = ImageView.ScaleType.FIT_CENTER
//            } else {
//                binding.ivProductImage.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
//                binding.ivProductImage.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
//                binding.ivProductImage.scaleType = ImageView.ScaleType.FIT_CENTER
//            }
//            binding.ivProductImage.requestLayout()
//            isImageFullScreen = !isImageFullScreen
//        }
//
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


}