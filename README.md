# chat-web-app

The goal of this project is to implement a web app in Java that provides an interface with a chat window for users to 
post, get and delete messages.

## Setup

### Java

This project is developed using Java 8 update 261 `jre-8u261`

1. Download the Java Runtime Environment (JRE) from [Oracle](https://www.oracle.com/java/technologies/javase-jre8-downloads.html) 
for your operating system

### Tomcat

Tomcat is needed to run the Java servlets and launch the web app locally. This project uses Tomcat 9.0

1. Go to Tomcat's [download page](https://tomcat.apache.org/download-90.cgi)
2. For macOS, download the tar.gz, unzip it and save the folder somewhere (you will need it later)

### IntelliJ IDEA

1. Clone this repo using HTTPS, SSH, GitHub Desktop or any other method

2. Open IntelliJ IDEA, select `Open Project` and choose `~/chat-web-app/pom.xml`. When the dialog box appears select 
`Open as project`. This will make IntelliJ automatically build the project for you.

3. On IntelliJ's topbar, go to Run -> Edit Configurations

Add Tomcat Server and set the following options:
- Application Server: Find the path of the Tomcat server you previously downloaded. If do not have Tomcat installed,
please refer to the section above so you are able to finish your setup
- URL: `http://localhost:8080/chat_web_app_war/`. _NOTE: The URL will be changed soon_

Before you hit apply, above this button there will be a message in red saying `No artifacts marked for deployment`.
Please hit `Fix` and select `chat-web-app:war`

4. Run the new Tomcat configuration and navigate away!
