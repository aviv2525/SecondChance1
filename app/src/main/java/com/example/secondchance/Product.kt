import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val name: String,
    val price: String,
    val description: String ,
    val imageRes: Int
) :Parcelable