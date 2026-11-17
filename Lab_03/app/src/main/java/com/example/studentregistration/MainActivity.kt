package com.example.studentregistration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.studentregistration.ui.theme.StudentRegistrationTheme

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

    // 1. Text field states
    var fullName by remember { mutableStateOf("") }
    var rollNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // 2. Dropdown and radio button states
    var selectedProgramme by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var studyMode by remember { mutableStateOf("") }

    // 3. Checkbox states
    var mobileDevelopment by remember { mutableStateOf(false) }
    var webDevelopment by remember { mutableStateOf(false) }
    var softwareTesting by remember { mutableStateOf(false) }

    // 4. Switch and slider states
    var receiveUpdates by remember { mutableStateOf(false) }
    var studyHours by remember { mutableStateOf(0f) }

    // 5. Validation states
    var submitted by remember { mutableStateOf(false) }
    var registrationSuccess by remember { mutableStateOf(false) }

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

            // Heading
            Text(
                text = "Student Registration Form",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Please enter your registration details",
                style = MaterialTheme.typography.bodyMedium
            )

            HorizontalDivider()

            // 6. Full Name
            Text("Full Name")

            OutlinedTextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                    registrationSuccess = false
                },
                label = { Text("Enter full name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (submitted && fullName.trim().isEmpty()) {
                Text(
                    "Full name is required",
                    color = MaterialTheme.colorScheme.error
                )
            }

            // 7. Roll Number
            Text("Roll Number")

            OutlinedTextField(
                value = rollNumber,
                onValueChange = {
                    rollNumber = it
                    registrationSuccess = false
                },
                label = { Text("Enter roll number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (submitted && rollNumber.trim().isEmpty()) {
                Text(
                    "Roll number is required",
                    color = MaterialTheme.colorScheme.error
                )
            }

            // 8. Email
            Text("Email Address")

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    registrationSuccess = false
                },
                label = { Text("Enter email address") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (submitted && email.trim().isEmpty()) {
                Text(
                    "Email is required",
                    color = MaterialTheme.colorScheme.error
                )
            }

            // 9. Programme Dropdown
            Text("Programme")

            Box(modifier = Modifier.fillMaxWidth()) {

                OutlinedTextField(
                    value = selectedProgramme.ifEmpty {
                        "Select programme"
                    },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Programme") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            expanded = true
                        }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {

                    listOf(
                        "Software Engineering Technology",
                        "Computer Science",
                        "Information Technology"
                    ).forEach { programme ->

                        DropdownMenuItem(
                            text = { Text(programme) },
                            onClick = {
                                selectedProgramme = programme
                                expanded = false
                                registrationSuccess = false
                            }
                        )
                    }
                }
            }

            if (submitted && selectedProgramme.isEmpty()) {
                Text(
                    "Please select a programme",
                    color = MaterialTheme.colorScheme.error
                )
            }

            // 10. Study Mode
            Text(
                text = "Study Mode",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = studyMode == "On Campus",
                    onClick = {
                        studyMode = "On Campus"
                        registrationSuccess = false
                    }
                )

                Text(
                    text = "On Campus",
                    modifier = Modifier.clickable {
                        studyMode = "On Campus"
                        registrationSuccess = false
                    }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = studyMode == "Online",
                    onClick = {
                        studyMode = "Online"
                        registrationSuccess = false
                    }
                )

                Text(
                    text = "Online",
                    modifier = Modifier.clickable {
                        studyMode = "Online"
                        registrationSuccess = false
                    }
                )
            }

            if (submitted && studyMode.isEmpty()) {
                Text(
                    "Please select a study mode",
                    color = MaterialTheme.colorScheme.error
                )
            }

            // 11. Interests
            Text(
                text = "Areas of Interest",
                style = MaterialTheme.typography.titleMedium
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = mobileDevelopment,
                    onCheckedChange = {
                        mobileDevelopment = it
                        registrationSuccess = false
                    }
                )

                Text(
                    "Mobile Development",
                    modifier = Modifier.clickable {
                        mobileDevelopment = !mobileDevelopment
                        registrationSuccess = false
                    }
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = webDevelopment,
                    onCheckedChange = {
                        webDevelopment = it
                        registrationSuccess = false
                    }
                )

                Text(
                    "Web Development",
                    modifier = Modifier.clickable {
                        webDevelopment = !webDevelopment
                        registrationSuccess = false
                    }
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = softwareTesting,
                    onCheckedChange = {
                        softwareTesting = it
                        registrationSuccess = false
                    }
                )

                Text(
                    "Software Testing",
                    modifier = Modifier.clickable {
                        softwareTesting = !softwareTesting
                        registrationSuccess = false
                    }
                )
            }

            // 12. Course Updates Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Receive course updates")

                Switch(
                    checked = receiveUpdates,
                    onCheckedChange = {
                        receiveUpdates = it
                        registrationSuccess = false
                    }
                )
            }

            // 13. Weekly Study Hours Slider
            Text(
                text = "Weekly Study Hours: ${studyHours.toInt()}",
                style = MaterialTheme.typography.titleMedium
            )

            Slider(
                value = studyHours,
                onValueChange = {
                    studyHours = it
                    registrationSuccess = false
                },
                valueRange = 0f..20f,
                steps = 19
            )

            // 14. Register Button
            Button(
                onClick = {

                    submitted = true

                    registrationSuccess =
                        fullName.trim().isNotEmpty() &&
                                rollNumber.trim().isNotEmpty() &&
                                email.trim().isNotEmpty() &&
                                selectedProgramme.isNotEmpty() &&
                                studyMode.isNotEmpty()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }

            // 15. Success Message
            if (registrationSuccess) {
                Text(
                    text = "Registration form completed successfully",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // 16. Reset Button
            OutlinedButton(
                onClick = {

                    fullName = ""
                    rollNumber = ""
                    email = ""

                    selectedProgramme = ""
                    expanded = false
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
