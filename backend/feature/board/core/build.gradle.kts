import org.gradle.kotlin.dsl.dependencies

plugins {
    id("band.effective.office.backend.spring-data-jpa")
}

group = "band.effective.office.backend.board.core"

dependencies {
    implementation(libs.jakarta)
    implementation(libs.jakarta.servlet.api)
    implementation(libs.springdoc.openapi.starter.webmvc.ui)
}
