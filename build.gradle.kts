plugins {
	kotlin("jvm") version "1.3.61"
	id("idea")
	id("com.github.spacialcircumstances.gradle-cucumber-reporting") version "0.1.15"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))

	implementation("com.intuit.karate", "karate-apache", "0.9.4")
	implementation("com.intuit.karate", "karate-junit5", "0.9.4")

	testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")

	testImplementation("com.github.tomakehurst:wiremock-jre8:2.25.1")
	testImplementation("net.masterthought:cucumber-reporting:5.0.1")
}

tasks.test {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
	}
	// ALWAYS run the tests
	outputs.upToDateWhen { false }
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
	kotlinOptions.jvmTarget = "1.8"
}

sourceSets {
	test {
		resources {
			srcDir(file("src/test/kotlin"))
			exclude("**/*.kt")
		}
	}
}

/*
Karate doesn't generate the json it seems?
(this task is run after "test")

cucumberReports {
	outputDir = file("build/cucumber-reports")
	buildId = "0"
	reports = files(
		"build/surefire-reports/com.karate.print.json",
		"build/surefire-reports/com.karate.test.json"
	)
}
 */
