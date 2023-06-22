package com.amalitech.onboarding.signup.use_case

import com.amalitech.core.util.UiText
import com.amalitech.core.R

class CheckValuesNotBlank {

    operator fun invoke(vararg value: String): UiText? {
        value.forEach { string ->
            if (string.isBlank())
                return UiText.StringResource(R.string.error_value_is_blank)
        }
        return null
    }
}
