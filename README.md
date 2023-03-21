# MiZaWra
*the home for writers, journalers, and others*

## About
MiZaWra is a minimalistic-designed writing app - providing various kinds of prompts to its users, if they want it. Users can login and then select different modes to write: 

### Modes
- **Free Journaling** 
This mode leads directly to writing mode. You write your own journal, with a small timer (Default: 15 Minutes - if you change it, next time it will be set to this default. After the time, a not too aggressive sound (like birds) reminds you that it is time to get to an end; the timer functionality can be turned off, too) and what you have written will be saved with the date as soon as you click save. 

- **Prompted Journaling**  
This mode has three sub-modes: 
  - Positive (you get positive prompts that will try to make you think about positive stuff) 
  - Neutral (Reflections about yourself, your previous life, your current life, your family - of course that might not be neutral)
  - Philosophical (reflecting some more philosophical questions, like: What is time? Why is there anything rather than nothing? Who is you - the one who thinks or the one you are thinking about when you are thinking of yourself? If there is a god, what is He/She/It like? ) 
  - (Negative (this mode will face negative moments — I’d say, let’s skip this mode for now))
  
- **Prompts for Writing Stories**
This mode has three sub-modes: 
  - Stimulus Story - Word (you get a prompt word, and you should write a story about it) 
  - Stimulus Story - Words (you get a few words, and you should write a story that contains all of these words)
  - Stimulus Story - Paragraph (you get a few sentences, and you should write a story about it/ expand what is written there - feel free to change it completely on the way) 

## Setup of Project

### First of all you need to install all that programs on your computer:
- Install Java 17 or higher
- Install PostgreSQL
- Install Git

### Local variables:
- JDBC_DATABASE_URL = link to your local database (for ex. jdbc:postgresql://localhost:5432/postgres?user=postgres&password=password)
- PORT = free port on your machine (for ex. 8080)
- DOMAIN = domain of our website (locally it's http://localhost:8080)
- EMAIL_LOGIN = login to our email 
- EMAIL_PSSWD = password to email
- CRYPTO_KEY = key for encrypting journals

### Setup database
1. Open pgAdmin (or any other GUI for PostgreSQL)
2. Launch Query Tool
3. Run all queries from schema.sql file
4. Run all queries from data.sql file (it will fulfill tables with needed data)