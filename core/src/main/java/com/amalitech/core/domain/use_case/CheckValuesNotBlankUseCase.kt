package com.amalitech.core.domain.use_case

import com.amalitech.core.R
import com.amalitech.core.util.UiText

class CheckValuesNotBlankUseCase {

    operator fun invoke(vararg value: String): UiText? {
        value.forEach { string ->
            if (string.isBlank())
                return UiText.StringResource(R.string.error_value_is_blank)
        }
        return null
    }
}
