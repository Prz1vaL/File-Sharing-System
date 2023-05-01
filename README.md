# File Sharing System

## Description:

File Transfer System is a simple <strong> client-server application </strong> that allows users to transfer files from a
client to a server. The server receives the files and displays a list of received files. Users can then download the
received files from the server.

The application is written in Java and uses Java Swing for the client and server UIs. It supports the transfer of any
file type, including text and image files.

## Features:

<ol>
<li>Simple client-server file transfer</li>
<li>Server-side file list display</li>
<li>File preview and download on the server</li>
<li>Support for text and image file formats</li>
</ol>

## Installation:

<ol>
<li> <strong>Clone the repository:</strong></li>
<code>git clone https://github.com/yourusername/APM30-File-Transfer-System.git</code>

<li><strong>Compile the Java files: </strong></li>
<code>javac org/apm30/client/Client.java org/apm30/server/Server.java org/apm30/server/MyFile.java </code>
</ol>

## Usage:

<ol>
<li><strong> Start the server:</strong></li>
<code>java org.apm30.server.Server</code>
<li><strong> Start the client:</strong></li>
<code>java org.apm30.client.Client</code>
<li>On the client-side, click "Choose MyFile" to select a file to send.</li>
<li>Click "Send MyFile" to send the selected file to the server.</li>
<li>On the server-side, the received file will be displayed in the list.</li>
<li>Click on a file in the list to preview and download the file.</li>
</ol>

## Contributions:

<ol>
<li>Clone the repository from the school GitHub</li>
<li>Create a new branch for your feature or bug fix.</li>
<li>Write unit tests for your code.</li>
<li>Implement your feature or bug fix.</li>
<li>Ensure all tests pass.</li>
<li>Commit your changes and push to your branch on the GitHub</li>
<li>Submit a merge request to merge your changes into the main branch.</li>
</ol>

## License:

This project is licensed under the <code> MIT License. </code>See the <code>LICENSE</code> file for more information.
