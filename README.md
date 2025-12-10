<p align="center">
	<img alt="logo" src="https://oscimg.oschina.net/oscnet/up-b99b286755aef70355a7084753f89cdb7c9.png">
</p>
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">RuoYi v3.6.6</h1>
<h4 align="center">Base on Ruoyi to build the 6650 final project</h4>
<h5 align="center">Notice: This project comment has lots of Chinese</h5>

<p align="center">
	<a href="https://gitee.com/y_project/RuoYi-Cloud/stargazers"><img src="https://gitee.com/y_project/RuoYi-Cloud/badge/star.svg?theme=dark"></a>
	<a href="https://gitee.com/y_project/RuoYi-Cloud"><img src="https://img.shields.io/badge/RuoYi-v3.6.6-brightgreen.svg"></a>
	<a href="https://gitee.com/y_project/RuoYi-Cloud/blob/master/LICENSE"><img src="https://img.shields.io/github/license/mashape/apistatus.svg"></a>
</p>

## Modules

~~~
com.ruoyi     
├── ruoyi-ui              //  [80]
├── ruoyi-gateway         // [8080]
├── ruoyi-auth            //  [9200]
├── ruoyi-api             
│       └── ruoyi-api-system                          
├── ruoyi-common          
│       └── ruoyi-common-core                         
│       └── ruoyi-common-datascope                    
│       └── ruoyi-common-datasource                   
│       └── ruoyi-common-log                          
│       └── ruoyi-common-redis                        
│       └── ruoyi-common-seata                        
│       └── ruoyi-common-security                     
│       └── ruoyi-common-sensitive                    
│       └── ruoyi-common-swagger                      
├── ruoyi-modules         
│       └── ruoyi-system                              //  [9201]
│       └── ruoyi-gen                                 //  [9202]
│       └── ruoyi-job                                 //  [9203]
│       └── ruoyi-file                                // [9300]
├── ruoyi-visual          
│       └── ruoyi-visual-monitor                      // [9100]
├──pom.xml                
~~~

How to Start it (Chinese)：https://doc.ruoyi.vip/ruoyi-cloud/document/hjbs.html


## Built-in Features

1.  User Management: Manages system users and their configuration.
2.  Department Management: Configures the system’s organizational structure (company, department, team) with tree-based display and support for data permissions.
3.  Post (Position) Management: Configures the positions or job roles assigned to users.
4.  Menu Management: Configures system menus, operating permissions, and button-level permission identifiers.
5.  Role Management: Assigns role-based menu permissions and defines data access scopes by organization.
6.  Dictionary Management: Maintains frequently used and relatively fixed data across the system.
7.  Parameter Management: Dynamically configures commonly used system parameters.
8.  Notifications and Announcements: Publishes and maintains system-wide announcement information.
9.  Operation Logs: Records and queries normal operation logs; captures and queries system exception logs.
10. Login Logs: Records and queries user login activities, including abnormal login attempts.
11. Online Users: Monitors the status of currently active users within the system.
12. Scheduled Tasks: Manages task scheduling online (add, edit, delete) and displays execution logs.
13. Code Generator: Generates backend and frontend code (Java, HTML, XML, SQL), supporting CRUD scaffolding downloads. 。
14. System API Documentation: Automatically generates API documentation based on business code.
15. Service Monitoring: Monitors system metrics such as CPU, memory, disk usage, and runtime stack information.
16. Online Builder: Drag-and-drop form builder that generates corresponding HTML code.
17. Data Source / Connection Pool Monitoring: Monitors database connection pool status and analyzes SQL performance bottlenecks.


