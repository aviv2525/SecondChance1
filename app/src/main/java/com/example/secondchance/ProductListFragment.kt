
package com.example.secondchance
import android.util.Log

import android.app.AlertDialog
import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.secondchance.databinding.FragmentProductListBinding
import com.example.secondchance.ProductAdapter as ProductAdapter


class ProductListFragment : Fragment((R.layout.fragment_product_list)) {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)

        // כאן נקבל את ה-ViewModel
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerView()
        observeProducts()
        addDefaultProductsIfNeeded()

        //setupListeners()

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_productListFragment_to_addEditProductFragment)
        }



        parentFragmentManager.setFragmentResultListener("new_product_request", viewLifecycleOwner) { _, bundle ->
            val name = bundle.getString("name")
            val price = bundle.getString("price")
            val imageUriString = bundle.getString("imageUri")

            if (name != null && price != null) {
                val newProduct = Product(
                    name = name,
                    price = price,
                    imageRes = R.drawable.ic_product,
                    imageUri = imageUriString
                )
                productViewModel.addProduct(newProduct)
            }

        }


    }

    private fun addDefaultProductsIfNeeded() {
        // השתמש ב-ViewModel במקום גישה ישירה ל-DAO
        productViewModel.productList.observe(viewLifecycleOwner) { products ->
            if (products.isEmpty()) {
                // יצירת רשימה של מוצרים דיפולטיביים
                val defaultProducts = listOf(
                    Product("p 1", "100", imageRes = R.drawable.ic_launcher_background),
                    Product("p 2", "150", imageRes = R.drawable.nate),
                    Product("p 3", "200", imageRes = R.drawable.ic_product)
                )

                // הוסף את המוצרים באמצעות ViewModel
                productViewModel.addDefaultProducts(defaultProducts)
/*                for (product in defaultProducts) {
                    productViewModel.addProduct(product)
                }*/
            }
        }
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(
            onItemClick = { product ->
                val bundle = Bundle().apply {
                    putParcelable("product", product)
                }
                findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment, bundle)
            },
            onItemLongClick = { product ->
                showDeleteDialog(product)
            }
        )
        binding.rvProductList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter


        }
    }

    private fun observeProducts() {
        productViewModel.productList.observe(viewLifecycleOwner) { products ->
            if (products.isNullOrEmpty()) {
                Log.d("ProductListFragment", "No products found in database.")
            } else {
                Log.d("ProductListFragment", "Loaded ${products.size} products:")
                products.forEach { product ->
                    Log.d("ProductListFragment", "Product: ${product.name}, Price: ${product.price}")
                }
            }

            products?.let {
                productAdapter.submitList(products)
            }
        }
    }

    private fun showDeleteDialog(product: Product) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure you want to delete this product?")
            .setPositiveButton("Yes") { dialog, _ ->
                productViewModel.deleteProduct(product)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





//    private fun setupRecyclerView() {
//        productAdapter = ProductAdapter { product ->
//            showDeleteDialog(product)
//        }
//        binding.rvProducts.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = productAdapter
//        }
//    }


