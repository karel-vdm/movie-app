package com.karel.movieapp.presentation.widget

import androidx.annotation.StringRes
import java.io.Serializable

data class EmptyStateViewModel(
    @StringRes val emptyStateStringResource: Int = -1
) : Serializable, IAdapterItemViewModel