package dev.klier.meem

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import dev.klier.meem.manager.DeviceImageManager
import dev.klier.meem.types.PhoneAlbum
import java.io.File
import java.util.Vector

class GalleryAdapter(private val dmi: DeviceImageManager, private val selected: MutableList<Pair<Int, Int>>) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private val dataSet: List<PhoneAlbum?> = dmi.albumCache

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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

    private fun isSelected(position: Int): Boolean {
        return selected.any { pair -> pair.first == position }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = "${dataSet[position]?.name} (${dataSet[position]?.albumPhotos?.size})"

        viewHolder.card.setOnClickListener {
            if (isSelected(position)) {
                selected.removeIf { pair -> pair.first == position }
                resetColors(viewHolder)
            } else {
                selected.add(Pair(position, dataSet[position]!!.id))
                setActiveColors(viewHolder)
            }
        }

        if (isSelected(position)) {
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