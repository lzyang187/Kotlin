package coroutine_guolin

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.SocketException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * 借助suspendCoroutine函数将传统回调机制的写法大幅简化
 */

interface CallBack<T> {
    fun onSuccess(t: T)
    fun onFailed(e: java.lang.Exception)
}

// 传统回调
fun request(address: String, callBack: CallBack<String>) {
    Thread {
        Thread.sleep(2000)
//        callBack.onSuccess("success")
        callBack.onFailed(SocketException("链接异常"))
    }.start()
}

// 协程简化
suspend fun requestSuspend(address: String): String {
    println("suspendCoroutine外的运行线程：${Thread.currentThread().name}")
    return suspendCoroutine {
        println("suspendCoroutine内的运行线程：${Thread.currentThread().name}")
        request(address, object : CallBack<String> {
            override fun onSuccess(t: String) {
                // 请求成功就调用Continuation的resume()方法恢复被挂起的协程，并传入服务器响应的数据，该值会成为suspendCoroutine函数的返回值
                it.resume(t)
            }

            override fun onFailed(e: java.lang.Exception) {
                // 请求失败，就调用Continuation的resumeWithException()恢复被挂起的协程
                it.resumeWithException(e)
            }
        })
    }
}

fun main() {
    request("address", object : CallBack<String> {
        override fun onSuccess(t: String) {
            println(t)
        }

        override fun onFailed(e: Exception) {
            println("回调写法的结果：$e")
        }
    })

    runBlocking {
        launch {
            try {
                val t = requestSuspend(address = "address")
                println("requestSuspend = $t")
            } catch (e: Exception) {
                println("requestSuspend = $e")
            }
        }
    }

}