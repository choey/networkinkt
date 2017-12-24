package com.egorzh.networkinkt

import org.w3c.xhr.XMLHttpRequest
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * @author Egor Zhdan
 */
actual class HTTPRequest actual constructor(val url: String, val method: String = "GET") {
    actual suspend fun loadText(): String = suspendCoroutine { continuation ->
        val req = XMLHttpRequest()
        req.open(method, url)
        req.onreadystatechange = {
            if (req.readyState == 4.toShort()) {
                if (req.status == 200.toShort()) {
                    continuation.resume(req.responseText)
                } else {
                    continuation.resumeWithException(HTTPException(req.status.toInt()))
                }
            }
        }
        req.send()
    }
}