plugins {
    id("java")
    id("maven-publish")
    id("signing")
}

group = "com.trustcomponent"
java.sourceCompatibility = JavaVersion.VERSION_17

version = if (project.hasProperty("version")) {
    project.version
} else {
    "0.0.1-SNAPSHOT" // Fallback-Version
}

val isReleaseVersion = System.getenv("CI_COMMIT_TAG") != null

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    implementation("org.jetbrains:annotations:24.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

val sourceJar = tasks.register<Jar>("sourceJar") {
    from(sourceSets.getByName("main").allSource)
    archiveClassifier.set("sources")
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    from(tasks.named("javadoc"))
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("trustcaptcha") {
            from(components["java"])
            artifact(sourceJar.get())
            artifact(javadocJar.get())

            groupId = "com.trustcomponent"
            artifactId = "trustcaptcha"
            version = this.version.toString()

            pom {
                name.set("TrustCaptcha - CAPTCHA for Java")
                description.set("TrustCaptcha – Privacy-first CAPTCHA solution for Java. GDPR-compliant, bot protection made in Europe. Compatible with Kotlin, Scala and Groovy.")
                url.set("https://www.trustcomponent.com/en/products/captcha/integrations/java-captcha")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/trustcomponent/trustcaptcha-java.git")
                    developerConnection.set("scm:git:ssh://git@github.com/trustcomponent/trustcaptcha-java.git")
                    url.set("https://github.com/trustcomponent/trustcaptcha-java")
                }

                developers {
                    developer {
                        name.set("TrustComponent")
                        email.set("mail@trustcomponent.com")
                        organization.set("Trustcaptcha GmbH")
                        organizationUrl.set("https://www.trustcomponent.com/en")
                    }
                }
            }
        }
    }
    repositories {
        if (isReleaseVersion) {
            maven {
                name = "MavenCentral"
                url = uri("https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/")
                credentials {
                    username = System.getenv("SONATYPE_USER_TOKEN_USER")
                    password = System.getenv("SONATYPE_USER_TOKEN_PASS")
                }
            }
        } else {
            maven {
                url = uri("${System.getenv("CI_API_V4_URL")}/projects/${System.getenv("CI_PROJECT_ID")}/packages/maven")
                name = "GitLab"
                credentials(HttpHeaderCredentials::class) {
                    name = "Job-Token"
                    value = System.getenv("CI_JOB_TOKEN")
                }
                authentication {
                    create("header", HttpHeaderAuthentication::class)
                }
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(System.getenv("SIGNING_KEY_ID"), System.getenv("SIGNING_PRIVATE_KEY"), System.getenv("SIGNING_PASSWORD"))
    isRequired = System.getenv("SIGNING_KEY_ID") != null

    publishing.publications.withType<MavenPublication>().configureEach {
        sign(this)
    }
}

tasks.register("publishToGitLab") {
    dependsOn("publish")
}
