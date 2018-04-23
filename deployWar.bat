echo off
del C:\Users\Admin\external-application\wildfly-10.1.0.Final\standalone\deployments\account-project.war
del C:\Users\Admin\external-application\wildfly-10.1.0.Final\standalone\deployments\account-project.war.deployed

xcopy /s C:\Users\Admin\eclipse-workspace\start-account\target\account-project.war C:\Users\Admin\external-application\wildfly-10.1.0.Final\standalone\deployments

call C:\Users\Admin\external-application\wildfly-10.1.0.Final\bin\standalone.bat