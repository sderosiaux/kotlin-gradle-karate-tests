package com.karate

import com.intuit.karate.Runner
import net.masterthought.cucumber.Configuration
import net.masterthought.cucumber.ReportBuilder
import org.apache.commons.io.FileUtils
import java.io.File


// Outside of JUnit and test infra, does its own thing
object RunAll {
	@JvmStatic
	fun main(args: Array<String>) {
		// look at the classpath for .feature
		val results = Runner.parallel(javaClass, 5) // 5=threads
		generateReport(results.reportDir)

		// => generate target/surefire-reports and target/cucumber-html-reports
	}

	private fun generateReport(karateOutputPath: String?) {
		val jsonFiles = FileUtils.listFiles(File(karateOutputPath), arrayOf("json"), true).map { it.absolutePath }
		ReportBuilder(jsonFiles, Configuration(File("target"), "demo")).generateReports()
	}
}

