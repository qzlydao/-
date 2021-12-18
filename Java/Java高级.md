# 1. 泛型



# 2. 反射



# 3. 多线程




# 4. lambda函数



# 5.  Java8 Stream

`@Async`注解会在以下几个场景失效，也就是说明明使用了`@Async`注解，但就没有走多线程。

- 异步方法使用[static关键词](https://www.zhihu.com/search?q=static关键词&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"article"%2C"sourceId"%3A134636915})修饰；
- 异步类不是一个Spring容器的bean（一般使用注解`@Component`和`@Service`，并且能被Spring扫描到）；
- SpringBoot应用中没有添加`@EnableAsync`注解；
- 在同一个类中，一个方法调用另外一个有@Async注解的方法，注解不会生效。原因是@Async注解的方法，是在代理类中执行的。

需要注意的是： 异步方法使用注解@Async的返回值只能为void或者Future及其子类，当返回结果为其他类型时，方法还是会异步执行，但是返回值都是null。



# 6. 

```java
public static void main(String[] args) {
		Map<String, Float> map = new HashMap<>();
    map.put("1111", 0.2f);
        
    // 不会类型检查，直接返回null
    System.out.println(map.get(1111));
}
```

