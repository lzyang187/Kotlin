//集合
fun main(args: Array<String>) {
    val list1 = mutableListOf(null, 1, 2, 3)
    //toList 扩展方法只是复制列表项，因此返回的 list 保证永远不会改变
    val snapshot = list1.toList()
    list1.add(4)
    println(snapshot)
    println(list1.filterNotNull())
    println(list1.firstOrNull())

    val list2 = listOf(1, 2, 3, 4)
    println("${list2.first()}  ${list2.last()}")
    val filter = list2.filter { it % 2 == 0 }
    println(filter)
    if (list2.none { it > 6 }) {
        println("没有大于6的元素")
    }
}

