package com.example.studentregistration

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RegistrationValidatorTest {

    private val validForm = RegistrationForm(
        fullName = "Hammad Ahmed",
        rollNumber = "2023-SE-01",
        email = "student@example.com",
        programme = "Computer Science",
        studyMode = "On Campus"
    )

    @Test
    fun completeForm_hasNoErrors() {
        assertFalse(RegistrationValidator.validate(validForm).hasErrors)
    }

    @Test
    fun emptyForm_reportsEveryRequiredField() {
        val errors = RegistrationValidator.validate(RegistrationForm())
        assertEquals("Full name is required", errors.fullName)
        assertEquals("Roll number is required", errors.rollNumber)
        assertEquals("Email is required", errors.email)
        assertEquals("Please select a programme", errors.programme)
        assertEquals("Please select a study mode", errors.studyMode)
        assertTrue(errors.hasErrors)
    }

    @Test
    fun whitespaceOnlyName_isRejected() {
        val errors = RegistrationValidator.validate(validForm.copy(fullName = "   "))
        assertEquals("Full name is required", errors.fullName)
    }

    @Test
    fun malformedEmail_isRejected() {
        listOf("plainaddress", "no@tld", "two@@example.com", "spa ce@example.com", "@example.com")
            .forEach { bad ->
                val errors = RegistrationValidator.validate(validForm.copy(email = bad))
                assertEquals("Enter a valid email address", errors.email)
            }
    }

    @Test
    fun wellFormedEmail_isAccepted() {
        listOf("a@b.co", "first.last+tag@sub.example.org", " padded@example.com ")
            .forEach { good ->
                assertNull(RegistrationValidator.validate(validForm.copy(email = good)).email)
            }
    }
}
