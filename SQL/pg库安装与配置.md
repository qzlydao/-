

# 10. Mac操作pg库

## 10.1 安装

```shell
(base) localhost:var liuqiang$ brew search postgres
==> Formulae
check_postgres   postgresql       postgresql@10    postgresql@11    postgresql@12    postgresql@13    postgresql@9.4   postgresql@9.5   postgresql@9.6   postgrest        qt-postgresql    postgis
==> Casks
navicat-for-postgresql                             postgres-unofficial                                postgrespreferencepane                             sqlpro-for-postgres
```

- Formulae：意思是一些软件包，一般是命令行工具、开发库、一些字体、插件，共性是不提供界面，提供给终端或者是开发者使用。
- Casks：是用户软件，比如 chrome、mvim、wechat、wechatwork 这些提供用户交互界面的软件。

```shell
(base) localhost:var liuqiang$ brew install postgresql@9.6

# 安装好后的提示信息
postgresql@9.6 is keg-only, which means it was not symlinked into /usr/local,
because this is an alternate version of another formula.

If you need to have postgresql@9.6 first in your PATH, run:
  echo 'export PATH="/usr/local/opt/postgresql@9.6/bin:$PATH"' >> /Users/liuqiang/.bash_profile

For compilers to find postgresql@9.6 you may need to set:
  export LDFLAGS="-L/usr/local/opt/postgresql@9.6/lib"
  export CPPFLAGS="-I/usr/local/opt/postgresql@9.6/include"

For pkg-config to find postgresql@9.6 you may need to set:
  export PKG_CONFIG_PATH="/usr/local/opt/postgresql@9.6/lib/pkgconfig"


To restart postgresql@9.6 after an upgrade:
  brew services restart postgresql@9.6
Or, if you don't want/need a background service you can just run:
  /usr/local/opt/postgresql@9.6/bin/postgres -D /usr/local/var/postgresql@9.6
```

值得一提的是，通过homebrew安装的程序，都会存储在/usr/local/Cellar目录下面，并将对应的应用软链接到/usr/local/opt目录下；应用的配置和存储数据会放在/usr/local/var下的对应目录下（==配置和数据不会随着卸载命令而删除==）

## 10.2 启动

```shell
# 第一种启动方式
(base) localhost:~ liuqiang$ brew services restart postgresql@9.6
==> Successfully ran `postgresql@9.6` (label: homebrew.mxcl.postgresql@9.6)
(base) localhost:~ liuqiang$ brew services  list
Name           Status  User     File
postgresql@9.6 started liuqiang 
redis@4.0      stopped  

# 第二种启动方式
(base) localhost:~ liuqiang$ pg_ctl -D /usr/local/var/postgresql@9.6 start
```

## 10.3 初始化

```sql
-- 这一步在安装时已经初始化了
(base) localhost:~ liuqiang$ initdb /usr/local/var/postgresql\@9.6/
```

## 10.4 创建数据库并登录

Mac 安装 PostgreSQL 后不会创建用户名数据库

```sql
(base) localhost:~ liuqiang$ createdb

# psql连接数据库默认选用的是当前的系统用户
(base) localhost:~ liuqiang$ psql
psql (9.6.24)
Type "help" for help.

liuqiang=# 
```

关于`psql`

```shell
(base) localhost:~ liuqiang$ psql --help
psql is the PostgreSQL interactive terminal.

Usage:
  psql [OPTION]... [DBNAME [USERNAME]]

General options:
  -c, --command=COMMAND    run only single command (SQL or internal) and exit
  -d, --dbname=DBNAME      database name to connect to (default: "liuqiang")
  -f, --file=FILENAME      execute commands from file, then exit
  -l, --list               list available databases, then exit
  -v, --set=, --variable=NAME=VALUE
                           set psql variable NAME to VALUE
                           (e.g., -v ON_ERROR_STOP=1)
  -V, --version            output version information, then exit
  -X, --no-psqlrc          do not read startup file (~/.psqlrc)
  -1 ("one"), --single-transaction
                           execute as a single transaction (if non-interactive)
  -?, --help[=options]     show this help, then exit
      --help=commands      list backslash commands, then exit
      --help=variables     list special variables, then exit

Input and output options:
  -a, --echo-all           echo all input from script
  -b, --echo-errors        echo failed commands
  -e, --echo-queries       echo commands sent to server
  -E, --echo-hidden        display queries that internal commands generate
  -L, --log-file=FILENAME  send session log to file
  -n, --no-readline        disable enhanced command line editing (readline)
  -o, --output=FILENAME    send query results to file (or |pipe)
  -q, --quiet              run quietly (no messages, only query output)
  -s, --single-step        single-step mode (confirm each query)
  -S, --single-line        single-line mode (end of line terminates SQL command)

Output format options:
  -A, --no-align           unaligned table output mode
  -F, --field-separator=STRING
                           field separator for unaligned output (default: "|")
  -H, --html               HTML table output mode
  -P, --pset=VAR[=ARG]     set printing option VAR to ARG (see \pset command)
  -R, --record-separator=STRING
                           record separator for unaligned output (default: newline)
  -t, --tuples-only        print rows only
  -T, --table-attr=TEXT    set HTML table tag attributes (e.g., width, border)
  -x, --expanded           turn on expanded table output
  -z, --field-separator-zero
                           set field separator for unaligned output to zero byte
  -0, --record-separator-zero
                           set record separator for unaligned output to zero byte

Connection options:
  -h, --host=HOSTNAME      database server host or socket directory (default: "local socket")
  -p, --port=PORT          database server port (default: "5432")
  -U, --username=USERNAME  database user name (default: "liuqiang")
  -w, --no-password        never prompt for password
  -W, --password           force password prompt (should happen automatically)

For more information, type "\?" (for internal commands) or "\help" (for SQL
commands) from within psql, or consult the psql section in the PostgreSQL
documentation.

Report bugs to <pgsql-bugs@postgresql.org>.
```

