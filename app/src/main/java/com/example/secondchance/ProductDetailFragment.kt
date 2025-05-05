package com.example.secondchance

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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
            showImageFullScreen(product)
        }


        return binding.root
    }
    private fun showImageFullScreen(product: Product?) {
        product ?: return

        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.window?.attributes?.windowAnimations = android.R.style.Animation_Dialog

        dialog.setContentView(R.layout.dialog_fullscreen_image)

        val imageView = dialog.findViewById<ImageView>(R.id.fullscreenImageView)

        val imageSource = product.imageUri?.let { Uri.parse(it) } ?: product.imageRes

        Glide.with(requireContext())
            .load(imageSource)
            .into(imageView)

        imageView.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = arguments?.getParcelable<Product>(getString(R.string.product))

        product?.let {
            binding.tvProductName.text = it.name
            binding.tvProductPrice.text = it.price
            binding.tvProductDescription.text = it.description ?: "No description"
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
            showImageFullScreen(product)
        }

            binding.btnContactSeller.setOnClickListener {
                val product = arguments?.getParcelable<Product>("product") ?: return@setOnClickListener
                val seller = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
                    .getSellerById(product.sellerId)

                if (seller == null) {
                    Toast.makeText(requireContext(), "לא נמצאו פרטי מוכר", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val bundle = Bundle().apply {
                    putParcelable("seller", seller)
                }

                findNavController().navigate(R.id.action_productDetailFragment_to_sellerDetailFragment, bundle)
            }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}
}


