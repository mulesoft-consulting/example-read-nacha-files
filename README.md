Read NACHA files
================
Purpose
-------

<p>This example shows how to read and process NACHA files and parse the ACH format.</p>

<p>To read ACH format I used code from an open source project http://achviewer.sourceforge.net/.<BR>
This project is a Swing Java application that allows to open ach files, view the contents and modify and add records.
</p> 

How it works
-------
<p>
Using a custom java component I embedded the code from this project. This will read the file and return Pojos
</p>

<p>
	NachaToJavaTransformer.java: is the code for a MessageTransformer that references the ACHViewer code.<BR>
	This Message Transformer is used in the flow to read the files.	
</p>

How to run the example
-------

<p>1. To read, modify or create new files, run the app from the command line:<BR>
<code>java -jar ACHViewer.jar</code>
</p>

<p>
2. Open the mule project
</p>

<p>
3. The src/test/resources contains two example files.</br>
Place them in a temp folder and modify the mule-app.properties with the properties for input folder and output folder.
</p>

<p>
4. The file inbound will pick and read the files. The message processor will process the file contents and return Pojos with the ACH structure.
The datamapper will transform that to a Map.
</p>

Versions
-------
This example is built with runtime 3.5.1

References
-------
ACH Viewer open source project: http://achviewer.sourceforge.net/

