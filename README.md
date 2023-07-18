## Build tools & versions used
I have used the following tools and dependencies to accomplish the objectives of the app.  
1. Android Material-1.9.0: Android material helps create capturing UI elements and visual effects. For my project.  
   I have used the material design library for creating circular images for the profile photos.
2. OkHttp-4.10.0: OkHttp library provide great functionality related to making network calls which has been its primary purpose in this app.
3. Jackson-Core-2.13.0: The Jackson core library provides rich support for parsing network responses using annotations and helps you avoid writing boilerplate code to parse network responses.
4. SwipeRefreshLayout-1.1.0: SwipeRefreshLayout is an Android UI component which enables you to execute some business logic when the user performs a swipe-down gesture on the layout area.
   For this app, I used the SwipeRefreshLayout to reload the employee list by making the network call.
5. Picasso: Picasso is a library helpful for fetching images from remote sources. It also has built in capabilities to cache the images in-memory and on disk which helps improve the performance of the app by avoiding having to make frequent network calls to fetch images.
6. Junit-4.13.2: For implementing unit testing for the functionality of the app
7. Robolectric-4.9: Robolectric enables creation of tests which rely on the Android framework in a sandboxed environment. Unit tests which rely on android activities and main threads can be implemented using the robolectric framework.
8. Mockito-5.4.0: Mockito provides a mocking framework where you need not create real time instances of classes and can manipulate the behavior of class methods to simulate the required use-case.
9. Lombok-1.18.28: Lombok auto-generates boiler plate code using annotations. This keeps your classes free and clean of boiler plate implementation code like getters, setters, constructors etc.

## Steps to run the app
1. Download and install the APK file from the releases section of the github project.
2. Once you open the app, you will be able to see the employee directory list.
3. You can tap on any employee to see a summary of the employee.

## What areas of the app did you focus on?
My main focus while developing the app was on the following areas:
1. Functionality and Features: Most of the functionality and features that have been described in the requirements are available in the app.
   This includes creating a list of employees, fetching the employee details from the remote source, fetching the employee photo from the remote source as well as reload/refresh data capability.
2. User interface: I have created a user interface compliant with the most recent styles using progress bar to indicate a loading state, gradient colors, dialog box and pills UI elements.
3. Performance optimization: As mentioned in the requirements, The app is not making any network calls more than is necessary. The resource intensive elements like photos are being cached in memory as well as disk to reduce unnecessary network calls.
4. Error and Exception Handling: The app also contains error handling logic, in case the data returned from the remote source is empty or malformed, in which case it shows an error state UI.
5. Testing and Quality assurance: I have added unit tests for the core logic present in the app making use of Junit, mockito and robolectric libraries to accomplish this. I also did UI testing to make sure, all the functional and non-functional requirements are being met.
6. Documentation: This project contains annotated code for understanding the purpose of what different modules and services are doing. In addition, this readme file will also serve as a guiding point for the app usage and purpose.

## What was the reason for your focus? What problems were you trying to solve?
The main reason behind the aforementioned areas was to deliver a competent product to the user which at a minimum meets, the outlined requirements.  
The main objective of the app is to deliver a list of employees working in the country. On the surface, the objective is pretty straightforward,  
but as we dive deeper into the functionality, more questions arise like, how do we make performant network calls, how do we address caching, how should the UI look and how to test the code.  
The requirements document gives a high level information on what is expected from the app which provided alignment on what needs to be done.  
However, the implementation details on what tools and dependencies to use and how to go about designing the solution was the crux of the project.  
To summarize, I have addressed the following points:
1. To make asynchronous network calls so that main thread is not not blocked
2. Caching resources so that you do not have to make unnecessary network calls everytime you want to access the resources
3. Testing the functionality to make sure that the functional requirements (at minimum) are being met
4. Create a UI that will attract and appeal to the users.

## How long did you spend on this project?
I was able to complete the project sometime around 8 hours spread over multiple days.

## Did you make any trade-offs for this project? What would you have done differently with more time?
I have worked on the following trade offs, for the project:
1. Okhttp vs Retrofit: OkHttp and Retrofit are libraries used for making network calls. Both of them offer capability to make asynchronous network calls.
   For my project, I went ahead with OkHttp because of familiarity, easy of use and lightweight nature. Retrofit is built on top of OkHttp and offers a variety of features,
   which were not needed for this project hence I decided to use a simpler implementation. Also I was not so familiar with Retrofit framework and
   using it in my project would have entailed me to spend time in getting acquainted with the library which is why I decided to go with OkHttp.

2. Default vs Custom UI animations: Places which require or have scope for using animations contain default animations (for e.g. Progress bar indicating loading state) because, getting custom animations right requires some trial and error if you don't have extensive experience with it.

If I had more time, I would probably try to use Retrofit instead of OkHttp as it offers a rich variety of features like JSON serialization/deserialization and request/response handling, and makes it easier to extend and maintain code
Additionally, I would also spend some more time on Custom animations to make the app more appealing to the users.

## What do you think is the weakest part of your project?
I would attribute the weakest part of my project would be limited test case coverage. I have tried to cover as much testable code as I could.
However, due to time constraints I was not able to cover some parts of the code.
Additionally, as mentioned above, I feel that making use of retrofit libraries and custom UI animations are a couple of weak points  
in the project as well.

## Did you copy any code or dependencies? Please make sure to attribute them here!
I have not copied any code or dependencies for my project.

## Is there any other information you’d like us to know?
Just that it has been some time since I worked on android and I enjoyed working on this project a lot and I look forward to the next steps as well.