plugins {
    java
    `maven-publish`
}

group = "org.fugerit.java"
version = "0.1.0"

val freemarkerVersion = "2.3.34"
val graalSdkVersion = "24.1.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.freemarker:freemarker:$freemarkerVersion")
    compileOnly("org.graalvm.sdk:graal-sdk:$graalSdkVersion")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

