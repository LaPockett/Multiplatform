package com.dian.prueba.openApi

import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class FakeHttpClient : HttpClientProvider {
    override suspend fun registerUser(payload: UserProfile): String {
        if (payload.username != "" && payload.email != "") {
            return "Fake API Response success"
        } else {
            return "Fake API Error"
        }
    }

    override suspend fun healthPing(): String {
        return "pong"
    }
    override suspend fun getUserProfile(payload: UserProfile): String {
        if (payload.username != "" && payload.email != "") {
            return "${payload.username} ${payload.email}"
        } else {
            return "Fake API Error"
        }
    }
}
class RequestsKtTest {
    @Test
    fun `register user with valid payload returns success`() {
        val payload = UserProfile(username = "string", email = "string")
        val requests = Requests(FakeHttpClient())
        runTest {
            val response = requests.registerUser(payload)
            assertEquals("Fake API Response success", response)
        }
    }

    @Test
    fun `register user with empty credentials throws exception`(){
        val payload = UserProfile(username = "", email = "")
        val requests = Requests(FakeHttpClient())
        runTest {
            val response = requests.registerUser(payload)
            assertEquals("Fake API Error", response)
        }
    }
    @Test
    fun `register user with one empty credential throws exception`(){
        val payload = UserProfile(username = "string", email = "")
        val requests = Requests(FakeHttpClient())
        runTest {
            val response = requests.registerUser(payload)
            assertEquals("Fake API Error", response)
        }
    }

    @Test
    fun `health ping returns pong`() {
        val requests = Requests(FakeHttpClient())
        runTest {
            val response = requests.healthPing()
            assertEquals("pong", response)
        }
    }

    @Test
    fun `get user profile with valid payload returns success`() {
        val payload = UserProfile(username = "string", email = "gnirts")
        val requests = Requests(FakeHttpClient())
        runTest {
            val response = requests.getUserProfile(payload)
            assertEquals("string gnirts", response)
        }
    }
    @Test
    fun `get user profile with empty credentials throws exception`() {
        val payload = UserProfile(username = "", email = "")
        val requests = Requests(FakeHttpClient())
        runTest {
            val response = requests.getUserProfile(payload)
            assertEquals("Fake API Error", response)
        }
    }
}