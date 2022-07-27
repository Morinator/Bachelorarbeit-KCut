import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    application
}

group = "me.moritz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation(kotlin("test"))
    implementation("com.github.dpaukov:combinatoricslib3:3.3.3")
    implementation("org.jgrapht:jgrapht-core:1.5.1")
    // implementation(files("C:\\Program Files\\IBM\\ILOG\\CPLEX_Studio221\\cplex\\lib\\cplex.jar"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
