package coroutine_guolin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    // 先创建了一个Job对象，然后把它传入CoroutineScope()函数当中
    val job = Job()
    // 注意这里的CoroutineScope()是个函数，虽然它的命名更像是一个类
    val coroutineScope = CoroutineScope(job)
    // 有了CoroutineScope对象之后，就可以随时调用它的launch函数来创建一个协程了
    coroutineScope.launch {
        println("start1")
        delay(100)
        println("finish1")
    }
    coroutineScope.launch {
        println("start2")
        delay(100)
        println("finish2")
    }
    // 所有调用CoroutineScope的launch函数所创建的协程，都会被关联在Job对象的作用域下面。
    // 这样只需要调用一次cancel()方法，就可以将同一作用域内的所有协程全部取消，从而大大降低了协程管理的成本
    job.cancel()


}