**常用命令：**

1. 查看可用数据库列表

   ```shell
   (base) localhost:~ liuqiang$ psql -l
                                     List of databases
      Name    |  Owner   | Encoding |   Collate   |    Ctype    |   Access privileges   
   -----------+----------+----------+-------------+-------------+-----------------------
    liuqiang  | liuqiang | UTF8     | en_US.UTF-8 | en_US.UTF-8 | 
    postgres  | postgres | UTF8     | en_US.UTF-8 | en_US.UTF-8 | =Tc/postgres         +
              |          |          |             |             | postgres=CTc/postgres
    template0 | liuqiang | UTF8     | en_US.UTF-8 | en_US.UTF-8 | =c/liuqiang          +
              |          |          |             |             | liuqiang=CTc/liuqiang
    template1 | liuqiang | UTF8     | en_US.UTF-8 | en_US.UTF-8 | =c/liuqiang          +
              |          |          |             |             | liuqiang=CTc/liuqiang
   (4 rows)
   ```

2. 查看版本信息

   ```shell
   (base) localhost:~ liuqiang$ psql -V
   psql (PostgreSQL) 9.6.24
   ```

## 10.5 创建postgres用户

```sql
liuqiang=# create user postgres  with password '518518';
CREATE ROLE
```

## 10.6 删除默认生成的postgres数据库

```sql
liuqiang=# drop database postgres;
DROP DATABASE
```

## 10.7 创建属于postgres用户的postgres数据库

```sql
liuqiang=# CREATE DATABASE postgres OWNER postgres;
CREATE DATABASE
```

## 10.8 将数据库所有权限赋予postgres用户

```sql
liuqiang=# GRANT ALL PRIVILEGES ON DATABASE postgres to postgres;
GRANT
```

## 10.9 给postgres用户添加创建数据库的属性

```sql
liuqiang=# ALTER ROLE postgres CREATEDB;
ALTER ROLE
```

这样可以使用postgres作为数据库的登录用户了，并可以使用该用户管理数据库了。

## 10.10 登录控制台指令

```shell
psql -U [user] -d [database] -h [host] -p [port]
```

-U 指定用户，-d 指定数据库，-h 指定服务器， -p 指定端口

比如，使用postgres用户登录

```sql
(base) localhost:~ liuqiang$ psql -U postgres -d postgres
psql (9.6.24)
Type "help" for help.

postgres=> 
```

**控制台指令：**

