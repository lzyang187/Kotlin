package coroutine

import kotlinx.coroutines.*

fun main() {
//    cancel()
//    cancelFailed()
//    cancelSuccess()
//    runInFinally()
//    timeout()
    timeoutOrNull()
}

/**
 * 取消协程的执行
 */
private fun cancel() {
    runBlocking {
        val job = launch {
            try {
                repeat(1000) {
                    println("job: I'm sleeping $it ...")
                    delay(500)
                }
            } finally {
                println("job: I'm running finally") //在 finally 中释放资源
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancel()// 取消该作业
        job.join() // 等待作业执行结束
        // 合并了对 cancel 以及 join 的调用
//        job.cancelAndJoin()
        println("main: Now I can quit.")
    }
}

/**
 * 取消是协作的：一段协程代码必须协作才能被取消，如果协程正在执行计算任务，并且没有检查取消的话，那么它是不能被取消的
 */
private fun cancelFailed() {
    runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) { // 一个执行计算的循环，只是为了占用 CPU
                // 每秒打印消息两次
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }
}

/**
 * 使计算代码可取消：
 * 第一种方法是定期调用挂起函数来检查取消。对于这种目的 yield 是一个好的选择。
 * 另一种方法是显式的检查取消状态。让我们试试第二种方法，isActive 是一个可以被使用在 CoroutineScope 中的扩展属性
 */
private fun cancelSuccess() {
    runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (isActive) { // 可以被取消的计算循环
                // 每秒打印消息两次
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }
}

/**
 * 在finally中运行不能取消的代码块：任何尝试在 finally 块中调用挂起函数的行为都会抛出 CancellationException，因为这里持续运行的代码是可以被取消的。
 * 通常，这并不是一个问题，所有良好的关闭操作（关闭一个文件、取消一个作业、或是关闭任何一种通信通道）通常都是非阻塞的，并且不会调用任何挂起函数。
 * 然而，在真实的案例中，当你需要挂起一个被取消的协程，你可以将相应的代码包装在 withContext(NonCancellable) {……} 中，并使用 withContext 函数以及 NonCancellable 上下文
 */
private fun runInFinally() {
    runBlocking {
        val job = launch {
            try {
                repeat(1000) {
                    println("job：I'm sleeping $it ...")
                    delay(500)
                }
            } finally {
                withContext(NonCancellable) {
                    println("job: I'm running finally")
                    delay(1000L)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }
}

/**
 * 超时：在实践中绝大多数取消一个协程的理由是它有可能超时
 */
private fun timeout() {
    runBlocking {
        try {
            withTimeout(1300) {// withTimeout 抛出了 TimeoutCancellationException
                repeat(1000) {
                    println("job：I'm sleeping $it ...")
                    delay(500)
                }
            }
        } catch (e: Exception) {
            println("异常信息：$e")
        } finally {
            println("job: I'm running finally") //在 finally 中释放资源
        }
    }
}

/**
 *  withTimeoutOrNull 通过返回 null 来进行超时操作，从而替代抛出一个异常
 */
private fun timeoutOrNull() {
    runBlocking {
        val result = withTimeoutOrNull(1300) {
            repeat(1000) {
                println("job：I'm sleeping $it ...")
                delay(500)
            }
            "Done" // 如果没有超时，则返回Done
        }
        println("result = $result")
    }
}