plugins {
    java
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
}

group = "org.fugerit.java"
version = "0.1.0"

val freemarkerVersion = "2.3.34"
val graalSdkVersion = "24.1.1"

val profile = findProperty("profile") as String? ?: "default"

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

object Meta {
    const val desc = "GraalVM support for ApacheFreemarker."
    const val githubRepo = "fugerit-org/freemarker-native"
    const val release = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
    const val snapshot = "https://oss.sonatype.org/content/repositories/snapshots/"
}

publishing {
    publications {

        when (profile) {
            "prod" -> create<MavenPublication>("mavenJava") {
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
            else -> create<MavenPublication>("mavenJava") {
                from(components["java"])
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            val ossrhUsername = providers.gradleProperty("ossrhUsername")
            val ossrhPassword = providers.gradleProperty("ossrhPassword")
            if (ossrhUsername.isPresent && ossrhPassword.isPresent) {
                username.set(ossrhUsername.get())
                password.set(ossrhPassword.get())
            }
        }
    }
}

when (profile) {
    "prod" -> signing {
        useInMemoryPgpKeys(findProperty("signing.keyId") as String?, findProperty("signing.secretKey") as String?, findProperty("signing.password") as String?)
        sign(publishing.publications["mavenJava"])
    }
}

