package com.example.secondchance.ui.sellerdetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.secondchance.R
import com.example.secondchance.data.model.Product
import com.example.secondchance.data.model.Seller
import com.example.secondchance.databinding.FragmentSellerDetailBinding
import androidx.core.net.toUri

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
            binding.tvSellerName.text = getString(R.string.b_name, it.name)
            binding.tvSellerPhone.text = getString(R.string.b_phone, it.phone)
            binding.tvSellerAddress.text = getString(R.string.b_address, it.address)

            binding.btnCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = "tel:${seller.phone}".toUri()
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