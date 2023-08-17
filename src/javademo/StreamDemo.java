package javademo;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        Stream<String> stream = strings.stream().filter(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.isEmpty();
            }
        });
        long count = stream.count();
        System.out.println("去除空字符串后的数量：" + count);

        List<String> strings1 = strings.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());
        System.out.println("去除空字符串后的列表：" + strings1);

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        Set<String> collect = numbers.stream().map(integer -> integer + "s").collect(Collectors.toSet());
        System.out.println("转换为字符串后返回集合：" + collect);

        // 去重
        List<Integer> collect1 = numbers.stream().distinct().collect(Collectors.toList());
        System.out.println("去重：" + collect1);

        // limit方法会对流进行顺序截取，从第1个元素开始，保留最多maxSize个元素
        List<Integer> collect2 = numbers.stream().limit(3).collect(Collectors.toList());
        System.out.println("截取前三个：" + collect2);

        List<Integer> integers = Arrays.asList(1, 2, 13, 4, 15, 6, 17, 8, 19);
        IntSummaryStatistics stats = integers.stream().mapToInt(new ToIntFunction<Integer>() {
            @Override
            public int applyAsInt(Integer value) {
                return value;
            }
        }).summaryStatistics();
        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());

    }
}
