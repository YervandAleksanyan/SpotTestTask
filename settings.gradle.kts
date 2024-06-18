pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "SpotTestTask"
include(":app")
include(":core:network")
include(":core:utils")
include(":core:entities")
include(":feature-spots-requests:data")
include(":feature-spots-requests:domain")
include(":feature-spots-requests:presentation")
include(":feature-spots-requests:shared")
include(":feature-spots-info:shared")
include(":feature-spots-info:presentation")
