# DeployTOOL(发布工具)
* python
  * [windows10下面的python 和 django 的安装](https://github.com/yueheng-li/pythonLearn/wiki/python-and-django-install-in-windows)
  * [django 工程的做成以及问题对应](https://github.com/yueheng-li/pythonLearn/wiki/django-project)
  * [django 静态资源追加](https://github.com/yueheng-li/pythonLearn/wiki/django-%E9%9D%99%E6%80%81%E8%B5%84%E6%BA%90%E8%BF%BD%E5%8A%A0)
  
  * [python 操作远程文件（paramiko）的安装](https://github.com/yueheng-li/pythonLearn/wiki/python-%E6%93%8D%E4%BD%9C%E8%BF%9C%E7%A8%8B%E6%96%87%E4%BB%B6%EF%BC%88paramiko%EF%BC%89)
```
cd \path\DeployTool
python manage.py runserver 0.0.0.0:8087
http://ip:8087/
```

# automyte-poc(MYTE)
* mysql
```
mysql安装(yum install mysql5.7)
source automyte-poc\automyte-web\src\main\resources\sql
配置文件：
automyte-poc\automyte-web\src\main\resources\
application.properties->spring.profiles.active=dev
application-dev.properties

下面的内容修改
######  db config start ###### 
spring.datasource.url=jdbc:mysql://localhost:3307/myte
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
######  db config end ###### 
```
* docker方式启动
```
使用maven package把工程build成auto-web-1.0.0-SNAPSHOT.jar
mkdir /usr/local/docker
cp auto-web-1.0.0-SNAPSHOT.jar /usr/local/docker/
cp /automyte-poc/automyte-web/docker/Dockerfile /usr/local/docker/
cd /usr/local/docker/
docker build -t cloud-lch:v1 /opt/docker-file/cloud/
docker run -d -p 8080:8080 cloud-lch:v1 /opt/docker-file/cloud/
http://ip:8080/myte/
```
* Linux 
  * [Linux jdk 安装](https://github.com/yueheng-li/linuxLearn/wiki/Linux-jdk-%E5%AE%89%E8%A3%85)
  * [linux maven 安装](https://github.com/yueheng-li/linuxLearn/wiki/linux-maven-%E5%AE%89%E8%A3%85)
  * [linux git 安装](https://github.com/yueheng-li/linuxLearn/wiki/linux-git-%E5%AE%89%E8%A3%85)
  * [linux maven poc的automyte poc打包脚本](https://github.com/yueheng-li/linuxLearn/wiki/linux-maven-poc%E7%9A%84automyte-poc%E8%84%9A%E6%9C%AC)
  * [linux 下spring boot的启动shell](https://github.com/yueheng-li/linuxLearn/wiki/linux-%E4%B8%8Bspring-boot%E7%9A%84%E5%90%AF%E5%8A%A8shell)
  
# crawler-poc(爬虫工具)
参照automyte-poc(MYTE)的设定。
