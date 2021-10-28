package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * 各种流操作符
 */
fun main() {
    runBlocking {
        // 使用 map 操作符映射出结果，即使执行一个长时间的请求操作也可以使用挂起函数来实现
        (1..3).asFlow()
                .map {
                    performRequest(it)
                }
                .collect {
                    println("map：$it")
                }

        //  使用 transform 操作符，我们可以 发射 任意值任意次
        (1..3).asFlow()
                .transform {
                    emit("Making request $it")
                    emit(performRequest(it))
                }
                .collect {
                    println("transform：$it")
                }

        // 限长过渡操作符（例如 take）在流触及相应限制的时候会将它的执行取消。
        // 协程中的取消操作总是通过抛出异常来执行，这样所有的资源管理函数（如 try {...} finally {...} 块）会在取消的情况下正常运行
        (1..3).asFlow()
                .take(2)// 只获取前两个
                .collect {
                    println("take：$it")
                }

        // 转化为各种集合，例如 toList 与 toSet。
        (1..3).asFlow()
                .toList()
                .forEach {
                    println("toList $it")
                }

        // 获取第一个（first）值
        val first = (1..3).asFlow()
                .first()
        println("first：$first")

        // 确保流发射单个（single）值
        val single = flow {
            emit("发送第一个值")
//            emit(2)
        }.single()
        println("single：$single")

        // 使用 reduce 与 fold 将流规约到单个值
        val reduce = listOf("str1,str2").asFlow()
                .reduce { accumulator, value ->
                    "$accumulator $value"
                }
        println("reduce：$reduce")

        // filter：流是连续的，从上游到下游每个过渡操作符都会处理每个发射出的值然后再交给末端操作符。
        (1..5).asFlow()
                .filter {
                    println("filter：$it")
                    it % 2 == 0
                }
                .map {
                    println("map：$it")
                    "map：$it"
                }
                .collect {
                    println("collect：$it")
                }

        // flowOn 操作符：该函数用于更改流发射的上下文
        flow {
            for (i in 1..3) {
                delay(100)
                log("Emitting $i")
                emit(i)
            }
        }.flowOn(Dispatchers.Default).collect {
            log("Collected $it")
        }

        // 缓冲：从收集流所花费的时间来看，将流的不同部分运行在不同的协程中将会很有帮助，特别是当涉及到长时间运行的异步操作时
        val time1 = measureTimeMillis {
            simple().collect {
                delay(300)// 假装我们花费 300 毫秒来处理它
            }
        }
        println("Collected in $time1 ms")
        // 可以在流上使用 buffer 操作符来并发运行这个 simple 流中发射元素的代码以及收集的代码， 而不是顺序运行它们
        val time2 = measureTimeMillis {
            simple().buffer().collect {
                delay(200)
            }
        }
        println("buffer Collected in $time2 ms")

        // 处理最新值，collectLatest：取消并重新发射最后一个值
        simple().collectLatest {
            println("Collecting $it")
            delay(300) // 假装我们花费 300 毫秒来处理它
            println("Done $it")
        }

        // zip：组合两个流中的相关值
        val num = (1..3).asFlow()
        val str = flowOf("one", "two")
        num.zip(str) { a, b -> "$a -> $b" } // 组合单个字符串
                .collect { println(it) } // 收集并打印

        // 流拥有 onCompletion 过渡操作符，它在流完全收集时调用
        simple().onCompletion {
            println("Done $this")
        }.collect {
            println("$it")
        }
        // onCompletion 的主要优点是其 lambda 表达式的可空参数 Throwable 可以用于确定流收集是正常完成还是有异常发生
        val exceptionFlow = flow {
            emit(1)
            throw RuntimeException()
        }
        exceptionFlow.onCompletion { cause ->
            println("onCompletion 发生了异常：$cause")
        }.catch { cause ->
            println("catch 发生了异常：$cause")
        }.collect {
            println("$it")
        }


    }
}

private fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100) // 假装我们异步等待了 100 毫秒
        emit(i) // 发射下一个值
    }
}

private fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

private suspend fun performRequest(request: Int): String {
    delay(1000)
    return "response $request"
}