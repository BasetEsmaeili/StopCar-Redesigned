package ir.esmaeili.stopcar.ui.activity

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField

class EventMessageViewModel {
    private val eventBackgroundColor: ObservableField<Int> = ObservableField()
    private val eventIcon: ObservableField<Drawable> = ObservableField()
    private val eventTitle: ObservableField<String> = ObservableField()
    private val eventDescription: ObservableField<String> = ObservableField()

    fun setBackground(color: Int) {
        this.eventBackgroundColor.set(color)
    }

    fun setIcon(icon: Drawable) {
        this.eventIcon.set(icon)
    }

    fun setTitle(title: String) {
        this.eventTitle.set(title)
    }

    fun setDescription(description: String) {
        this.eventDescription.set(description)
    }

    fun getBackground(): ObservableField<Int> {
        return this.eventBackgroundColor
    }

    fun getIcon(): ObservableField<Drawable> {
        return this.eventIcon
    }

    fun getTitle(): ObservableField<String> {
        return this.eventTitle
    }

    fun getDescription(): ObservableField<String> {
        return this.eventDescription
    }

}