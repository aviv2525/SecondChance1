
package com.example.secondchance

import android.app.AlertDialog
import android.media.Image
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

        recyclerView = view.findViewById(R.id.rvProductList)

        productAdapter = ProductAdapter(
            onItemClick = { product ->
                // פעולה בלחיצה רגילה
            },
            onItemLongClick = { product ->
                // פעולה בלחיצה ארוכה
            }
        )

        // חיבור ה-RecyclerView ל-Adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = productAdapter

        // הגשת נתונים ל-Adapter
        val productList = listOf(
            Product("Product 1", "10$", imageRes = R.id.product_image),
            Product("Product 2", "20$", imageRes = R.id.ivProductImage)
        )

        productAdapter.submitList(productList)

        addDefaultProductsIfNeeded()

        setupRecyclerView()
        observeProducts()

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_productListFragment_to_addEditProductFragment)
        }

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_productListFragment_to_settingsFragment)
        }
    }
    private fun addDefaultProductsIfNeeded() {
        // קבל את ה-ViewModel שלך ואת ה-DAO
        val productDao = AppDatabase.getDatabase(requireContext()).ProductsDau()

        // יצירת רשימה של מוצרים דיפולטיביים
        val defaultProducts = listOf(
            Product("p 1", "100", imageRes = R.id.product_image),
            Product("p 2", "150", imageRes = R.id.product_image),
            Product("p 3", "200", imageRes = R.id.product_image)
        )

        // בדוק אם יש כבר מוצרים במסד הנתונים
    }
    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(
            onItemClick = { product ->
                // מה קורה בלחיצה רגילה - לדוגמה מעבר למסך עריכה (אתה יכול לשנות מה שתרצה)
                // כרגע רק טסט
                val bundle = Bundle().apply {
                    putParcelable("product", product)
                }
                findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment, bundle)

                //findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment)


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

//    private fun setupRecyclerView() {
//        productAdapter = ProductAdapter { product ->
//            showDeleteDialog(product)
//        }
//        binding.rvProducts.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = productAdapter
//        }
//    }


    private fun observeProducts() {
        productViewModel.productList.observe(viewLifecycleOwner) { products ->
            products?.let {
                productAdapter.submitList(it)
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







