# MyBudget — Android starter project

MyBudget is an Android expense-tracking starter built with **Kotlin, Jetpack Compose, MVVM + Clean Architecture, Hilt and Room**.

## Included in this initial project

- Compose bottom navigation: Home, Categories, Transactions and Settings.
- Room database with starter categories (Food, Coffee, Clothes, Transport, Shopping, Bills, Health and Other).
- Create, edit and delete expense categories.
- Bank notification listener foundation using `NotificationListenerService`.
- Installed-app picker: users can search launchable apps on their phone and choose which packages MyBudget should monitor.
- Selected app package names are saved locally with Preferences DataStore and applied dynamically by the notification service.
- Package-agnostic starter parser for common English outgoing-payment notification formats.
- Automatic transaction persistence with duplicate-notification fingerprint protection.
- Manual category assignment for detected transactions.
- Dashboard showing today's detected spending and recent transactions.
- Parser unit tests.

## Tech stack

| Area | Technology |
|---|---|
| Language | Kotlin 2.2.21 |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Clean Architecture |
| Dependency injection | Hilt 2.57.1 |
| Local database | Room 2.8.5 |
| App-selection preferences | Preferences DataStore 1.2.1 |
| Navigation | Navigation Compose 2.9.8 |
| Async | Coroutines + Flow |
| Android Gradle Plugin | 8.13.2 |
| Gradle | 8.14.5 |
| JDK | 17 |

The Compose BOM is intentionally pinned to `2025.10.00` rather than the newest 2026 BOM, to keep this starter on the AGP 8.13.x toolchain instead of pulling in newer artifacts that may require AGP 9.x.