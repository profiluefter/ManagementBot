[//]: # (TODO: Order)
#Management Bot

##Features

###Core
- Delete a specified amount of messages
- Add a custom emoji based on an url
- Localization in english and german
- Save choosen language per user across restarts
- Customize the prefix
- Execute generic Java code

###Music
- Display the current playing song
- Pause and resume the player
- Queue songs
- Skip songs

###Work in Progress
- Permission system

##Commands

###Core
All commands are prefixed with the prefix of the config file. Standard: -
- `clear <amount>`
  - Deletes the specified amount of messages
- `emoji <name> <url>` OR `emoji <name>` with an image as attachment
  - Adds an emoji from the specified URL(has to be an common image format as a direct link) with the specified name
- `help`
  - Displays a short description for every command
- `me set lang <EN/DE>`
  - Sets the language of the responses of the bot per user
- `ping`
  - Displays the current ping of the bot
- `eval <source code>`

###Music
  - Compiles the given source code and executes it. Has to be a valid Java class. The [IO class](src/main/java/eval/environment/IO.java) can be used for IO purposes.
- `join`
  - Joins the bot to the current voice channel. Has to be executed before any other music related command. In the future this command shouldn't be needed.
- `np`
  - Displays the currently playing song and related info.
- `play <URL>`
  - Enqueues the linked song to the queue. Supports some big sites like youtube, but doesn't support search at the moment.
- `pause`
  - Pauses playback.
- `resume`
  - Resumes playback.
- `skip`
  - Skip a song in the playlist and plays the next song in the queue.