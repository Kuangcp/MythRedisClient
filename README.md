### 步骤
-   新建一个gradle 空Module(不勾选java或者其他类型)，这就是整个大项目
-   选中刚新建的Module 然后新建Module 只选java 就是se核心复用部分
-   选中最下的依赖库（External Libraries），然后新建Springboot 的Module，选择Gradle Project 不是Gradle config，不然没有初始代码和完整目录，选择依赖之后建立成功后（后台不再有任务运行）
-   直接将Springboot模块拖到大项目下，就成为了大项目的子文件夹
-   修改大项目下的 setting.gradle 文件，添加 include 'Springboot模块的名字 ' 即可
-   然后在右边 gradle工具条中删除 和大项目同级的那个Springboot （前提是你发现和se项目同级出现了Springboot项目名） 弹窗提示然后去掉main和test的勾，就不会删除对应包
- 修改gradle构建文件： 

###  整个项目的`build.gradle 文件`：
```
allprojects {
    apply plugin: 'java'
    group 'com.github.kuangcp'
    version = '1.0'
    sourceCompatibility = 1.8
    targetCompatibility = 1.8
}
subprojects {
    ext {
//        slf4jVersion = '1.7.7'
        springVersion = '4.3.8.RELEASE'
        hibernateVersion = '4.3.1.Final'
    }
    [compileJava, compileTestJava, javadoc]*.options*.encoding = 'UTF-8'
    repositories {
        mavenCentral()
    }
    configurations {
        //compile.exclude module: 'commons-logging'
        all*.exclude module: 'commons-logging'
    }
    dependencies {
        compile(
                 'redis.clients:jedis:2.9.0',
                 'org.slf4j:slf4j-api:1.7.25',
                 'ch.qos.logback:logback-core:1.1.11',
                 'ch.qos.logback:logback-classic:1.1.11',

//                "org.slf4j:jcl-over-slf4j:${slf4jVersion}",
//                "org.slf4j:slf4j-log4j12:${slf4jVersion}",
                "org.springframework:spring-context:$springVersion",
//                "org.springframework:spring-orm:$springVersion",
//                "org.springframework:spring-tx:$springVersion",
//                "org.springframework.data:spring-data-jpa:1.5.2.RELEASE",
//                "org.hibernate:hibernate-entitymanager:$hibernateVersion",
//                "c3p0:c3p0:0.9.1.2",
                "mysql:mysql-connector-java:5.1.35",
//                "commons-fileupload:commons-fileupload:1.3.1",
                "com.fasterxml.jackson.core:jackson-databind:2.3.1"
        )
        testCompile(
                "org.springframework:spring-test:$springVersion",
                "junit:junit:4.12"
        )
    }
}
project(':redis_core') {

}
project(':redis_web') {
    apply plugin: "war"
    dependencies {
        compile project(":redis_core")
        compile(
                'org.springframework.boot:spring-boot-starter-thymeleaf',
                'org.springframework.boot:spring-boot-starter-web'
        )
        testCompile(
                'org.springframework.boot:spring-boot-starter-test'
        )
//        providedCompile(
//                "javax.servlet:javax.servlet-api:3.1.0",
//                "javax.servlet.jsp:jsp-api:2.2.1-b03",
//                "javax.servlet.jsp.jstl:javax.servlet.jsp.jstl-api:1.2.1"
//        )
    }
    processResources{
        /* 从'$projectDir/src/main/java'目录下复制文件到'WEB-INF/classes'目录下覆盖原有同名文件*/
        from("$projectDir/src/main/java")
    }

    /*自定义任务用于将当前子项目的java类打成jar包,此jar包不包含resources下的文件*/
    def jarArchiveName="${project.name}-${version}.jar"
    task jarWithoutResources(type: Jar) {
        from sourceSets.main.output.classesDir
        archiveName jarArchiveName
    }

    /*重写war任务:*/
    war {
        dependsOn jarWithoutResources
        /* classpath排除sourceSets.main.output.classesDir目录,加入jarWithoutResources打出来的jar包 */
        classpath = classpath.minus(files(sourceSets.main.output.classesDir)).plus(files("$buildDir/$libsDirName/$jarArchiveName"))
    }
    /*打印编译运行类路径*/
    task jarPath << {
        println configurations.compile.asPath
    }
}

/*从子项目拷贝War任务生成的压缩包到根项目的build/explodedDist目录*/
task explodedDist(type: Copy) {
    into "$buildDir/explodedDist"
    subprojects {
        from tasks.withType(War)
    }
}
```
###  效果图

![效果图](http://img.blog.csdn.net/20170608205441978?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQva2NwNjA2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

- 使用整个项目的build命令就可以把两个模块进行整合了
- 在web模块的build/lib/下的jar直接运行，然后打开浏览器 http://localhost:8080/r/o 看到有文字输出就是构建成功了

