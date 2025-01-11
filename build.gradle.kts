plugins {
    java
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    id("org.sonarqube") version "6.0.1.5171"
}

group = "org.fugerit.java"
version = "0.1.9"

val freemarkerVersion = "2.3.34"
val graalSdkVersion = "24.1.1"
val junitJupiterVersion = "5.11.4"

val profile = findProperty("profile") as String? ?: "default"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.freemarker:freemarker:$freemarkerVersion")
    compileOnly("org.graalvm.sdk:graal-sdk:$graalSdkVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
    withJavadocJar()
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("PASSED", "FAILED", "SKIPPED")
    }
}

object Meta {
    const val desc = "GraalVM support for ApacheFreemarker."
    const val githubRepo = "fugerit-org/freemarker-native"
    const val release = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
    const val snapshot = "https://oss.sonatype.org/content/repositories/snapshots/"
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("Freemarker Native")
                description.set(Meta.desc)
                url.set("https://github.com/${Meta.githubRepo}")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("fugerit79")
                        name.set("Matteo Franci a.k.a. Fugerit")
                        email.set("m@fugerit.org")
                        url.set("https://github.com/fugerit79")
                        organization.set("Fugerit")
                        organizationUrl.set("https://github.com/fugerit-org")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/your-repo/freemarker-native.git")
                    developerConnection.set("scm:git:ssh://github.com/your-repo/freemarker-native.git")
                    url.set("https://github.com/your-repo/freemarker-native")
                }
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            val ossrhUsername = providers.environmentVariable("MAVEN_USERNAME")
            val ossrhPassword = providers.environmentVariable("MAVEN_PASSWORD")
            if (ossrhUsername.isPresent && ossrhPassword.isPresent) {
                username.set(ossrhUsername.get())
                password.set(ossrhPassword.get())
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}

sonar {
    properties {
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.projectKey", "fugerit-org_freemarker-native")
        property("sonar.organization", "fugerit-org")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}