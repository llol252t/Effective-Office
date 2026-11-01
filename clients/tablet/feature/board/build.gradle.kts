import band.effective.office.backend.libs

plugins {
    id("band.effective.office.client.kmp.feature")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.findLibrary("jetbrains-lifecycle-viewmodel").get())
            implementation("org.jetbrains.compose.components:components-ui-tooling-preview:1.10.0")
        }
    }
}
