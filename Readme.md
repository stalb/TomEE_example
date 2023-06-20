# Small example for bug [TOMEE-4226]([https://issues.apache.org/jira/projects/TOMEE/issues/TOMEE-4226?filter=addedrecently) #


The webapp defines a JPA Object [Mesure.java](src/main/java/fr/usmb/m2isc/mesure/jpa/Mesure.java) 
and a EJB [MesureEJB.java](src/main/java/fr/usmb/m2isc/mesure/ejb/MesureEJB.java).

In order to use a PostgreSQL database to persist the data, a data-source is defined using a `@DataSourceDefinition` annotation in the EJB: 

```java
@DataSourceDefinition (
    name = "java:app/env/jdbc/MyDataSource",
    className = "org.postgresql.ds.PGSimpleDataSource",
    user = "admin",
    password = "admin",
    serverName = "pgserver",
    portNumber = 5432,
    databaseName = "mesures")
```

When the webapp is deployed on *TomEE webprofile*, you get the following error:

```
mesures-tomee-1     | 20-Jun-2023 15:47:30.094 INFO [main] jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke Deploying web application archive [/usr/local/tomee/webapps/mesures.war]
mesures-tomee-1     | 20-Jun-2023 15:47:30.112 INFO [main] org.apache.tomee.catalina.TomcatWebAppBuilder.init ------------------------- localhost -> /mesures
mesures-tomee-1     | 20-Jun-2023 15:47:30.776 INFO [main] org.apache.openejb.config.ConfigurationFactory.configureApplication Configuring enterprise application: /usr/local/tomee/webapps/mesures
mesures-tomee-1     | 20-Jun-2023 15:47:31.653 INFO [main] org.apache.openejb.config.InitEjbDeployments.deploy Auto-deploying ejb MesureEJB: EjbDeployment(deployment-id=MesureEJB)
mesures-tomee-1     | 20-Jun-2023 15:47:31.668 INFO [main] org.apache.openejb.config.ConfigurationFactory.configureService Configuring Service(id=mesures/app/env/jdbc/MyDataSource, type=Resource, provider-id=Default JDBC Database)
mesures-tomee-1     | 20-Jun-2023 15:47:31.670 INFO [main] org.apache.openejb.assembler.classic.Assembler.createRecipe Creating Resource(id=mesures/app/env/jdbc/MyDataSource)
mesures-tomee-1     | 20-Jun-2023 15:47:31.760 SEVERE [main] org.apache.tomee.catalina.TomcatWebAppBuilder.startInternal Unable to deploy collapsed ear in war StandardEngine[Catalina].StandardHost[localhost].StandardContext[/mesures]
mesures-tomee-1     |   org.apache.xbean.recipe.ConstructionException: Error invoking factory method: public static javax.sql.CommonDataSource org.apache.openejb.resource.jdbc.DataSourceFactory.create(java.lang.String,boolean,java.lang.Class,java.lang.String,org.apache.openejb.util.Duration,org.apache.openejb.util.Duration,org.apache.openejb.util.Duration,boolean) throws java.lang.IllegalAccessException,java.lang.InstantiationException,java.io.IOException
mesures-tomee-1     |           at org.apache.xbean.recipe.ReflectionUtil$StaticFactory.create(ReflectionUtil.java:1019)
mesures-tomee-1     |           at org.apache.xbean.recipe.ObjectRecipe.internalCreate(ObjectRecipe.java:279)
mesures-tomee-1     |           at org.apache.xbean.recipe.AbstractRecipe.create(AbstractRecipe.java:96)
mesures-tomee-1     |           at org.apache.xbean.recipe.AbstractRecipe.create(AbstractRecipe.java:61)
mesures-tomee-1     |           at org.apache.openejb.assembler.classic.Assembler.doCreateResource(Assembler.java:3178)
mesures-tomee-1     |           at org.apache.openejb.assembler.classic.Assembler.createResource(Assembler.java:3013)
mesures-tomee-1     |           at org.apache.openejb.config.ConfigurationFactory.doInstall(ConfigurationFactory.java:466)
mesures-tomee-1     |           at org.apache.openejb.config.ConfigurationFactory.install(ConfigurationFactory.java:459)
mesures-tomee-1     |           at org.apache.openejb.config.AutoConfig.installResource(AutoConfig.java:2215)
mesures-tomee-1     |           at org.apache.openejb.config.AutoConfig.processApplicationResources(AutoConfig.java:1048)
mesures-tomee-1     |           at org.apache.openejb.config.AutoConfig.deploy(AutoConfig.java:192)
mesures-tomee-1     |           at org.apache.openejb.config.ConfigurationFactory$Chain.deploy(ConfigurationFactory.java:420)
mesures-tomee-1     |           at org.apache.openejb.config.ConfigurationFactory.configureApplication(ConfigurationFactory.java:1033)
mesures-tomee-1     |           at org.apache.tomee.catalina.TomcatWebAppBuilder.startInternal(TomcatWebAppBuilder.java:1318)
mesures-tomee-1     |           at org.apache.tomee.catalina.TomcatWebAppBuilder.configureStart(TomcatWebAppBuilder.java:1162)
mesures-tomee-1     |           at org.apache.tomee.catalina.GlobalListenerSupport.lifecycleEvent(GlobalListenerSupport.java:134)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.fireLifecycleEvent(LifecycleBase.java:123)
mesures-tomee-1     |           at org.apache.catalina.core.StandardContext.startInternal(StandardContext.java:4852)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase.addChildInternal(ContainerBase.java:683)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase.addChild(ContainerBase.java:658)
mesures-tomee-1     |           at org.apache.catalina.core.StandardHost.addChild(StandardHost.java:662)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.deployWAR(HostConfig.java:1023)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig$DeployWar.run(HostConfig.java:1910)
mesures-tomee-1     |           at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Unknown Source)
mesures-tomee-1     |           at java.base/java.util.concurrent.FutureTask.run(Unknown Source)
mesures-tomee-1     |           at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75)
mesures-tomee-1     |           at java.base/java.util.concurrent.AbstractExecutorService.submit(Unknown Source)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.deployWARs(HostConfig.java:824)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.deployApps(HostConfig.java:474)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.start(HostConfig.java:1617)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.lifecycleEvent(HostConfig.java:318)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.fireLifecycleEvent(LifecycleBase.java:123)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.setStateInternal(LifecycleBase.java:423)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.setState(LifecycleBase.java:366)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:898)
mesures-tomee-1     |           at org.apache.catalina.core.StandardHost.startInternal(StandardHost.java:795)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1332)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1322)
mesures-tomee-1     |           at java.base/java.util.concurrent.FutureTask.run(Unknown Source)
mesures-tomee-1     |           at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75)
mesures-tomee-1     |           at java.base/java.util.concurrent.AbstractExecutorService.submit(Unknown Source)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:871)
mesures-tomee-1     |           at org.apache.catalina.core.StandardEngine.startInternal(StandardEngine.java:249)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           at org.apache.catalina.core.StandardService.startInternal(StandardService.java:428)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           at org.apache.catalina.core.StandardServer.startInternal(StandardServer.java:917)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           at org.apache.catalina.startup.Catalina.start(Catalina.java:772)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/java.lang.reflect.Method.invoke(Unknown Source)
mesures-tomee-1     |           at org.apache.catalina.startup.Bootstrap.start(Bootstrap.java:347)
mesures-tomee-1     |           at org.apache.catalina.startup.Bootstrap.main(Bootstrap.java:478)
mesures-tomee-1     |   Caused by: org.apache.xbean.recipe.ConstructionException: Error setting property: public void org.postgresql.ds.common.BaseDataSource.setUrl(java.lang.String)
mesures-tomee-1     |           at org.apache.xbean.recipe.ObjectRecipe.setProperty(ObjectRecipe.java:528)
mesures-tomee-1     |           at org.apache.xbean.recipe.ObjectRecipe.setProperties(ObjectRecipe.java:378)
mesures-tomee-1     |           at org.apache.xbean.recipe.ObjectRecipe.internalCreate(ObjectRecipe.java:289)
mesures-tomee-1     |           at org.apache.xbean.recipe.AbstractRecipe.create(AbstractRecipe.java:96)
mesures-tomee-1     |           at org.apache.xbean.recipe.AbstractRecipe.create(AbstractRecipe.java:61)
mesures-tomee-1     |           at org.apache.xbean.recipe.AbstractRecipe.create(AbstractRecipe.java:49)
mesures-tomee-1     |           at org.apache.openejb.resource.jdbc.DataSourceFactory.create(DataSourceFactory.java:181)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/java.lang.reflect.Method.invoke(Unknown Source)
mesures-tomee-1     |           at org.apache.xbean.recipe.ReflectionUtil$StaticFactory.create(ReflectionUtil.java:1009)
mesures-tomee-1     |           ... 56 more
mesures-tomee-1     |   Caused by: java.lang.IllegalArgumentException: URL invalid jdbc:hsqldb:file:/usr/local/tomee/data/hsqldb/hsqldb
mesures-tomee-1     |           at org.postgresql.ds.common.BaseDataSource.setUrl(BaseDataSource.java:1133)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/java.lang.reflect.Method.invoke(Unknown Source)
mesures-tomee-1     |           at org.apache.xbean.recipe.ObjectRecipe$MethodMember.setValue(ObjectRecipe.java:648)
mesures-tomee-1     |           at org.apache.xbean.recipe.ObjectRecipe.setProperty(ObjectRecipe.java:519)
mesures-tomee-1     |           ... 67 more
mesures-tomee-1     | 20-Jun-2023 15:47:31.762 SEVERE [main] jdk.internal.reflect.NativeMethodAccessorImpl.invoke Error destroying child
mesures-tomee-1     |   org.apache.catalina.LifecycleException: An invalid Lifecycle transition was attempted ([before_destroy]) for component [StandardEngine[Catalina].StandardHost[localhost].StandardContext[/mesures]] in state [STARTING_PREP]
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.invalidTransition(LifecycleBase.java:430)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.destroy(LifecycleBase.java:316)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase.removeChild(ContainerBase.java:778)
mesures-tomee-1     |           at org.apache.tomee.catalina.TomcatWebAppBuilder.undeploy(TomcatWebAppBuilder.java:1688)
mesures-tomee-1     |           at org.apache.tomee.catalina.TomcatWebAppBuilder.undeploy(TomcatWebAppBuilder.java:1668)
mesures-tomee-1     |           at org.apache.tomee.catalina.TomcatWebAppBuilder.startInternal(TomcatWebAppBuilder.java:1349)
mesures-tomee-1     |           at org.apache.tomee.catalina.TomcatWebAppBuilder.configureStart(TomcatWebAppBuilder.java:1162)
mesures-tomee-1     |           at org.apache.tomee.catalina.GlobalListenerSupport.lifecycleEvent(GlobalListenerSupport.java:134)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.fireLifecycleEvent(LifecycleBase.java:123)
mesures-tomee-1     |           at org.apache.catalina.core.StandardContext.startInternal(StandardContext.java:4852)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase.addChildInternal(ContainerBase.java:683)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase.addChild(ContainerBase.java:658)
mesures-tomee-1     |           at org.apache.catalina.core.StandardHost.addChild(StandardHost.java:662)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.deployWAR(HostConfig.java:1023)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig$DeployWar.run(HostConfig.java:1910)
mesures-tomee-1     |           at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Unknown Source)
mesures-tomee-1     |           at java.base/java.util.concurrent.FutureTask.run(Unknown Source)
mesures-tomee-1     |           at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75)
mesures-tomee-1     |           at java.base/java.util.concurrent.AbstractExecutorService.submit(Unknown Source)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.deployWARs(HostConfig.java:824)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.deployApps(HostConfig.java:474)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.start(HostConfig.java:1617)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.lifecycleEvent(HostConfig.java:318)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.fireLifecycleEvent(LifecycleBase.java:123)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.setStateInternal(LifecycleBase.java:423)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.setState(LifecycleBase.java:366)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:898)
mesures-tomee-1     |           at org.apache.catalina.core.StandardHost.startInternal(StandardHost.java:795)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1332)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1322)
mesures-tomee-1     |           at java.base/java.util.concurrent.FutureTask.run(Unknown Source)
mesures-tomee-1     |           at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75)
mesures-tomee-1     |           at java.base/java.util.concurrent.AbstractExecutorService.submit(Unknown Source)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:871)
mesures-tomee-1     |           at org.apache.catalina.core.StandardEngine.startInternal(StandardEngine.java:249)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           at org.apache.catalina.core.StandardService.startInternal(StandardService.java:428)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           at org.apache.catalina.core.StandardServer.startInternal(StandardServer.java:917)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           at org.apache.catalina.startup.Catalina.start(Catalina.java:772)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/java.lang.reflect.Method.invoke(Unknown Source)
mesures-tomee-1     |           at org.apache.catalina.startup.Bootstrap.start(Bootstrap.java:347)
mesures-tomee-1     |           at org.apache.catalina.startup.Bootstrap.main(Bootstrap.java:478)
mesures-tomee-1     | 20-Jun-2023 15:47:31.763 SEVERE [main] jdk.internal.reflect.NativeMethodAccessorImpl.invoke Error deploying web application archive [/usr/local/tomee/webapps/mesures.war]
mesures-tomee-1     |   java.lang.IllegalStateException: Error starting child
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase.addChildInternal(ContainerBase.java:686)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase.addChild(ContainerBase.java:658)
mesures-tomee-1     |           at org.apache.catalina.core.StandardHost.addChild(StandardHost.java:662)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.deployWAR(HostConfig.java:1023)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig$DeployWar.run(HostConfig.java:1910)
mesures-tomee-1     |           at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Unknown Source)
mesures-tomee-1     |           at java.base/java.util.concurrent.FutureTask.run(Unknown Source)
mesures-tomee-1     |           at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75)
mesures-tomee-1     |           at java.base/java.util.concurrent.AbstractExecutorService.submit(Unknown Source)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.deployWARs(HostConfig.java:824)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.deployApps(HostConfig.java:474)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.start(HostConfig.java:1617)
mesures-tomee-1     |           at org.apache.catalina.startup.HostConfig.lifecycleEvent(HostConfig.java:318)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.fireLifecycleEvent(LifecycleBase.java:123)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.setStateInternal(LifecycleBase.java:423)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.setState(LifecycleBase.java:366)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:898)
mesures-tomee-1     |           at org.apache.catalina.core.StandardHost.startInternal(StandardHost.java:795)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1332)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1322)
mesures-tomee-1     |           at java.base/java.util.concurrent.FutureTask.run(Unknown Source)
mesures-tomee-1     |           at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75)
mesures-tomee-1     |           at java.base/java.util.concurrent.AbstractExecutorService.submit(Unknown Source)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:871)
mesures-tomee-1     |           at org.apache.catalina.core.StandardEngine.startInternal(StandardEngine.java:249)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           at org.apache.catalina.core.StandardService.startInternal(StandardService.java:428)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           at org.apache.catalina.core.StandardServer.startInternal(StandardServer.java:917)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           at org.apache.catalina.startup.Catalina.start(Catalina.java:772)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/java.lang.reflect.Method.invoke(Unknown Source)
mesures-tomee-1     |           at org.apache.catalina.startup.Bootstrap.start(Bootstrap.java:347)
mesures-tomee-1     |           at org.apache.catalina.startup.Bootstrap.main(Bootstrap.java:478)
mesures-tomee-1     |   Caused by: org.apache.catalina.LifecycleException: Failed to start component [StandardEngine[Catalina].StandardHost[localhost].StandardContext[/mesures]]
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.handleSubClassException(LifecycleBase.java:440)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:198)
mesures-tomee-1     |           at org.apache.catalina.core.ContainerBase.addChildInternal(ContainerBase.java:683)
mesures-tomee-1     |           ... 37 more
mesures-tomee-1     |   Caused by: org.apache.tomee.catalina.TomEERuntimeException: org.apache.xbean.recipe.ConstructionException: Error invoking factory method: public static javax.sql.CommonDataSource org.apache.openejb.resource.jdbc.DataSourceFactory.create(java.lang.String,boolean,java.lang.Class,java.lang.String,org.apache.openejb.util.Duration,org.apache.openejb.util.Duration,org.apache.openejb.util.Duration,boolean) throws java.lang.IllegalAccessException,java.lang.InstantiationException,java.io.IOException
mesures-tomee-1     |           at org.apache.tomee.catalina.TomcatWebAppBuilder.startInternal(TomcatWebAppBuilder.java:1352)
mesures-tomee-1     |           at org.apache.tomee.catalina.TomcatWebAppBuilder.configureStart(TomcatWebAppBuilder.java:1162)
mesures-tomee-1     |           at org.apache.tomee.catalina.GlobalListenerSupport.lifecycleEvent(GlobalListenerSupport.java:134)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.fireLifecycleEvent(LifecycleBase.java:123)
mesures-tomee-1     |           at org.apache.catalina.core.StandardContext.startInternal(StandardContext.java:4852)
mesures-tomee-1     |           at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
mesures-tomee-1     |           ... 38 more
mesures-tomee-1     |   Caused by: org.apache.xbean.recipe.ConstructionException: Error invoking factory method: public static javax.sql.CommonDataSource org.apache.openejb.resource.jdbc.DataSourceFactory.create(java.lang.String,boolean,java.lang.Class,java.lang.String,org.apache.openejb.util.Duration,org.apache.openejb.util.Duration,org.apache.openejb.util.Duration,boolean) throws java.lang.IllegalAccessException,java.lang.InstantiationException,java.io.IOException
mesures-tomee-1     |           at org.apache.xbean.recipe.ReflectionUtil$StaticFactory.create(ReflectionUtil.java:1019)
mesures-tomee-1     |           at org.apache.xbean.recipe.ObjectRecipe.internalCreate(ObjectRecipe.java:279)
mesures-tomee-1     |           at org.apache.xbean.recipe.AbstractRecipe.create(AbstractRecipe.java:96)
mesures-tomee-1     |           at org.apache.xbean.recipe.AbstractRecipe.create(AbstractRecipe.java:61)
mesures-tomee-1     |           at org.apache.openejb.assembler.classic.Assembler.doCreateResource(Assembler.java:3178)
mesures-tomee-1     |           at org.apache.openejb.assembler.classic.Assembler.createResource(Assembler.java:3013)
mesures-tomee-1     |           at org.apache.openejb.config.ConfigurationFactory.doInstall(ConfigurationFactory.java:466)
mesures-tomee-1     |           at org.apache.openejb.config.ConfigurationFactory.install(ConfigurationFactory.java:459)
mesures-tomee-1     |           at org.apache.openejb.config.AutoConfig.installResource(AutoConfig.java:2215)
mesures-tomee-1     |           at org.apache.openejb.config.AutoConfig.processApplicationResources(AutoConfig.java:1048)
mesures-tomee-1     |           at org.apache.openejb.config.AutoConfig.deploy(AutoConfig.java:192)
mesures-tomee-1     |           at org.apache.openejb.config.ConfigurationFactory$Chain.deploy(ConfigurationFactory.java:420)
mesures-tomee-1     |           at org.apache.openejb.config.ConfigurationFactory.configureApplication(ConfigurationFactory.java:1033)
mesures-tomee-1     |           at org.apache.tomee.catalina.TomcatWebAppBuilder.startInternal(TomcatWebAppBuilder.java:1318)
mesures-tomee-1     |           ... 43 more
mesures-tomee-1     |   Caused by: org.apache.xbean.recipe.ConstructionException: Error setting property: public void org.postgresql.ds.common.BaseDataSource.setUrl(java.lang.String)
mesures-tomee-1     |           at org.apache.xbean.recipe.ObjectRecipe.setProperty(ObjectRecipe.java:528)
mesures-tomee-1     |           at org.apache.xbean.recipe.ObjectRecipe.setProperties(ObjectRecipe.java:378)
mesures-tomee-1     |           at org.apache.xbean.recipe.ObjectRecipe.internalCreate(ObjectRecipe.java:289)
mesures-tomee-1     |           at org.apache.xbean.recipe.AbstractRecipe.create(AbstractRecipe.java:96)
mesures-tomee-1     |           at org.apache.xbean.recipe.AbstractRecipe.create(AbstractRecipe.java:61)
mesures-tomee-1     |           at org.apache.xbean.recipe.AbstractRecipe.create(AbstractRecipe.java:49)
mesures-tomee-1     |           at org.apache.openejb.resource.jdbc.DataSourceFactory.create(DataSourceFactory.java:181)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/java.lang.reflect.Method.invoke(Unknown Source)
mesures-tomee-1     |           at org.apache.xbean.recipe.ReflectionUtil$StaticFactory.create(ReflectionUtil.java:1009)
mesures-tomee-1     |           ... 56 more
mesures-tomee-1     |   Caused by: java.lang.IllegalArgumentException: URL invalid jdbc:hsqldb:file:/usr/local/tomee/data/hsqldb/hsqldb
mesures-tomee-1     |           at org.postgresql.ds.common.BaseDataSource.setUrl(BaseDataSource.java:1133)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/java.lang.reflect.Method.invoke(Unknown Source)
mesures-tomee-1     |           at org.apache.xbean.recipe.ObjectRecipe$MethodMember.setValue(ObjectRecipe.java:648)
mesures-tomee-1     |           at org.apache.xbean.recipe.ObjectRecipe.setProperty(ObjectRecipe.java:519)
mesures-tomee-1     |           ... 67 more
mesures-tomee-1     | 20-Jun-2023 15:47:31.765 INFO [main] jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke Deployment of web application archive [/usr/local/tomee/webapps/mesures.war] has finished in [1,669] ms
```

