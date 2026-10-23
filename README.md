# PantryChef

PantryChef is an Android application built in Java that helps users reduce food waste by tracking the ingredients they have at home. The app uses a strict-matching algorithm to suggest recipes that can be cooked using *only* the ingredients currently in the user's pantry.

## Features
- Full CRUD (Create, Read, Update, Delete) functionality for pantry items.
- Pre-loaded database of 15+ recipes.
- **Strict-Matching Algorithm:** Only suggests recipes where the user has 100% of the required ingredients in the correct quantities.
- Robust matching (ignores plurals and case, e.g., "Tomato" matches "tomatoes").
- Settings screen with persistent SharedPreferences toggle.

## Database Choice & Justification
**SQLite** (via SQLiteOpenHelper) was chosen for this project.
**Why?** SQLite is perfect for this app because the data is highly structured (relational) and local. A pantry is an offline, personal concept, so storing the data directly on the device ensures the app works perfectly without an internet connection. It is lightweight, requires no external server setup, and offers fast querying for the strict-matching algorithm.

## Setup & Run Instructions
1. Clone this repository to your local machine.
2. Open the project in **Android Studio**.
3. Allow Gradle to sync and download any necessary dependencies.
4. Select an Android Emulator (API 24 or higher) or connect a physical Android device.
5. Click the **Run** (green play) button to build and launch the app.