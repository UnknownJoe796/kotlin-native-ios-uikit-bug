pluginManagement {
    repositories {
        mavenCentral()
        maven("https://dl.bintray.com/kotlin/kotlin-dev")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

enableFeaturePreview("GRADLE_METADATA")

include(":uikit")
include(":uikit-lib")
