pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "AwesomeManager"
include (":app")

include(":feature:menu")
include(":feature:auth")
include(":feature:home")
include(":feature:intro")
include(":feature:account:accounts")
include(":feature:account:account-editor")
include(":feature:account:account-details")
include(":feature:transaction:transaction-editor")
include(":feature:transaction:transactions")
include(":feature:transaction:transaction-details")

include(":core:data")
include(":core:common")
include(":core:database")
include(":core:datastore")
include(":core:network")
include(":core:notification")
include(":core:ui")
include(":core:designsystem")
include(":core:domain")
include(":core:model")

include(":sync")
