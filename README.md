# Enterprise Application Integration (implemented using an example Scooter Maintenance System)
This repository contains a Scooter Maintenance System build in order to demonstrate/implement various enterprise integration patterns described by the book _Enterprise integration patterns: Designing, building, and deploying messaging solutions_[^1].

The Scooter Maintenance System consists of four disparate subsystems:
- **Scooter OBD (on-board diagnostic)**, for diagnosing and reporting issues with the scooter. Each scooter has its own OBD system running, which is able to send notification messages in case of any issues. The OBD system is simulated by generating random scooter diagnostics/error messages. Marked with orange in the system diagram.
- **Statistics dashboard**, also referred to as statboard, a dashboard for showing statistics of the Scooter Maintenance System. The statistics dashboards is a very simple Spring Boot web app showing the total amount of broken and fixed scooter. Marked with purple in the system diagram.
- **Management system**, a component that routes broken scooter messages to the maintenance app, and that stores/updates the scooter information. Marked with red in the system diagram.
- **Maintenance app**, the mobile application that is used by maintenance personnel to receive broken scooter notifications, and to mark repaired scooter as functional. Marked with yellow in the system diagram.

The system diagram below shows how the different subsystems are integrated.

![System diagram](https://github.com/rriesebos/enterprise-application-integration/blob/main/images/diagrams/system-diagram.png)

The implemented enterprise integration patterns include:
- Channel adapter
- Content-based router
- Point-to-point channel
- Competing consumers
- Dead letter channel
- Durable subscriber
- Message filter
- Messaging gateway
- Messaging mapper
- Idempotent receiver

Additional details and explanation/mapping of each pattern can be found in the [report](https://github.com/rriesebos/enterprise-application-integration/blob/main/report.pdf).

## Running the application
- Install Docker: <https://docs.docker.com/install/>
- Install Docker Compose: <https://docs.docker.com/compose/install/>
- Navigate to the root folder
- Start the application: `docker-compose up`
- Open the [scooter-maintenance-app](https://github.com/rriesebos/enterprise-application-integration/tree/main/scooter-maintenance-app) project in Android Studio, change the `HOST_NAME` in [MessagingGateway.java](https://github.com/rriesebos/enterprise-application-integration/blob/main/scooter-maintenance-app/app/src/main/java/com/eai/scootermaintenanceapp/messaging/MessagingGateway.java) to your local IP address and run the application.

After the previous steps have been successfully completed, the following is now happening:
- Apache Activemq artemis is now running and available at http://localhost:8161/console/ with 'default' as username and password. Furthermore, a MongoDB instance is running and a Cassandra instance is running.
- The [broken-scooter-generator](https://github.com/rriesebos/enterprise-application-integration/tree/main/broken-scooter-generator) has now started generating random, fictional scooter diagnostics/error messages (use `docker attach broken-scooter-generator` to observe the generator errors). 
- The [statistics dashboard (database) adapter](https://github.com/rriesebos/enterprise-application-integration/tree/main/statboard-adapter) is now storing each generated scooter error in the Cassandra (timeseries) database.
- The total number of broken and fixed scooters as stored in this database are shown on the [statistics dashboard](https://github.com/rriesebos/enterprise-application-integration/tree/main/statboard), available at http://localhost:8080. 
- The [management-system](https://github.com/rriesebos/enterprise-application-integration/tree/main/management-system) is now keeping track of the status of each scooter and storing it in the MongoDB database. It is also forwarding the scooter errors to the Scooter Maintenance (Android) App.
- Each instance of the [scooter-maintenance-app](https://github.com/rriesebos/enterprise-application-integration/tree/main/scooter-maintenance-app) is now competing for the broken scooter error messages (within the selected region) forwarded by the management system. The broken scooters w/ details are shown in a list, and the selected broken scooter can be viewed on the map. Scooters can be marked as fixed, updating the statboard and management system.

[^1]: Hohpe, Gregor, and Bobby Woolf. _Enterprise integration patterns: Designing, building, and deploying messaging solutions._ Addison-Wesley Professional, 2004.
