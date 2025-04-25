package com.example.secondchance

import Product
import ProductAdapter
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

        productAdapter = ProductAdapter(productList) { selectedProduct ->
            val bundle = Bundle().apply {
                putParcelable("product", selectedProduct)
            }
            findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment, bundle)
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


//        binding.rvProducts.apply {
//            adapter = ProductAdapter(sampleProducts) { selectedProduct ->
//                // מעבר למסך פרטים, אפשר להעביר את המוצר ב־SafeArgs או Bundle
//                findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment)
//            }
//            layoutManager = LinearLayoutManager(requireContext())
//        }
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
