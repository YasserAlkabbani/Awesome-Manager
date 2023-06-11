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
include(":feature:account:search")
include(":feature:account:create-update")
include(":feature:account:details")
include(":feature:transaction:create-update")
include(":feature:transaction:search")
include(":feature:transaction:details")

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
