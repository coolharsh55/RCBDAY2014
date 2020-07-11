# Birthday App (RCBDAY)

This was an android app I made as a gift for a friend's birthday in 2014. The app collected and showed messages from friends on the day of the birthday. I'm putting this on a public repository for archival purposes.

The app was built within the available free time, and when I was barely learning Android app development. The code quality is horrenduos, there are no helpful (or any) comments. 

## Functionality of the App

1. Until the day of the birthday, show a message (e.g. _"surprise will be revealed soon"_)
2. On the day of the birthday, the app notifies with a birthday message (e.g. _"Happy Birthday"_ in notification area)
3. Upon opening the app on the birthday (and after), the collected texts and pictures are shown as a slideshow

## Design & Components

* app code filse are in `./main/java/harshp/app/rcbday2014/`
* `Activity0.java`  - this is the default activity of the app. It is responsible for figuring out if it is time to open the gift yet. If yes, then Activity1 is opened. Otherwise a difference until the day of the birthday is shown.
* `Activity1.java` - this is the activity for showing the texts and pictures. It builds a library/set of objects and allows swiping through them.
* `AlarmReceiver.java` - is responsible for the alarm functionality in Android whereby the notification is generated at intervals up to the day of the birthday
* `DBHelper.java` - file to extract messages from a SQLite database storing the texts and pictures
* `MyWish.java` - another separate activity to show my (personal) message for the birthday
* `NotifyService.java` - registers the notification with android notification service system
* `OnSwipeTouchListener.java` - implements the swiping functionality between messages in Activity1
* `./main/assets/databases/test.db` - will be the SQLite database containing all the messages
* `./db/imgdb.py` - python script to bundle images in a folder (numbered in base10) into the SQLite database. The schema used is `MESSAGES(MESSAGE,SENDER,IMAGE)` where `IMAGE` is a binary blob and the rest are text. The code has been sanitised to remove personal messages. There is also a file `imgdebug.py` for debugging images in the database.