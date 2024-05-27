
<h1 align="center">
  <a href="https://github.com/yanghaiji/DependentDetective.git">
  <img src="https://github.com/yanghaiji/DependentDetective/blob/main/doc/img/dep.png" 
  alt="Standard - DependentDetectiveLogo" width="500"></a>
</h1>
<p align="center">
    <a href="https://spring.io/projects"><img src='https://img.shields.io/badge/license-Apache%202-borightgreen' alt='License'/></a>
    <a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring%20Boot-2.6.3-brightgreen)" alt="npm version"></a>
    <a href="https://standardjs.com"><img src="https://img.shields.io/badge/code_style-standard-brightgreen.svg" alt="Standard - Java Style Guide"></a>
</p>


在执行当前项目的过程中，我们有必要对所采纳的技术框架及其版本进行严格审查，以确保它们符合企业架构的规范和要求。
为了高效地管理和统计项目的技术栈，我们开发了了DependentDetective工具。该工具的主要目的是提供一种自动化机制，
用于检测项目所依赖的技术框架及其版本，从而帮助我们确保项目的合规性，并简化技术栈的审计过程。

包含了后端与前端的项目扫描


# 使用方式

## 后端扫描

### 修改配置

修改`application.yaml`里边的`mvnEnv`变量，修改成自己的环境变量


### 访问

启动相关后，将`doc/http`里的json文件导入到postman，将参数修改为带扫描的项目路径

<h1 align="center">
  <a href="https://github.com/yanghaiji/DependentDetective.git">
  <img src="https://github.com/yanghaiji/DependentDetective/blob/main/doc/img/postman.png" 
  alt="Standard - DependentDetectiveLogo" width="500"></a>
</h1>

这里的projectPath指定根路径即可，程序会自动扫描出，根路径下所有的maven程序，进行依赖分析，扫描结束后会自动导出Excel
<h1 align="center">
  <a href="https://github.com/yanghaiji/DependentDetective.git">
  <img src="https://github.com/yanghaiji/DependentDetective/blob/main/doc/img/excel_tepmlate.png" 
  alt="Standard - DependentDetectiveLogo" width="500"></a>
</h1>

## 前端扫描

### 安装依赖

> npm install
>

### 运行扫描的脚本

> node .\dependency-scanner.js 需要扫描项目的根路径
>

完成扫描后，会在本地生成一个excel，示例如下:

<h1 align="center">
  <a href="https://github.com/yanghaiji/DependentDetective.git">
  <img src="https://github.com/yanghaiji/DependentDetective/blob/main/doc/img/frontend_excel.png" 
  alt="Standard - DependentDetectiveLogo" width="500"></a>
</h1>

