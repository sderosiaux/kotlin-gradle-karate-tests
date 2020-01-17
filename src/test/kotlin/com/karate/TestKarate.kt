package com.karate

import com.intuit.karate.KarateOptions
import com.intuit.karate.Runner
import com.intuit.karate.junit5.Karate
import net.masterthought.cucumber.Configuration
import net.masterthought.cucumber.ReportBuilder
import org.apache.commons.io.FileUtils
import java.io.File

// Doesn't work? @KarateOptions(features = ["classpath:com/karate/print.feature"])
class TestKarate {

	@Karate.Test
	fun print(): Karate {
		//return Karate().feature("print").relativeTo(this.javaClass) // one test here

		return Karate().feature("classpath:com/karate") // all tests here
	}

}
