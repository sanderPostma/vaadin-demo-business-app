# Vaadin Demo Business App (Quarkus Edition)
[![CircleCI](https://circleci.com/gh/igor-baiborodine/vaadin-demo-business-app.svg?style=svg)](https://circleci.com/gh/igor-baiborodine/vaadin-demo-business-app)

#### This project contains the source code generated by [Vaadin's Starter Pack](https://vaadin.com/start) (Business App Starter)

### Branches
* **master** - latest, Vaadin 15 (not yet Quarkus-enabled)
* **V14** - Vaadin 14 (Quarkus 1.8.2.Final)

Business App is a starter for Vaadin. [Live demo](https://business.demo.vaadin.com/)

The starter gives you a productivity boost and a head-start. You get an app shell with a typical hierarchical left-hand menu. The shell, the views and the components are all responsive and touch friendly, which makes them great for desktop and mobile use. The views are built with Java, which enhances Java developers' productivity by allowing them to do all in one language.

The app comes with multiple list views to edit master-detail data. Views can be divided horizontally or vertically to open up the details, and the the details can also be split into multiple tabs for extra space. The details can also be opened fullscreen to maximize the use of space. Additionally there is an opt-in option for opening multiple application views in tabs within the app, for quick comparison or navigation between data. You enable this feature by setting MainLayout.navigationTabs to true.

You can read the detailed documentation in [Vaadin Docs](https://vaadin.com/docs/business-app/overview.html)

## Running the Project in Development Mode (not yet ready to use - there are some issues)

`mvn package quarkus:dev`

Wait for the application to start

Open http://localhost:8080/ to view the application.

## Running the Project in Production Mode

`mvn clean install -P production
 java -jar target/test-service-1.0.0-SNAPSHOT-runner.jar`

The default mode when the application is built or started is 'development'. The 'production' mode is turned on by setting the `vaadin.productionMode` system property when building or starting the app.

Note that if you switch between running in production mode and development mode, you need to do
```
mvn clean
```
before running in the other mode.

## License
A paid Pro or Prime subscription is required for creating a new software project from this starter. After its creation, results can be used, developed and distributed freely, but licenses for the used commercial components are required during development. The starter or its parts cannot be redistributed as a code example or template.

For full terms, see LICENSE
