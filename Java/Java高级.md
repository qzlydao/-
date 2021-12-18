# 1. 泛型



# 2. 反射



# 3. 多线程



# 4. Lambda表达式

```java
public static void main(String[] args) {
      System.out.println(Thread.currentThread().getName());
      new Thread(new Runnable() {
        @Override
        public void run() {
          System.out.println(Thread.currentThread().getName());
          System.out.println("新线程中方法被执行了...\n");
        }
      }).start();

      System.out.println("================================");
      // 使用lambda表达式改写
      new Thread(() -> {
        System.out.println(Thread.currentThread().getName());
        System.out.println("使用lambda函数实现Runnable接口");
      }).start();
}
```





# 5.  Java8 Stream



# 6. 

```java
public static void main(String[] args) {
		Map<String, Float> map = new HashMap<>();
    map.put("1111", 0.2f);
        
    // 不会类型检查，直接返回null
    System.out.println(map.get(1111));
}
```

