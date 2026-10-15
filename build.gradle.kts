plugins {
    java
    application
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(27)
    }
}

dependencies {
    // Processing: drawing, windows, saving frames
    implementation("org.processing:core:4.5.7")

    testImplementation(platform("org.junit:junit-bom:5.12.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

application {
    mainClass = "checkout.App"
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("runHeadless") {
    group = "application"
    description = "Runs the simulation without a window."
    classpath = sourceSets["main"].runtimeClasspath
    mainClass = "checkout.Headless"
}
