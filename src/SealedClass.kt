/**
 * 密封类用来表示受限的类继承结构：当一个值为有限几种的类型、而不能有任何其他类型时。在某种意义上，他们是枚举类的扩展：枚举类型的值集合也是受限的，
 * 但每个枚举常量只存在一个实例，而密封类的一个子类可以有可包含状态的多个实例。
 * 虽然密封类也可以有子类，但是所有子类都必须在与密封类自身相同的文件中声明
 */
sealed class Expr

fun main() {
    println(eval(Sum(Const(2.0), NotANumber)))
}

data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()
object NotANumber : Expr()

/**
 * 使用密封类的关键好处在于使用 when 表达式 的时候，如果能够验证语句覆盖了所有情况，就不需要为该语句再添加一个 else 子句
 */
fun eval(expr: Expr): Double = when (expr) {
    is Const -> expr.number
    is Sum -> eval(expr.e1) + eval(expr.e2)
    is NotANumber -> Double.NaN
}