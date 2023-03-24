package coroutine_guolin

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        // 使用zip连接的两个flow，它们之间是并行的运行关系。这点和flatMap差别很大，
        // 因为flatMap的运行方式是一个flow中的数据流向另外一个flow，是串行的关系。
        val start = System.currentTimeMillis()
        val flow1 = flow {
            delay(3000)
            emit("a")
        }
        val flow2 = flow {
            delay(2000)
            emit(1)
        }
        flow1.zip(flow2) { a, b ->
            a + b
        }.collect {
            // 并行运行
            val costTime = System.currentTimeMillis() - start
            println("zip collect = $it costTime = $costTime")
        }

        // 应用场景：我们正在开发一个天气预报应用，需要去一个接口请求当前实时的天气信息，还需要去另一个接口请求未来7天的天气信息。
        //这两个接口之间并没有先后依赖关系，但是却需要两个接口同时返回数据之后再将天气信息展示给用户。
        //如果我们先去请求当前实时的天气信息，等得到数据响应之后再去请求未来7天的天气信息，那效率就会比较低了，因为这两件事情很明显可以同时去做。
        sendRealtimeWeatherRequest().zip(sendSevenDaysWeatherRequest()) { a, b ->
                "result realtimeWeather = $a, sevenDaysWeather = $b"
            }.collect {
                println("zip weather result = $it")
            }
    }
}

fun sendRealtimeWeatherRequest(): Flow<String> = flow {
    delay(300)
    emit("realtimeWeather")
}

fun sendSevenDaysWeatherRequest(): Flow<String> = flow {
    delay(500)
    emit("sevenDaysWeather")
}