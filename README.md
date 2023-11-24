# Survey app
An Android demo app made with Jetpack compose, code style, and project structure inspired from [NowInAndroid app](https://github.com/android/nowinandroid)

# Configuration
Add 2 properties `CLIENT_ID` and `CLIENT_SECRETS` into `secrects.defaults.properties` file and you should be good to run the project

# Screenshots
- Splash screen using Splash API
- Login screen with basic validation
- Home screen with a horizontal list of Survey overview
- A simple Survey detail screen

<img width="876" alt="Screen Shot 2023-11-24 at 14 53 42" src="https://github.com/baocntt2602/survey/assets/43448274/6a87ec09-4911-4d99-9ae0-73dbd35a71dd">

# Requirement achievements
1. Authentication:\
   ✔️ Implement the login authentication screen.\
   ✔️ Implement the OAuth authentication including the storage of access tokens.\
   ✔️ Implement the automatic usage of refresh tokens to keep the user logged in using the OAuth API.\
2. Home Screen:\
   ✔️ On the home screen, each survey card must display the following info:\
   ✔️ Cover image (background)\
   ✔️ Name (in bold)\
   ✔️ Description\
   ✔️ Implement the caching of surveys onto the device. \
   There must be 2 actions:\
   ✔️ Horizontal scroll through the surveys.\
   ✔️ A button “Take Survey” should take the user to the survey detail screen. \
   ✔️ The list of surveys must be fetched when opening the application.\
   ✔️ Show a loading animation when fetching the list of surveys.\
   ✔️ The navigation indicator list (bullets) must be dynamic and based on the API response.\
