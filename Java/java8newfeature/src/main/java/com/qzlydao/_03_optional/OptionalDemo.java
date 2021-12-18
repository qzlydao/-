package com.qzlydao._03_optional;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: liuqiang
 * Date: 2021-12-18 下午9:21
 */
public class OptionalDemo {

    public static void main(String[] args) {
        Optional<Author> author = getAuthor();

        // 避免了对author的null判断
        author.ifPresent(author1 -> System.out.println(author1.getName()));

        // Filter
        author.filter(author1 -> author1.getAge() > 50).ifPresent(author2 -> System.out.println(author2.getName()));
    }

    public static Optional<Author> getAuthor() {
        Author author = new Author(1L, "马克思", 43, "哲学,社会学", null);
        return Optional.ofNullable(author);
    }

}
