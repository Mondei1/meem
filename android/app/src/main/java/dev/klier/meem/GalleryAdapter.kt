package dev.klier.meem

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import dev.klier.meem.types.PhoneAlbum
import java.io.File
import java.util.Vector
import kotlin.coroutines.coroutineContext

class GalleryAdapter(private val dataSet: Vector<PhoneAlbum?>, private val activatedElements: Vector<Int>) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var isActive = false
        val textView: TextView
        val cover: ImageView
        val card: MaterialCardView

        init {
            textView = view.findViewById(R.id.label)
            cover = view.findViewById(R.id.cover)
            card = view.findViewById(R.id.cardview)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.gallery_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        if (dataSet[position] != null) {
            viewHolder.textView.text = "${dataSet[position]?.name} (${dataSet[position]?.albumPhotos?.size})"

            viewHolder.card.setOnClickListener {
                if (activatedElements.contains(position)) {
                    activatedElements.remove(position)
                    resetColors(viewHolder)
                } else {
                    activatedElements.add(position)
                    setActiveColors(viewHolder)
                }
            }

            if (activatedElements.contains(position)) {
                Log.i("Gallery Adapter", "Element #$position is active")
                setActiveColors(viewHolder)
            } else {
                resetColors(viewHolder)
            }

            val pic = Picasso.get()
            pic
                .load(File(dataSet[position]?.coverUri?.path.toString()))
                .centerCrop()
                .fit()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(viewHolder.cover)
        }
    }

    private fun setActiveColors(viewHolder: ViewHolder) {
        viewHolder.card.isChecked = true
        viewHolder.textView.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, com.google.android.material.R.color.material_dynamic_secondary10))
        viewHolder.card.strokeColor = ContextCompat.getColor(viewHolder.itemView.context, com.google.android.material.R.color.material_dynamic_primary90)
        viewHolder.card.setCardBackgroundColor(ContextCompat.getColor(viewHolder.itemView.context, com.google.android.material.R.color.material_dynamic_primary90))
    }

    private fun resetColors(viewHolder: ViewHolder) {
        viewHolder.card.isChecked = false
        viewHolder.textView.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, com.google.android.material.R.color.material_dynamic_neutral90))
        viewHolder.card.strokeColor = ContextCompat.getColor(viewHolder.itemView.context, com.google.android.material.R.color.material_dynamic_neutral_variant60)
        viewHolder.card.setCardBackgroundColor(ContextCompat.getColor(viewHolder.itemView.context, com.google.android.material.R.color.material_dynamic_neutral10))
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}