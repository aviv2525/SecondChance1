
package com.example.secondchance

import android.R
import android.app.AlertDialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.secondchance.databinding.FragmentAddEditProductBinding
import java.io.File

class AddEditProductFragment : Fragment() {

    private var _binding: FragmentAddEditProductBinding? = null
    private val binding get() = _binding!!

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectedImageUri = it
            binding.ivProductImage.setImageURI(it)
            binding.ivProductImage.visibility = View.VISIBLE
        }
    }
    private var selectedImageUri: Uri? = null

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            val uri = saveBitmapAndGetUri(it)
            selectedImageUri = uri
            binding.ivProductImage.setImageURI(uri)
            binding.ivProductImage.visibility = View.VISIBLE

        }
    }
    private lateinit var sellerList: List<Seller>
    private lateinit var selectedSeller: Seller



    private fun saveBitmapAndGetUri(bitmap: Bitmap): Uri? {
        val file = File(requireContext().cacheDir, "${System.currentTimeMillis()}.jpg")
        file.outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            file
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddEditProductBinding.inflate(inflater, container, false)


        val productViewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]

        productViewModel.sellerList.observe(viewLifecycleOwner) { sellers ->
            sellerList = sellers

            val sellerNames = sellers.map {
                it.name
            }
            val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, sellerNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.sellerSpinner.adapter = adapter

            binding.sellerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: android.widget.AdapterView<*>, view: View?, position: Int, id: Long) {
                    selectedSeller = sellerList[position]
                }

                override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
            }
        }


        binding.backToListButton1.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSelectImage.setOnClickListener {
            val options = arrayOf(getString(com.example.secondchance.R.string.choose_from_gallery),
                getString(com.example.secondchance.R.string.take_a_photo))
            AlertDialog.Builder(requireContext())
                .setTitle(getString(com.example.secondchance.R.string.select_image_source))
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> galleryLauncher.launch("image/*")
                        1 -> cameraLauncher.launch(null)
                    }
                }
                .show()
        }


        binding.btnSaveProduct.setOnClickListener {
            val name = binding.etProductName.text.toString().trim()
            val description = binding.etProductDescription.text.toString().trim()
            val price = binding.Price.text.toString().trim()


            if (name.isBlank()) {
                binding.etProductName.error =
                    getString(com.example.secondchance.R.string.please_enter_a_product_name)
                return@setOnClickListener
            }

            val priceValue = price.toDoubleOrNull()
            if (priceValue == null || priceValue < 0) {
                binding.Price.error =
                    getString(com.example.secondchance.R.string.please_enter_a_valid_price)
                return@setOnClickListener
            }

            val priceWithShekel = "$price ₪"


            val newProduct = Product(
                name = name,
                price = priceWithShekel,
                description = description,
                imageUri = selectedImageUri?.toString(),
                sellerId = selectedSeller.sellerId
            )


            val productViewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
            //productViewModel.addProduct(newProduct)
            productViewModel.addProductToSeller(selectedSeller.sellerId, newProduct)



            if (!::selectedSeller.isInitialized) {
                Toast.makeText(requireContext(),
                    getString(com.example.secondchance.R.string.please_select_a_seller), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //productViewModel.updateSellerList(updatedSellers)
            //setFragmentResult("new_product_request", result)


            Toast.makeText(requireContext(),
                getString(com.example.secondchance.R.string.product_saved_successfully), Toast.LENGTH_SHORT).show()
            //val product = Product(name = name, price = priceWithShekel, imageRes = R.drawable.ic_launcher_background)
            //ProductViewModel.addProduct(product)


            binding.etProductName.text.clear()
            binding.etProductDescription.text.clear()
            binding.Price.text.clear()
            binding.ivProductImage.setImageDrawable(null)
            binding.ivProductImage.visibility = View.GONE

            findNavController().navigateUp()

        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    }