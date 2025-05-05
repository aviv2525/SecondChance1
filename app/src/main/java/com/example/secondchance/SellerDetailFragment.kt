package com.example.secondchance

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.secondchance.databinding.FragmentSellerDetailBinding

class SellerDetailFragment : Fragment() {

    private var _binding: FragmentSellerDetailBinding? = null
    private val binding get() = _binding!!

    val product = arguments?.getParcelable<Product>("product")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSellerDetailBinding.inflate(inflater, container, false)

        val seller = arguments?.getParcelable<Seller>("seller")

        seller?.let {
            binding.tvSellerName.text = getString(R.string.name_seller, it.name)
            binding.tvSellerPhone.text = getString(R.string.mobile_phone_seller, it.phone)
            binding.tvSellerAddress.text = getString(R.string.address_seller, it.address)

            binding.btnCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${seller.phone}")
                }
                startActivity(intent)
            }
        }


        binding.backToListButton2.setOnClickListener {
            findNavController().navigateUp()
        }


            return binding.root

    }


}