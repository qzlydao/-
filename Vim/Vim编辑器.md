- 命令模式
- 编辑模式



语法高亮：

```shell
:syntax on # 只对当前打开的文档有效
```

显示行号

```shell
:set number # 只对当前打开的文档有效
```

对vim进行配置

```shell
vim ~/.vimrc

# 配置语法高亮
syntax on
# 配置显示行号
set number
```

移动光标

```shell
# 命令模式下
h: 向左
j: 向下
k: 向上
l: 向右
```

根据单词跳动

```shell
# 在命令模式下

w(word): 将光标从当前单词切换到下一个单词
b(back): 往前跳
```

翻页

```shell
ctrl + f # 向下翻页
ctrl + b # 向上翻页
```

快速定位

```shell
# 根据行号快速定位

row_num 然后连按两次gg
```

上下跳转指定行

```shell
row_num + k: 向上跳row_num行
row_num + j: 向下跳row_num行
```

查找

```shell
# 命令模式下

/target_word : # 查找target_word
n: # 跳到下一个target_word
shift + n: # 跳到上一个target_word
```

撤销操作

```shell
# 命令模式下
u
```

连续删除多行

```shell
ndd: # 删除连续的n行
```

剪切

```shell
cc: # 剪切一行
cnc: # 剪切n行
```

复制

```
yy
```



粘贴

```shell
p
```

visual模式

```shell
# 类似用光标选择

# 命令模式下按 v 进入visual模式
```





