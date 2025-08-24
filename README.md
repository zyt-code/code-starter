# Code Starter 项目文档

## 项目介绍

Code Starter 是一个基于 Spring Boot 3.1.5 的后端服务基础框架，旨在帮助开发者快速搭建和开发后端应用程序。该项目提供了一套完整的基础设施，包括统一的响应格式、基础实体类、数据访问层、服务层等通用组件，可以大大减少重复开发工作。

## 技术栈

- **核心框架**: Spring Boot 3.1.5
- **编程语言**: Java 17
- **构建工具**: Maven 3.x
- **数据库**: MySQL 8.0.33 (生产环境) / H2 (测试环境)
- **ORM 框架**: JPA/Hibernate
- **API 文档**: SpringDoc OpenAPI
- **测试工具**: JUnit 5, TestContainers
- **代码质量**: Jacoco (测试覆盖率检查)
- **其他**: Lombok (简化代码), Jackson (JSON 处理)

## 环境要求

- JDK 17 或更高版本
- Maven 3.x 或更高版本
- MySQL 8.0.33 (可选，用于生产环境)
- IDE 支持 (推荐 IntelliJ IDEA 或 VS Code，并安装 Lombok 插件)

## 项目结构

```
src
├── main
│   ├── java
│   │   └── com.example.codestarter
│   │       ├── common          // 通用组件
│   │       ├── config          // 配置类
│   │       ├── entity          // 实体类
│   │       ├── repository      // 数据访问层
│   │       ├── service         // 服务层
│   │       └── CodeStarterApplication.java  // 启动类
│   └── resources
│       └── application.yml     // 配置文件
└── test                        // 测试代码
```

## 核心组件

### 1. 统一响应格式 (Result)

项目提供了统一的 API 响应格式 [Result](src/main/java/com/example/codestarter/common/Result.java)，包含状态码、消息和数据三个部分，支持多种响应类型：

- `success()`: 成功响应
- `error()`: 错误响应
- `badRequest()`: 参数错误响应
- `unauthorized()`: 未授权响应
- `forbidden()`: 禁止访问响应
- `notFound()`: 资源未找到响应

### 2. 基础实体类 (BaseEntity)

[BaseEntity](src/main/java/com/example/codestarter/entity/BaseEntity.java) 提供了所有实体类的基类，包含以下通用字段：

- `id`: 主键ID，自增
- `createTime`: 创建时间，自动填充
- `updateTime`: 更新时间，自动填充
- `deleted`: 逻辑删除标志
- `createBy`: 创建者ID
- `updateBy`: 更新者ID
- `version`: 版本号，用于乐观锁

### 3. 基础数据访问层 (BaseRepository)

[BaseRepository](src/main/java/com/example/codestarter/repository/BaseRepository.java) 扩展了 JpaRepository，提供了逻辑删除支持的通用数据访问方法：

- `findAllByDeletedOrderByCreateTimeDesc()`: 查询未删除的所有记录
- `findAllByDeletedFalse()`: 分页查询未删除记录
- `findByIdAndDeleted()`: 根据ID查询未删除记录
- `logicalDeleteById()`: 逻辑删除指定记录
- `logicalDeleteByIds()`: 批量逻辑删除记录

### 4. 基础服务层 (BaseService & BaseServiceImpl)

[BaseService](src/main/java/com/example/codestarter/service/BaseService.java) 定义了通用的业务操作接口，[BaseServiceImpl](src/main/java/com/example/codestarter/service/impl/BaseServiceImpl.java) 提供了默认实现：

- 增删改查操作
- 物理删除和逻辑删除支持
- 分页查询支持
- 存在性检查和计数功能

### 5. 配置类

- [CorsConfig](src/main/java/com/example/codestarter/config/CorsConfig.java): CORS 跨域配置
- [JpaAuditingConfig](src/main/java/com/example/codestarter/config/JpaAuditingConfig.java): JPA 审计配置，支持自动填充创建和更新时间
- [SpringDocConfig](src/main/java/com/example/codestarter/config/SpringDocConfig.java): SpringDoc OpenAPI 配置

## 运行项目

### 1. 环境准备

确保已安装 JDK 17 和 Maven 3.x。

### 2. 数据库配置

在 `src/main/resources/application.yml` 中配置数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/code_starter?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true
    username: root
    password: root
```

### 3. 构建项目

```bash
mvn clean install
```

### 4. 运行项目

```bash
mvn spring-boot:run
```

或者

```bash
mvn clean package
java -jar target/code-starter-1.0.0.jar
```

项目启动后可通过 `http://localhost:8080/api` 访问。

## 测试

项目支持多种测试模式：

### 快速测试 (仅单元测试)

```bash
mvn test -Pquick-test
```

### 标准测试 (单元测试 + 服务测试)

```bash
mvn test -Pstandard-test
```

### 完整测试 (所有测试)

```bash
mvn verify -Pfull-test
```

项目使用 Jacoco 进行测试覆盖率检查，默认要求达到 90% 的行覆盖率。

## API 文档

项目集成了 SpringDoc OpenAPI，启动后可通过以下地址访问 API 文档：

- Swagger UI: `http://localhost:8080/api/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api/v3/api-docs`

## API 文档

项目集成了 SpringDoc OpenAPI，可以自动生成 RESTful API 文档。

### 访问方式

项目启动后可通过以下地址访问 API 文档：

- Swagger UI: `http://localhost:8080/api/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api/v3/api-docs`

### 使用方法

1. 在 Controller 类上添加 `@Tag` 注解描述接口分类
2. 在方法上添加 `@Operation` 注解描述接口功能
3. 在参数上添加 `@Parameter` 注解描述参数信息

示例：
```java
@Tag(name = "示例接口", description = "用于演示 SpringDoc API 文档功能的示例接口")
@RestController
@RequestMapping("/hello")
public class HelloController {
    
    @Operation(summary = "问候接口", description = "根据用户名返回问候语")
    @GetMapping
    public Result<String> hello(
            @Parameter(description = "用户名") 
            @RequestParam(defaultValue = "World") String name) {
        return Result.success("Hello, " + name + "!");
    }
}
```

## 部署

使用以下命令打包应用：

```bash
mvn clean package
```

生成的 jar 文件位于 `target/code-starter-1.0.0.jar`，可通过以下命令运行：

```bash
java -jar target/code-starter-1.0.0.jar
```

## 开发指南

### 创建新实体

1. 创建实体类，继承 [BaseEntity](src/main/java/com/example/codestarter/entity/BaseEntity.java)
2. 添加实体特定字段和业务逻辑
3. 创建对应的 Repository 接口，继承 [BaseRepository](src/main/java/com/example/codestarter/repository/BaseRepository.java)
4. 创建 Service 接口，继承 [BaseService](src/main/java/com/example/codestarter/service/BaseService.java)
5. 创建 Service 实现类，继承 [BaseServiceImpl](src/main/java/com/example/codestarter/service/impl/BaseServiceImpl.java)

### 添加新的 API 接口

1. 创建 Controller 类
2. 注入对应的 Service
3. 实现 RESTful API 接口
4. 使用 [Result](src/main/java/com/example/codestarter/common/Result.java) 作为统一返回格式
5. 添加 SpringDoc 注解生成 API 文档

## 贡献

欢迎提交 Issue 和 Pull Request 来改进这个项目。