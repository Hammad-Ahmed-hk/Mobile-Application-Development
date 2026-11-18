# Lab 03: Student Registration Form

A Jetpack Compose app that shows a student registration form and validates it.

## Features

- Text fields: full name, roll number, email (email keyboard)
- Programme dropdown, study-mode radio buttons, interest checkboxes
- Course-updates switch and a weekly study-hours slider (0 to 20)
- Validation with messages under each field; email must be well formed
- **Register** shows a success message only when the form is valid; **Reset** clears everything
- Entered values survive screen rotation (`rememberSaveable`)

## Structure

```
app/src/main/java/com/example/studentregistration/
├── MainActivity.kt             # Compose screen and small reusable composables
├── RegistrationValidator.kt    # Pure Kotlin validation rules
└── ui/theme/                   # Material 3 theme
app/src/test/.../RegistrationValidatorTest.kt   # Unit tests for the rules
```

## Run it

Open this folder in Android Studio, let Gradle sync, and run the `app` configuration (minSdk 24).

Run the unit tests with `./gradlew test` (or `gradlew.bat test` on Windows).

## Report

[2023-SE-01_Lab_Task_03_Student_Registration.docx](2023-SE-01_Lab_Task_03_Student_Registration.docx)
