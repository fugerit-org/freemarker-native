plugins {
    java
    `maven-publish`
}

group = "org.fugerit.java"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.freemarker:freemarker:2.3.34")
    compileOnly("org.graalvm.sdk:graal-sdk:24.1.1")
}

java {
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

