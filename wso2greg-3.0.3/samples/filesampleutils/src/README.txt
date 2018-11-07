Exporting/importing a file to Remote registry
============================================

Introduction
============
This sample demonstrates how to export a local file system into a remote registry, and how to import a remote registry into a local file system.  We can build our content in the local file system with the directory hierarchy we want, including any type of content.  Then we can use the registry API to export our local file system into the registry.

Once we upload a local file system into the registry we have "socially enabled" it.  We can comment on resources, we can tag, we can rate, etc... 

We can also build our hierarchy in the Registry with the structure we want, and then we can download or import the remote registry into our local file system.  Then the registry will create the exact same directory structure and download all the files in the registry. 

Running the sample
==================

Running the sample is just a matter of executing an ant file.  If you don't
have ant, you can download it at http://ant.apache.org/. Run 'ant' at the 'bin' directory
of the distribution.

Exporting local file system into a remote registry 
--------------------------------------------------

Run "ant upload" inside the "samples/filesample" directory.  You will be asked for: 

 -Keystore file path : Provide GREG_HOME/resources/security/client-truststore.jks
 -Registry URL : Provide "https://localhost:9443/registry"
                     [If your registry is in somewhere else then give that path]
 - User name : If you have not changed the admin user then pass �admin�
 - Password : Use "admin"
 - FromPath : Location in the file system to export from.  Enter the
                 full path to the "resources" directory(eg:- C:\test\test.txt)
 - ToPath : Where to put the resource.  In this sample let's use
               "/sample/file"

 - If you login to the management console you can see that all the files have been
  moved and the registry has the same structure as the filesystem under 'Resources' table. 


Importing a file into the local file system
-----------------------------------------------

Now we go the other way.  Again, run "ant download" first, and provide values:

  -Keystore file path : Provide GREG_HOME/resources/security/client-truststore.jks
  - Registry URL : Provide "https://localhost:9443/registry"
                    [If your registry is in somewhere else then give that path]
  - User name : If you have not changed the admin user then pass �admin�
  - Password : Use "admin"
  - FromPath :  Resource path, such as "/sample/file"
  - ToPath : Where to put the resource in the local filesystem. For this
               sample let's use "C:\Documents and Settings\test"

- Once you run ant you will see a newly created folder hierarchy in the path you specified.

Note:- 

1. You should put all the required jars into the "GREG_HOME\lib" folder.


Jars you may need to run the sample
-----------------------------------
1.jaxen-1.1.1. jar( http://www.java2s.com/Code/JarDownload/jaxen-1.1.1.jar.zip)
2.abdera -jars 0.4.0 (http://abdera.apache.org/)
3.commons httpclient 3.0.1 jar(http://www.java2s.com/Code/JarDownload/commons-httpclient-3.0.1.jar.zip)
4.commons-codec-1.3 jar (http://www.java2s.com/Code/JarDownload/commons-codec-1.3.jar.zip)
5.axiom-impl-1.2.5 jar(http://www.java2s.com/Code/JarDownload/axiom-impl-1.2.5.jar.zip)
6.axiom-api-1.2.5 jar(http://www.java2s.com/Code/JarDownload/axiom-api-1.2.5.jar.zip)
7.org.wso2.carbon.registry.core-2.0.2.jar (Find at GREG_HOME\webapps\ROOT\WEB-INF\plugins\common folder)
8.axis2-transport-base-1.0.0.wso2v2.jar(Find at GREG_HOME\webapps\ROOT\WEB-INF\plugins\common folder)

Keep all those jars in "GREG_HOME/lib directory"
