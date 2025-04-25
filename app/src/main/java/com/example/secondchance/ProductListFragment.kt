package com.example.secondchance

import Product
import ProductAdapter
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.secondchance.databinding.FragmentProductListBinding


class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val productList = mutableListOf(
        Product(" מוצר לדוגמה", "₪99", R.drawable.nate),
        Product("ביצים גדולות", "₪99", R.drawable.ic_launcher_background),
        Product("כוס קרמית", "₪29", R.drawable.ic_launcher_foreground),
        Product("קומקום חשמלי", "₪199", R.drawable.ic_launcher_background)
    )
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = ProductAdapter(productList,
            onItemClick =  { selectedProduct ->
            val bundle = Bundle().apply {
                putParcelable("product", selectedProduct)
            }
            findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment, bundle)
        },
        longClickListener = { product ->
            // פעולה בלחיצה ארוכה (מחיקת המוצר)
            deleteProduct(product)
        }
        )

        // הגדרת ה-Recyclerview
        binding.rvProducts.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }


        binding.rvProducts.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        setFragmentResultListener("new_product_request") { _, bundle ->
            val name = bundle.getString("name") ?: return@setFragmentResultListener
            val price = bundle.getString("price") ?: return@setFragmentResultListener
            val imageRes = bundle.getInt("imageRes", R.drawable.ic_launcher_background)

            val newProduct = Product(name, price, imageRes)
            productList.add(newProduct)
            productAdapter.notifyItemInserted(productList.size - 1)
        }

        binding.addButton.setOnClickListener{
            findNavController().navigate(R.id.action_productListFragment_to_addEditProductFragment)
        }


        binding.detailButton.setOnClickListener{
            findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment)
        }


        binding.settingsButton.setOnClickListener{
            findNavController().navigate(R.id.action_productListFragment_to_settingsFragment)
        }


    }

    private fun deleteProduct(product: Product) {
//        productList.remove(product)
//        productAdapter.submitList(productList) // עדכון ה-RecyclerView
        // יצירת התראה עם כפתורי אישור וביטול
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure you want to delete this product?")
            .setCancelable(false) // Prevent closing the dialog by tapping outside
            .setPositiveButton("Yes") { dialog, id ->
                // When the user clicks "Yes", delete the product
                productList.remove(product)
                productAdapter.submitList(productList) // Update the RecyclerView
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss() // Close the dialog without any action
            }

        // Show the dialog
        builder.create().show()



}
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






/*
class ProductListFragment : Fragment() {

    private var _binding : FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
*/
/*

        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.action_productListFragment_to_addEditProductFragmen t)
        }
*//*



        binding.addButton.setOnClickListener{
            findNavController().navigate(R.id.action_productListFragment_to_addEditProductFragment)
        }


        binding.detailButton.setOnClickListener{
            findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment)
        }


        binding.settingsButton.setOnClickListener{
            findNavController().navigate(R.id.action_productListFragment_to_settingsFragment)
        }


        return binding.root
    }

*/




//    override fun onViewCreated(view: View, savedInstanceState: Bundle?): LinearLayout {
//        super.onViewCreated(view, savedInstanceState)
//        return binding.root
//
//    }
/*
val view = inflater.inflate(R.layout.fragment_product_list, container, false)

// כפתור לעבור למסך הוספה
val addButton = Button(requireContext()).apply {
    text = "Go to Add"
    setOnClickListener {
        findNavController().navigate(R.id.action_productListFragment_to_addEditProductFragment)
    }
}

val detailButton = Button(requireContext()).apply {
    text = "Go to Detail"
    setOnClickListener {
        findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment)
    }
}

val settingsButton = Button(requireContext()).apply {
    text = "Go to Settings"
    setOnClickListener {
        findNavController().navigate(R.id.action_productListFragment_to_settingsFragment)
    }
}

// הוספת הכפתורים לדף
(view as LinearLayout).apply {
    addView(addButton)
    addView(detailButton)
    addView(settingsButton)
}

return view
}*/
/*

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
*/
