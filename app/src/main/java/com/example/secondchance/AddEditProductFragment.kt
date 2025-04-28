package com.example.secondchance

import android.app.AlertDialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
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


        binding.backToListButton1.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSelectImage.setOnClickListener {
            val options = arrayOf("מהבחר גלריה", "צלם תמונה")
            AlertDialog.Builder(requireContext())
                .setTitle("בחר מקור תמונה")
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
                binding.etProductName.error = "יש להזין שם מוצר"
                return@setOnClickListener
            }

            val priceValue = price.toDoubleOrNull()
            if (priceValue == null || priceValue <= 0) {
                binding.Price.error = "יש להזין מחיר תקין"
                return@setOnClickListener
            }

            val priceWithShekel = "$price ₪"

            val result = Bundle().apply {
                putString("name", binding.etProductName.text.toString())
                putString("price", priceWithShekel)
                putString("imageUri", selectedImageUri?.toString()) // אפשר לשנות בהמשך
            }

            setFragmentResult("new_product_request", result)
            findNavController().navigateUp()


            Toast.makeText(requireContext(), "המוצר נשמר בהצלחה", Toast.LENGTH_SHORT).show()
            //val product = Product(name = name, price = priceWithShekel, imageRes = R.drawable.ic_launcher_background)
            //ProductViewModel.addProduct(product)


            binding.etProductName.text.clear()
            binding.etProductDescription.text.clear()
            binding.Price.text.clear()
            binding.ivProductImage.setImageDrawable(null)
            binding.ivProductImage.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
