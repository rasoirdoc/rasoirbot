plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
}

val compileOptions: org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions.() -> Unit = {
    languageVersion = "1.6"
    jvmTarget = "17"
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks

compileKotlin.kotlinOptions(compileOptions)