package com.example.studentregistration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentregistration.ui.theme.StudentRegistrationTheme

private val PROGRAMMES = listOf(
    "Software Engineering Technology",
    "Computer Science",
    "Information Technology"
)
private val STUDY_MODES = listOf("On Campus", "Online")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudentRegistrationTheme {
                StudentRegistrationScreen()
            }
        }
    }
}

@Composable
fun StudentRegistrationScreen() {
    // rememberSaveable keeps what the user typed when the screen is rotated.
    var fullName by rememberSaveable { mutableStateOf("") }
    var rollNumber by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    var programme by rememberSaveable { mutableStateOf("") }
    var programmeMenuOpen by rememberSaveable { mutableStateOf(false) }
    var studyMode by rememberSaveable { mutableStateOf("") }

    var mobileDevelopment by rememberSaveable { mutableStateOf(false) }
    var webDevelopment by rememberSaveable { mutableStateOf(false) }
    var softwareTesting by rememberSaveable { mutableStateOf(false) }

    var receiveUpdates by rememberSaveable { mutableStateOf(false) }
    var studyHours by rememberSaveable { mutableFloatStateOf(0f) }

    var submitted by rememberSaveable { mutableStateOf(false) }
    var registrationSuccess by rememberSaveable { mutableStateOf(false) }

    // Validation rules live in RegistrationValidator so they can be unit tested.
    val errors = RegistrationValidator.validate(
        RegistrationForm(fullName, rollNumber, email, programme, studyMode)
    )

    // Any edit after pressing Register hides the old success message.
    fun edited() {
        registrationSuccess = false
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Student Registration Form",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Please enter your registration details",
                style = MaterialTheme.typography.bodyMedium
            )

            HorizontalDivider()

            FormTextField(
                label = "Full Name",
                hint = "Enter full name",
                value = fullName,
                onValueChange = { fullName = it; edited() },
                error = errors.fullName.takeIf { submitted }
            )

            FormTextField(
                label = "Roll Number",
                hint = "Enter roll number",
                value = rollNumber,
                onValueChange = { rollNumber = it; edited() },
                error = errors.rollNumber.takeIf { submitted }
            )

            FormTextField(
                label = "Email Address",
                hint = "Enter email address",
                value = email,
                onValueChange = { email = it; edited() },
                error = errors.email.takeIf { submitted },
                keyboardType = KeyboardType.Email
            )

            // Programme dropdown
            Text("Programme")
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = programme,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Programme") },
                    placeholder = { Text("Select programme") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { programmeMenuOpen = true }
                )
                DropdownMenu(
                    expanded = programmeMenuOpen,
                    onDismissRequest = { programmeMenuOpen = false }
                ) {
                    PROGRAMMES.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                programme = option
                                programmeMenuOpen = false
                                edited()
                            }
                        )
                    }
                }
            }
            ErrorText(errors.programme.takeIf { submitted })

            // Study mode (radio buttons)
            Text(text = "Study Mode", style = MaterialTheme.typography.titleMedium)
            STUDY_MODES.forEach { mode ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = studyMode == mode,
                        onClick = { studyMode = mode; edited() }
                    )
                    Text(
                        text = mode,
                        modifier = Modifier.clickable { studyMode = mode; edited() }
                    )
                }
            }
            ErrorText(errors.studyMode.takeIf { submitted })

            // Areas of interest (checkboxes)
            Text(text = "Areas of Interest", style = MaterialTheme.typography.titleMedium)
            LabeledCheckbox("Mobile Development", mobileDevelopment) {
                mobileDevelopment = it; edited()
            }
            LabeledCheckbox("Web Development", webDevelopment) {
                webDevelopment = it; edited()
            }
            LabeledCheckbox("Software Testing", softwareTesting) {
                softwareTesting = it; edited()
            }

            // Course updates switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Receive course updates")
                Switch(
                    checked = receiveUpdates,
                    onCheckedChange = { receiveUpdates = it; edited() }
                )
            }

            // Weekly study hours slider
            Text(
                text = "Weekly Study Hours: ${studyHours.toInt()}",
                style = MaterialTheme.typography.titleMedium
            )
            Slider(
                value = studyHours,
                onValueChange = { studyHours = it; edited() },
                valueRange = 0f..20f,
                steps = 19
            )

            Button(
                onClick = {
                    submitted = true
                    registrationSuccess = !errors.hasErrors
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }

            if (registrationSuccess) {
                Text(
                    text = "Registration form completed successfully",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            OutlinedButton(
                onClick = {
                    fullName = ""
                    rollNumber = ""
                    email = ""
                    programme = ""
                    programmeMenuOpen = false
                    studyMode = ""
                    mobileDevelopment = false
                    webDevelopment = false
                    softwareTesting = false
                    receiveUpdates = false
                    studyHours = 0f
                    submitted = false
                    registrationSuccess = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reset")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/** Heading, single-line text field and (optional) error message. */
@Composable
private fun FormTextField(
    label: String,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    error: String?,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Text(label)
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(hint) },
        isError = error != null,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    ErrorText(error)
}

/** Shows a validation message in the theme's error colour; draws nothing for null. */
@Composable
private fun ErrorText(message: String?) {
    if (message != null) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
    }
}

/** A checkbox whose label is also clickable. */
@Composable
private fun LabeledCheckbox(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(label, modifier = Modifier.clickable { onCheckedChange(!checked) })
    }
}

@Preview(showBackground = true)
@Composable
private fun StudentRegistrationScreenPreview() {
    StudentRegistrationTheme {
        StudentRegistrationScreen()
    }
}
