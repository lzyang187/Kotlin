package coroutine_guolin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        // onEach：查看某个中间状态时flow的数据状态
        flowOf(1, 2, 3, 4, 5).filter {
            it % 2 == 0
        }.onEach {
            println("onEach=$it")
        }.map {
            "map:$it"
        }.collect {
            println("collect=$it")
        }

        // debounce：防抖，搜索输入的联想场景
        flow {
            emit(1)
            emit(2)
            delay(600)
            emit(3)
            delay(100)
            emit(4)
        }.debounce(500).collect {
            println("debounce collect=$it")
        }

        // sample：源数据量很大，但我们又只需展示少量数据的时候比较有用，比如弹幕
        flow {
            val startTime = System.currentTimeMillis()
            while (true) {
                emit("这是弹幕")
                if (System.currentTimeMillis() - startTime > 5000) {
                    break
                }
            }
        }.sample(1000).flowOn(Dispatchers.IO).collect {
            println("sample collect=$it")
        }

        // reduce和fold操作符不需要借助collect函数，自己就能终结整个flow流程的操作符函数，这种操作符函数也被称为终端操作符函数。
        // reduce：基本公式为flow.reduce { acc, value -> acc + value }
        val reduceResult = flow {
            for (i in 1..100) {
                emit(i)
            }
        }.reduce { accumulator, value -> accumulator + value }
        println("reduce result = $reduceResult")
        // fold：和reduce函数基本上是完全类似，主要的区别在于，fold函数需要传入一个初始值，这个初始值会作为首个累积值被传递到fold的函数体当中
        // 基本公式为：flow.fold(initial) { acc, value -> acc + value }
        val foldResult = flow {
            for (i in ('A'..'Z')) {
                emit(i)
            }
        }.fold("Alphabet:") { acc, value ->
            acc + value
        }
        println("fold result = $foldResult")
    }


}