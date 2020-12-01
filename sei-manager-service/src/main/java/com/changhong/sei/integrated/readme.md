# gitlab
1. createGroup 同步创建分组
2. getGroup 获取组
 
3. createProject 创建项目工程
4. pull/download 拉取模版工程
5. commit

6. create tag

7. createHooks 开发环境出发Jenkins构建

# Jenkins
1. createJob 创建任务
2. buildJob 构建任务,返回QueueId
3. getBuildNumberByQueueId 通过队列id获取构建任务号
4. getBuildLog 通过jobName和buildNumber获取构建日志
5. getBuildInfo 通过jobName和buildNumber获取构建信息