You have to define the `url` properties get rid of the error: 

```java
@DataSourceDefinition (
    name = "java:app/env/jdbc/MyDataSource",
    className = "org.postgresql.ds.PGSimpleDataSource",
    properties = {"user=admin", "password=admin"},
    url = "jdbc:postgresql://pgserver:5432/mesures")
```

## How to run the example ##

### Using docker compose ###

Clone the repository, then run the command `docker compose up --build`
at the root of the project.

This will build the project, start the PostgreSQL database and then start the TomEE webprofile werver.

The logs of the TomEE show that the webapp cannot be deployed because TomEE tries to create the postgresql datasource using the defaut hsqldb database url:

```
mesures-tomee-1     |   Caused by: java.lang.IllegalArgumentException: URL invalid jdbc:hsqldb:file:/usr/local/tomee/data/hsqldb/hsqldb
mesures-tomee-1     |           at org.postgresql.ds.common.BaseDataSource.setUrl(BaseDataSource.java:1133)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
mesures-tomee-1     |           at java.base/java.lang.reflect.Method.invoke(Unknown Source)
mesures-tomee-1     |           at org.apache.xbean.recipe.ObjectRecipe$MethodMember.setValue(ObjectRecipe.java:648)
mesures-tomee-1     |           at org.apache.xbean.recipe.ObjectRecipe.setProperty(ObjectRecipe.java:519)
mesures-tomee-1     |           ... 67 more
```

If you replace the non-working data-source definition with the working one, 
then the same command `docker compose up --build` will correctly deploy the webapp.

You can use the command `curl http://localhost:8080/mesures/AddMesureServlet?temp=23&piece=living-room&valider=OK` to create some data and 
`docker compose exec pgserver psql -U admin mesures -c "select * from mesure"` 
to verify that the tables have been created and the dat inserted in the `mesure` table:

```
$ docker compose exec pgserver psql -U admin mesures -c "select * from mesure"
 id  |       datemesure        | piece  | temp
-----+-------------------------+--------+------
 351 | 2023-06-20 13:15:55.146 | bureau |   20
(1 row)
```

### Deploy manually ###

1. Start a PostgreSQL instance.
2. Modify the data-source definition using the correct values in order to use the PostgreSQL instance.
3. Build the webapp archive: `./gradlew build`
4. Copy the `build/libs/mesures.war` archive into the TomEE webapps directory and
5. Start the TomEE server.

### Remark ###

If instead of the `@DataSourceDefinition` annotation, you define the data-source in the standard descriptor [web.xml](src/main/webapp/WEB-INF/web.xml), you will obtain the same results.






