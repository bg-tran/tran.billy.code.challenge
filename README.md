# Helpdesk CLI application

A Simple CLI app to search users, tickets or organizations from a data source in JSON format.
This version has only implemented file datasource.

### Build and release
#### Prerequisite
    Gradle 7.1 and above
    Java JDK 1.8 and above
#### Download the source code
    git clone https://github.com/bg-tran/tran.billy.code.challenge.git
#### Build
    cd tran.billy.code.challenge
    ./gradlew assmembleDist
    ./gradlew assmembleDist
Now the binary is copy into <project location>/build/install

Another way to install into your desired directory:
    
    cd <destination directory>
    cp <project location>/build/*.tar .
    tar -xvf *.tar

#### Application config file - config.properties
The app distribution comes with a default config file inside config directory
    
    # Connector iplemention class. 
    # Only support File type at the moment but could be enhanced to support connecting to REST API 
    data.stream.connector.implementation=tran.billy.code.challenge.stream.connector.FileStreamConnectorImpl
    
    # Organization data stream resouce path: could be a local file or enhaced to support REST API  
    organization.data.stream.resource.path=../data/organizations.json

    # User data stream resouce path: could be a local file or enhaced to support REST API
    user.data.stream.resource.path=../data/users.json

    # Ticket data stream resouce path: could be a local file or enhaced to support REST API
    ticket.data.stream.resource.path=../data/tickets.json

#### Run the CLI application using sample data in <app directory>/helpdesk/data
    cd <app directory>/helpdesk/bin
    sh ./helpdesk ../config/config.properties
