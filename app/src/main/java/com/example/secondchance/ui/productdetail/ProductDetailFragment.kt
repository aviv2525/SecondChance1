package com.example.secondchance.ui.productdetail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.secondchance.R
import com.example.secondchance.data.model.Product
import com.example.secondchance.databinding.FragmentProductDetailBinding
import viewmodel.ProductViewModel
import androidx.core.net.toUri

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = arguments?.getParcelable<Product>("product") ?: return

        binding.tvProductName.text = product.name
        binding.tvProductPrice.text = product.price
        binding.tvProductDescription.text = product.description ?: getString(R.string.no_description)

        val imageSource = product.imageUri?.toUri() ?: product.imageRes
        Glide.with(requireContext())
            .load(imageSource)
            .centerCrop()
            .into(binding.ivProductImage)

        binding.ivProductImage.setOnClickListener {
            showImageFullScreen(imageSource)
        }

        binding.btnContactSeller.setOnClickListener {
            val seller = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
                .getSellerById(product.sellerId)

            if (seller == null) {
                Toast.makeText(requireContext(),
                    getString(R.string.no_seller_details_found), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val action = ProductDetailFragmentDirections
                .actionProductDetailFragmentToSellerDetailFragment(seller, product)
            findNavController().navigate(action)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showImageFullScreen(imageSource: Any) {
        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.dialog_fullscreen_image)

        val imageView = dialog.findViewById<ImageView>(R.id.fullscreenImageView)

        Glide.with(requireContext())
            .load(imageSource)
            .into(imageView)

        imageView.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}