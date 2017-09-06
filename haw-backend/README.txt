JavaEE Server backend for HAW project
-------------------------------------

The project "Hochschulen fuer Angewandte Wissenschaften" allows
getting to setup and read out the compute shares on HPC compute
resources at various HPC providers.
It's developped in the frame bwHPC and bwHPC-C5 for the
state of Baden-Wuerttemberg, Germany.

The server component relies heavily on code developped
by students of the Hochschule fuer Technik, Stuttgart,
the so-called JAK-Project:
  https://sourceforge.net/projects/swproject/
for the Jazzclub Armer Konrad:
  https://www.jak-weinstadt.de

This code is delopped and released under the BSD 3-clause
license. Please pass on the License.


This software requires:
 - Java compiler and SDK (>1.7)
 - JavaEE SDK
   Parts require Glassfish/Payara application server
 - Maven for building

 - The Glassfish/Payara server requires the MySQL connector/J


Installation is typically:
   mvn generate-resources    # executed once
   mvn clean                 # Deletes artefacts, namely target/ directory
   mvn package
   mvn glassfish:deploy      # To deploy the Server component on the Application server. 

