package com.karate

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


/*
Either we use a companion with @JvmStatic or we use a PER_CLASS lifecycle:

	companion object {
		private var wireMockServer = WireMockServer(WireMockConfiguration().port(8087))

		@BeforeAll
		@JvmStatic
		private fun setup() {
			wireMockServer.start()
		}


		@AfterClass
		@JvmStatic
		private fun teardown() {
			wireMockServer.stop()
		}
	}


	// OR using JUnit Rules

	companion object {
        @ClassRule
        @JvmField
        val wireMockRule = WireMockRule(8087)
    }
 */

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestPlain {

	private var wireMockServer = WireMockServer(WireMockConfiguration().port(8087))

	@BeforeAll
	private fun setup() {
		wireMockServer.start()
	}


	@AfterAll
	private fun teardown() {
		wireMockServer.stop()
	}


	@Test
	fun hello(): Unit {
		val c = HttpClient.newBuilder().build()
		val req = HttpRequest.newBuilder(URI("http://localhost:8087/my/resource")).GET().build()
		val result = c.send(req, HttpResponse.BodyHandlers.ofString()).body()

		assertTrue(result.contains("Hello"))

		// check the calls received by the server
		val gotReq = getRequestedFor(urlMatching("/my/resource/*"))
			.withRequestBody(absent())
			.withHeader("Content-Type", absent()) // matching("application/json"))
		wireMockServer.verify(gotReq)
		wireMockServer.verify(1, gotReq)
	}
}

