# HealthEasy

**HealthEasy** is an Android-based Online Doctor Appointment Booking application. This is a **basic, offline-first prototype** developed to showcase Android UI design, local data handling, and application flow without backend or live service integration.

### Features:
- **Doctor Profiles:** Browse doctors by specialization and view detailed profiles including experience, fees, ratings, and availability.
- **Appointment Booking:** Simple UI for selecting dates and time slots with basic availability validation.
- **Payment System:** Card and UPI payment screens with form validation only (no real payment processing).
- **Favorites Management:** Users can add or remove doctors from favorites; favorite doctors are stored using SharedPreferences.
- **Bottom Navigation:** Includes three tabs — **Home**, **Favourites**, and **Account**.
- **Account Management:** Register and log in through a simple user interface with session management.
- **Profile Management:** View personal details and change account password.
- **Payment History:** View all successful payments in the Payments option under the Account tab.
- **Support Section:** Support page includes a contact mobile number and email ID for user assistance.
- **Static App Theme:** The app uses a fixed color theme that remains the same in both light and dark modes.

### Notes:
- The app does not interact with any real doctor databases; all data used is **dummy data**.
- There is **no real payment processing**. Only form validations are implemented.
- **Data Storage:**  
  - SharedPreferences are used for login sessions and favorite doctors.  
  - SQLite is used for storing user information and appointment details.
- **Screen Responsiveness:** The app does not maintain screen responsiveness across different device sizes.

---

## Technologies Used

- **Android SDK**
- **Java**
- **XML for UI Design**
- **SQLite**
- **SharedPreferences**
- **Gradle for Build Management**

---

### Upcoming Features:
- **Backend Integration:** Introduction of Firebase or REST APIs for real-time data handling.
- **Messaging and Notifications:** Features planned for future versions.
- **Online Payments:** Integration of a real payment gateway.
- **Improved UI Responsiveness:** Better layout handling for multiple screen sizes.

---

## Screenshots
<img src="Screen Recordings & Screenshots/1.png" height = "400" width="200"/>  <img src="Screenshots/2.png" height = "400" width="200"/>  <img src="Screenshots/3.png" height = "400" width="200"/>  <img src="Screenshots/4.png" height = "400" width="200"/>  
<img src="Screenshots/5.png" height = "400" width="200"/>  <img src="Screenshots/6.png" height = "400" width="200"/>  <img src="Screenshots/7.png" height = "400" width="200"/>  <img src="Screenshots/8.png" height = "400" width="200"/>  
<img src="Screenshots/9.png" height = "400" width="200"/>  <img src="Screenshots/10.png" height = "400" width="200"/>  <img src="Screenshots/11.png" height = "400" width="200"/> <img src="Screenshots/12.png" height = "400" width="200"/>
<img src="Screenshots/13.png" height = "400" width="200"/>  <img src="Screenshots/14.png" height = "400" width="200"/>  <img src="Screenshots/15.png" height = "400" width="200"/> <img src="Screenshots/16.png" height = "400" width="200"/>
<img src="Screenshots/17.png" height = "400" width="200"/>  <img src="Screenshots/18.png" height = "400" width="200"/>  
<img src="Screenshots/19.png" height = "200" width="400"/> <img src="Screenshots/20.png" height = "200" width="400"/> <img src="Screenshots/21.png" height = "200" width="400"/>

### Screen Recordings

The following screen recordings showcase the various features of the app:

[Older Version Screen Recordings]
- **Account Activity:** [Watch video](https://drive.google.com/file/d/19aeGypwY8ykQhq9KYu6WS593E0lIQF1I/view?usp=drive_link)
- **Appointment Selection Activity:** [Watch video](https://drive.google.com/file/d/1roUQFOJPfqK5x197wHyGlKo0h55jEAwv/view?usp=drive_link)
- **Doctor Details Activity:** [Watch video](https://drive.google.com/file/d/1xq_Jx1bJkPOj1goHwdoxcRKpD7HpBTwb/view?usp=drive_link)
- **Favourites Activity:** [Watch video](https://drive.google.com/file/d/1KJqPBWBfvkDWTFFV7zBabeHmgY_0uliG/view?usp=drive_link)
- **Home Activity:** [Watch video](https://drive.google.com/file/d/1QvwpO9DJi4fAmyQlj-ODKr5Acw23alrE/view?usp=drive_link)
- **Payment Card Activity:** [Watch video](https://drive.google.com/file/d/1YhXkbbIbCmt8Vj2bmkAZzeZnVpjV1u9v/view?usp=drive_link)
- **Payment UPI Activity:** [Watch video](https://drive.google.com/file/d/1bCDjjuuyrkv4s1oJtHpOB8ulHKVEXWiT/view?usp=drive_link)
- **Sign Up & Login Activity:** [Watch video](https://drive.google.com/file/d/10EG86U96jbYOCkIjD-YQVHD3MgOZNorF/view?usp=drive_link)
- **Two Users:** [Watch video](https://drive.google.com/file/d/1ykSm3XeqmOtnVpJMy3L5ZGb1_VjZJW_X/view?usp=drive_link)
- **User 2:** [Watch video](https://drive.google.com/file/d/1zDuWmyyaFHNx6T7H2uv8097BDtx-qtLd/view?usp=drive_link)

## Project Status

This project was developed as a **Major Project** for the **Bachelor of Computer Applications (BCA)** program and is open for future improvements and feature enhancements.