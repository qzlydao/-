package com.qzlydao._02_stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: liuqiang
 * Date: 2021-12-18 下午5:17
 */
public class StreamDemo {

    public static void main(String[] args) {
        List<Author> authors = getAuthors();

        // 1. 打印所有年龄小于18的作家名字，并且要注意去重
        authors.stream().distinct().filter(e -> e.getAge() < 18).forEach(e -> System.out.println(e.getName()));

        System.out.println("==================================");

        // 2. 创建流
        test01();

        System.out.println("==================================");

        // 3. map操作
        test02();

        System.out.println("==================================");

        // 4. 排序操作
        test03();

        System.out.println("==================================");

        // 5. limit 限制stream流中元素的个数
        test04();

        System.out.println("==================================");

        // 6. flatMap
        test05();
        test06();

        System.out.println("==================================");

        // 7. collect()成一个map
        test07();

        System.out.println("==================================");

        // 8. reduce操作之求作者年龄和
        test08();
    }

    public static void test01() {
        Integer[] arr = new Integer[]{1, 2, 3, 4};
        // 将数组对象转换为stream
        Arrays.stream(arr).forEach(e -> System.out.println(e));
    }

    private static void test02() {

        List<Author> authors = getAuthors();

        // map 将stream中的对象转换为String
        authors.stream().map(e -> e.getName()).forEach(e -> System.out.println(e));
    }

    private static void test03() {
        // 需求: 按作者年龄降序排列，并去重
        List<Author> authors = getAuthors();

        authors.stream().distinct().sorted((a1, a2) -> a2.getAge() - a1.getAge()).forEach(e -> System.out.println(e.getAge()));
    }

    private static void test04() {
        // 需求：按作者年龄降序排列，并去重，打印前2为作者
        List<Author> authors = getAuthors();

        authors.stream().distinct().sorted((a1, a2) -> a2.getAge() - a1.getAge()).limit(2).forEach(e -> System.out.println(e.getName() + ":" + e.getAge()));
    }

    private static void test05() {
        // 需求：打印所有作者的著作，并去重
        List<Author> authors = getAuthors();

        authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .forEach(book -> System.out.println(book.getName()));
    }

    private static void test06() {
        // 需求: 打印所有作者的书籍分类，去重
        List<Author> authors = getAuthors();

        authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .flatMap(book -> Arrays.stream(book.getCategory().split(",")))
                .distinct()
                .forEach(e -> System.out.println(e));
    }

    private static void test07() {
        List<Author> authors = getAuthors();

        // key: 作者名   value: 作者书籍
        Map<String, List<Book>> bookMap = authors.stream()
                .distinct()
                .collect(Collectors.toMap(author -> author.getName(), author -> author.getBooks()));

        // 遍历打印
        bookMap.entrySet().stream().forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }

    private static void test08() {
        List<Author> authors = getAuthors();

        // 获取所有作者年龄和
        Integer ageSum = authors.stream()
                .distinct()
                .map(author -> author.getAge())
                .reduce(0, (sum, ele) -> sum + ele);
        System.out.println(ageSum);

        // 求作者中年龄最大
        Integer max = authors.stream()
                .distinct()
                .map(author -> author.getAge())
                .reduce(Integer.MIN_VALUE, (result, ele) -> result < ele ? ele : result);
        System.out.println(max);

        // 求年龄最小值，使用一个参数的重载方法
        Optional<Integer> reduce = authors.stream()
                .distinct()
                .map(author -> author.getAge())
                .reduce((result, ele) -> result > ele ? ele : result);
        reduce.ifPresent(age -> System.out.println(age));

    }

    public static List<Author> getAuthors() {
        Author author1 = new Author(1L, "蒙多", 33, "一个从菜刀中明悟哲理的祖安人", null);
        Author author2 = new Author(2L, "亚拉索", 15, "狂风也追逐不上他的思考速度", null);
        Author author3 = new Author(3L, "易", 14, "是这个世界在限制他的思维", null);
        Author author4 = new Author(3L, "易", 14, "是这个世界在限制他的思维", null);

        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();
        List<Book> books3 = new ArrayList<>();

        books1.add(new Book(1L, "刀的两侧是光明与黑暗", "哲学,爱情", 88, "用一把刀划分了爱恨"));
        books1.add(new Book(2L, "一个人不能死在同一把刀下", "个人成长,爱情", 99, "讲述如何从失败中明悟真理"));

        books2.add(new Book(3L, "那风吹不到的地方", "哲学", 85, "带你用思维去领略世界的尽头"));
        books2.add(new Book(3L, "那风吹不到的地方", "哲学", 85, "带你用思维去领略世界的尽头"));
        books2.add(new Book(4L, "吹或不吹", "爱情,个人传记", 56, "一个哲学家的恋爱观注定很难被他所在的时代理解"));

        books3.add(new Book(5L, "你的剑就是我的剑", "爱情", 56, "无法想象一个武者能对他的伴侣这么的宽容"));
        books3.add(new Book(6L, "风与剑", "个人传记", 100, "两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));
        books3.add(new Book(6L, "风与剑", "个人传记", 100, "两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));

        author1.setBooks(books1);
        author2.setBooks(books2);
        author3.setBooks(books3);
        author4.setBooks(books3);

        return new ArrayList<>(Arrays.asList(author1, author2, author3, author4));

    }

}
