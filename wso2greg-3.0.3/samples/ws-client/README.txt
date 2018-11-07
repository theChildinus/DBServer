
This file explains how to use the Web service API of the registry to upload a file.
===================================================================================

This is useful when uploading large files, since atom based remote registry API is not that efficient in uploading large files.

Before getting the sample to work you have to run ant from GREG_HOME/bin and start the registry server.

Edit the build.xml by filling the path element with GREG-HOME and the AXIS2-HOME.
Steps to get this sample to work:
--------------------------------

1.Run the ant build file. i.e.( ant upload)
 
2. Provide keystore path which is provided with bin distribution "GREG_HOME/resources/security/client-truststore.jks".

3. Provide filepath which you want to upload.


