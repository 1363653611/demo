#!/usr/bin/env bash
# 显示当前 目录
owd
# 显示系统时间
date +%Y-%d-%m
#加减也可以 month | year
date +%Y-%m-%d  --date="-1 day"
## 修改时间
date -s "2016-07-28 16:12:00"


# 查看谁在线
who
# 查看最近的登陆历史记录
last

# 关机（必须用root用户）
shutdown -h now  ## 立刻关机
shutdown -h +10  ##  10分钟以后关机
shutdown -h 12:00:00  ##12点整的时候关机
halt   #  等于立刻关机

# 重启
shutdown -r now # 立即重启


#清屏
clear ## 或者用快捷键  ctrl + l

# 退出当前进程
ctrl+c # 有些进程q也可以退出

# 进程操作
ctrl+z # 挂起当前进程

bg jobid ## 让进程在后台继续执行
fg job id ## 让进程回到前台

echo # 输出： 相当于java中的System.out.println(xxx)