```shell
General
  \copyright             show PostgreSQL usage and distribution terms
  \errverbose            show most recent error message at maximum verbosity
  \g [FILE] or ;         execute query (and send results to file or |pipe)
  \gexec                 execute query, then execute each value in its result
  \gset [PREFIX]         execute query and store results in psql variables
  \q                     quit psql
  \crosstabview [COLUMNS] execute query and display results in crosstab
  \watch [SEC]           execute query every SEC seconds

Help
  \? [commands]          show help on backslash commands
  \? options             show help on psql command-line options
  \? variables           show help on special variables
  \h [NAME]              help on syntax of SQL commands, * for all commands

Query Buffer
  \e [FILE] [LINE]       edit the query buffer (or file) with external editor
  \ef [FUNCNAME [LINE]]  edit function definition with external editor
  \ev [VIEWNAME [LINE]]  edit view definition with external editor
  \p                     show the contents of the query buffer
  \r                     reset (clear) the query buffer
  \s [FILE]              display history or save it to file
  \w FILE                write query buffer to file

Input/Output
  \copy ...              perform SQL COPY with data stream to the client host
  \echo [STRING]         write string to standard output
  \i FILE                execute commands from file
  \ir FILE               as \i, but relative to location of current script
  \o [FILE]              send all query results to file or |pipe
  \qecho [STRING]        write string to query output stream (see \o)

Informational
  (options: S = show system objects, + = additional detail)
  \d[S+]                 list tables, views, and sequences
  \d[S+]  NAME           describe table, view, sequence, or index
  \da[S]  [PATTERN]      list aggregates
  \dA[+]  [PATTERN]      list access methods
  \db[+]  [PATTERN]      list tablespaces
  \dc[S+] [PATTERN]      list conversions
  \dC[+]  [PATTERN]      list casts
  \dd[S]  [PATTERN]      show object descriptions not displayed elsewhere
  \ddp    [PATTERN]      list default privileges
  \dD[S+] [PATTERN]      list domains
  \det[+] [PATTERN]      list foreign tables
  \des[+] [PATTERN]      list foreign servers
  \deu[+] [PATTERN]      list user mappings
  \dew[+] [PATTERN]      list foreign-data wrappers
  \df[antw][S+] [PATRN]  list [only agg/normal/trigger/window] functions
  \dF[+]  [PATTERN]      list text search configurations
  \dFd[+] [PATTERN]      list text search dictionaries
  \dFp[+] [PATTERN]      list text search parsers
  \dFt[+] [PATTERN]      list text search templates
  \dg[S+] [PATTERN]      list roles
  \di[S+] [PATTERN]      list indexes
  \dl                    list large objects, same as \lo_list
  \dL[S+] [PATTERN]      list procedural languages
  \dm[S+] [PATTERN]      list materialized views
  \dn[S+] [PATTERN]      list schemas
  \do[S+] [PATTERN]      list operators
  \dO[S+] [PATTERN]      list collations
  \dp     [PATTERN]      list table, view, and sequence access privileges
  \drds [PATRN1 [PATRN2]] list per-database role settings
  \ds[S+] [PATTERN]      list sequences
  \dt[S+] [PATTERN]      list tables
  \dT[S+] [PATTERN]      list data types
  \du[S+] [PATTERN]      list roles
  \dv[S+] [PATTERN]      list views
  \dE[S+] [PATTERN]      list foreign tables
  \dx[+]  [PATTERN]      list extensions
  \dy[+]  [PATTERN]      list event triggers
  \l[+]   [PATTERN]      list databases
  \sf[+]  FUNCNAME       show a function's definition
  \sv[+]  VIEWNAME       show a view's definition
  \z      [PATTERN]      same as \dp

Formatting
  \a                     toggle between unaligned and aligned output mode
  \C [STRING]            set table title, or unset if none
  \f [STRING]            show or set field separator for unaligned query output
  \H                     toggle HTML output mode (currently off)
  \pset [NAME [VALUE]]   set table output option
                         (NAME := {border|columns|expanded|fieldsep|fieldsep_zero|
                         footer|format|linestyle|null|numericlocale|pager|
                         pager_min_lines|recordsep|recordsep_zero|tableattr|title|
                         tuples_only|unicode_border_linestyle|
                         unicode_column_linestyle|unicode_header_linestyle})
  \t [on|off]            show only rows (currently off)
  \T [STRING]            set HTML <table> tag attributes, or unset if none
  \x [on|off|auto]       toggle expanded output (currently off)

Connection
  \c[onnect] {[DBNAME|- USER|- HOST|- PORT|-] | conninfo}
                         connect to new database (currently "liuqiang")
  \encoding [ENCODING]   show or set client encoding
  \password [USERNAME]   securely change the password for a user
  \conninfo              display information about current connection

Operating System
  \cd [DIR]              change the current working directory
  \setenv NAME [VALUE]   set or unset environment variable
  \timing [on|off]       toggle timing of commands (currently off)
  \! [COMMAND]           execute command in shell or start interactive shell

Variables
  \prompt [TEXT] NAME    prompt user to set internal variable
  \set [NAME [VALUE]]    set internal variable, or list all if no parameters
  \unset NAME            unset (delete) internal variable

Large Objects
  \lo_export LOBOID FILE
  \lo_import FILE [COMMENT]
  \lo_list
  \lo_unlink LOBOID      large object operations
```

## 10.11 关闭

```shell
(base) localhost:~ liuqiang$ brew services stop postgresql@9.6

(base) localhost:~ liuqiang$ pg_ctl -D /usr/local/var/postgresql@9.6 stop
```

## 10.12 卸载

```shell
(base) localhost:~ liuqiang$ brew uninstall postgresql@9.6
```

卸载命令执行完后，发现/usr/local/Cellar/postgresql@9.6目录已经完全不存在了，/usr/local/opt/postgresql@9.6目录也不存在了（因为软链接的源文件也不存在了），==但是/usr/local/var/postgresql@9.6目录还存在（配置和数据文件，如果想完全删除需要手动删除该目录）==

## 10.13 登录

```sql
#psql -U 用户名 -d 数据库名
#默认的用户和数据库是postgres
psql -U postgres -d postgres
psql -U qzlydao -d dbdemo
```



