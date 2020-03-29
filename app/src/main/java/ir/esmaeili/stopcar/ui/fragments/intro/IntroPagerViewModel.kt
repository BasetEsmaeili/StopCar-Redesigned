package ir.esmaeili.stopcar.ui.fragments.intro

import androidx.databinding.ObservableField

class IntroPagerViewModel {
    private val title: ObservableField<String> = ObservableField()
    private val description: ObservableField<String> = ObservableField()
    fun setTitle(resID: String) {
        this.title.set(resID)
    }

    fun setDescription(resID: String) {
        this.description.set(resID)
    }

    fun getTitle(): ObservableField<String> {
        return title
    }

    fun getDescription(): ObservableField<String> {
        return description
    }


}