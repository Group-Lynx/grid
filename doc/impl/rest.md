# Restful API

## 服务器认证

认证统一通过 API Key 完成，该 key 应当事先约定好。具体实现如下:

- 认证方式：通过查询参数提供：`/base/uri?apiKey=xxxxxxxxxxxx`

## 端口

### 服务器

| 实现 | 方法  | 端口      | 荷载 | 期待  | 描述           |
| ---- | ----- | --------- | ---- | ----- | -------------- |
| 🗹    | `GET` | `/health` |      | `204` | 检查服务器健康 |



### 学生用户

| 实现 | 方法     | 端口                          | 荷载                    | 期待                           | 描述             |
| ---- | -------- | ----------------------------- | ----------------------- | ------------------------------ | ---------------- |
|      | `POST`   | `/student/login`              | `{studentId, password}` | `201`                          | 学生登录         |
|      | `POST`   | `/student/signup`             | `{studentId, password}` | `201`                          | 学生注册         |
|      | `GET`    | `/student/<studentId>`        |                         | `200 {studentId, studentName}` | 学生个人信息     |
|      | `GET`    | `/student/<studentId>/class`  |                         | `200 [{classId, className}]`   | 学生加入班级信息 |
|      | `DELETE` | `/student/<studentId>/delete` |                         | `204`                          | 删除账户         |
|      |          |                               |                         |                                |                  |

### 学生待办

| 实现 | 方法   | 端口 | 荷载      | 期待 | 描述 |
| ---- | ------ | ---- | --------- | ---- | ---- |
|      | `GET` | `/student/<studentId>/todo` |                                            | `200 [{todoId, title, due, detail, done, display}]` | 获取待办 |
|      | `POST` | `/student/<studentId>/todo` | `{title, due, detail}` | `201` |创建待办|
|      | `PATCH` | `/student/<studentId>/todo/<todoId>` | `{title?, due?, detail?, done?, display?}` | `204` | 更新待办属性 |
|      | `DELETE` | `/student/<studentId>/todo/<todoId>` |  | `204` | 删除待办 |
|      |         |                                |                                            |  |                  |

### 学生事件

| 实现 | 方法   | 端口 | 荷载      | 期待 | 描述 |
| ---- | ------ | ---- | --------- | ---- | ---- |
|      | `GET` | `/student/<studentId>/event` |      | `200 [{eventId, name, location, start, end}]` | 获取事件 |
|      | `POST`      | `/student/<studentId>/event` | `{name, location, start, end}` | `201` |创建事件|
|  | `PATCH` | `/student/<studentId>/event/<eventId>` | `{name?, location?, start?, end?}` | `204` |更新事件待办事项属性|
|      | `DELETE` | `/student/<studentId>/event/<eventId>` |  | `204` | 删除事件 |
|      |        |                    |      |  |      |

### 学生通知


| 实现 | 方法   | 端口 | 荷载      | 期待 | 描述 |
| ---- | ------ | ---- | --------- | ---- | ---- |
|      | `GET` | `/student/<uid>/mail` |      | `200 [{mailId, title}]` | 获取所有通知 |
|      | `GET` | `/student/<uid>/mail/<mailId>` |  | `200 {mailId, title, detail}` | 获取通知详情 |
|      | `PUT`  | `/student/<uid>/mail/<mailId>` | `{read}` | `204` | 变更待办已读状态 |
|      |      |   |           |  |      |


### 老师用户

| 实现 | 方法     | 端口                          | 荷载                    | 期待                           | 描述             |
| ---- | -------- | ----------------------------- | ----------------------- | ------------------------------ | ---------------- |
|      | `POST`   | `/teacher/login`              | `{teacherId, password}` | `201`                          | 老师登录         |
|      | `POST`   | `/teacher/signup`             | `{teacherId, password}` | `201`                          | 老师注册         |
|      | `GET`    | `/teacher/<teacherId>`        |                         | `200 {studentId, studentName}` | 老师个人信息     |
|      | `GET`    | `/teacher/<teacherId>/class`  |                         | `200 [{classId, className}]`   | 老师管理班级信息 |
|      | `DELETE` | `/teacher/<teacherId>/delete` |                         | `204`                          | 删除账户         |
|      |          |                               |                         |                                |                  |

### 班级通知

| 实现 | 方法   | 端口              | 荷载                    | 期待  | 描述     |
| ---- | ------ | ----------------- | ----------------------- | ----- | -------- |
|      | `GET` | `/class/<classId>/mail` |  | `201 [{mailId, title}]` | 获取班级所有通知 |
|      | `POST` | `/class/<classId>/mail` | `{title, detail}` | `201 {mailId}` | 创建新通知草稿 |
|      | `GET` | `/class/<classId>/mail/<mailId>` |                         | `200 {title, detail, isDraft}` | 获取通知详情 |
|      | `PUT` | `/class/<classId>/mail/<mailId>` | `{title, detail}` | `204` | 更新通知内容 |
| | `POST` | `/class/<classId>/mail/<mailId>` |  | `204` | 发布通知 |
| | | | | | |


### 班级管理

| 实现 | 方法   | 端口           | 荷载                     | 期待            | 描述         |
| ---- | ------ | -------------- | ------------------------ | --------------- | ------------ |
|      | `POST` | `/class`       | `{className, teacherId}` | `201 {classId}` | 创建班级     |
|      | `POST` | `/class/join`  | `{classId, studentId}`   | `204`           | 学生加入班级 |
|      | `POST` | `/class/leave` | `{classId, studentId}`   | `204`           | 学生离开班级 |
|      |        |                |                          |                 |              |
