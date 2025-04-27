package com.example.secondchance

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProductRepository(application)

    private val productDao = AppDatabase.getDatabase(application).ProductsDau()

    //val viewModel by activityViewModels

    //val productList: LiveData<List<Product>> = productDao.getAllProducts().asLiveData()
    val productList: LiveData<List<Product>> = productDao.getProduct()
    //private val _productList = MutableLiveData<List<Product>>()


    fun addProduct(product: Product){
        repository.addProduct(product)
    }

    fun deleteProduct(product: Product){
        repository.deleteProduct(product)
    }

}
//
//
//    fun getAllProducts() {
//        // קבלת הנתונים מה-Repository (למשל: מתוך Room)
//        _productList.value = repository.getAllProducts()
//    }
//
//    fun addProduct(product: Product) {
//        // הוספת פריט חדש
//        repository.addProduct(product)
//        getAllProducts()  // לעדכן את הרשימה
//    }
//
