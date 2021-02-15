# GIthub-Repos

Brief about the app: 

When user launch the application. User sees list of Kotlin repository which is loaded by default. The list is infinitely scrollable. 
Clicking on repository item will navigate user to repository details page where repository name, description, top issues, and contributors are listed.
Navigating back will take to the main page where list of repository is listed. 
User can use search box to search repository by language. Also, user can refresh the list using swipe to refresh.
            

Technical aspect:

The app is designed using the MVVM architecture pattern. Kotlin is used for the development of application.
App is architect as single activity multiple fragment for each module. Navigation graph is used for navigation and transition animation is added.
Dependency injection is implemented using Koin. Data-binding is used to bind view and data. BindingAdapter is implemented.
Flow and Coroutine is used for asynchronous programming
100% test cases written for RepositoryViewModel and RepositoryDetailsViewModel class
Documentation is added for the RepositoryListFragment class
Kotlin higher order function, extension function, lateinit, lazy is used when found appropriate.
