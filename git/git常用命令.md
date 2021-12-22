## 1. 打tag



## 2. 远程分支覆盖本地分支

~~~shell
git fetch --all  
git reset --hard origin/master 
git pull
~~~

## 3. 删除本地和远程分支

```shell
# 1. 查看项目分支（本地和远程）
(base) localhost:11_常用技术 liuqiang$ git branch -a
* main
  master
  remotes/origin/main
  
# 2. 删除本地分支
(base) localhost:11_常用技术 liuqiang$ git branch -D master
Deleted branch master (was b515e4e).

# 3. 删除远程分支
(base) localhost:11_常用技术 liuqiang$ git push origin --delete master
To github.com:qzlydao/Common-Techniques.git
 - [deleted]         master

# 4. 再次查看项目分支
(base) localhost:11_常用技术 liuqiang$ git branch -a
* main
  remotes/origin/main
```

