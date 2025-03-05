# Redis-webflux

## This project for explore various Redis data structures and implement caching strategies.

### Prerequisite

We will be using docker-compose.yml to demonstrate redis server on our Microservices architecture.


#### How to run docker-compose.yml
1. Run this command
```bash
docker-compose up
```
2. Launch a separate terminal to access redis-cli
```bash
docker exec -it redis bash
```

### Redisson Summary
- Batch
  - To save network round trip
  - Reactive objects are proxy objects
* Transaction
  * To make a set of commands atomic
+ Pub/Sub
  + Message broadcasting
  + LocalCachedMap uses internally