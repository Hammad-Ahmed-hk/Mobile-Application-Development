package com.example.studentregistration

/** Values entered on the registration form. */
data class RegistrationForm(
    val fullName: String = "",
    val rollNumber: String = "",
    val email: String = "",
    val programme: String = "",
    val studyMode: String = ""
)

/** One error message per field; `null` means the field is valid. */
data class RegistrationErrors(
    val fullName: String? = null,
    val rollNumber: String? = null,
    val email: String? = null,
    val programme: String? = null,
    val studyMode: String? = null
) {
    val hasErrors: Boolean
        get() = listOf(fullName, rollNumber, email, programme, studyMode).any { it != null }
}

/** Pure Kotlin validation rules, so they can be unit tested without a device. */
object RegistrationValidator {

    // Simple, practical e-mail check: something@something.tld (no spaces).
    private val EMAIL_PATTERN = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun isValidEmail(email: String): Boolean = EMAIL_PATTERN.matches(email.trim())

    fun validate(form: RegistrationForm): RegistrationErrors = RegistrationErrors(
        fullName = if (form.fullName.isBlank()) "Full name is required" else null,
        rollNumber = if (form.rollNumber.isBlank()) "Roll number is required" else null,
        email = when {
            form.email.isBlank() -> "Email is required"
            !isValidEmail(form.email) -> "Enter a valid email address"
            else -> null
        },
        programme = if (form.programme.isEmpty()) "Please select a programme" else null,
        studyMode = if (form.studyMode.isEmpty()) "Please select a study mode" else null
    )
}
