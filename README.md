# eai_rug
Enterprise application integration systems of sharing scooters for the EAI course at the RUG.

### Install

Run ``docker-compose up``

Download glassfish from `http://download.oracle.com/glassfish/5.0.1/release/glassfish-5.0.1.zip`

Unzip glassfish

Run glassfish:
```
cd glassfish5\glassfish\bin\
sh startserv
```

Now go to localhost where glassfish set up the web interface and create a publish subscribe topic and a normal queue.
