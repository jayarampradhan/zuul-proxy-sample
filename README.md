ZUUL Proxy POC
================
A sample application using zuul most promising reverse proxy by Netflix

<b>Note** This document will not have definition like what is ZUUL, rather please go through the official wiki, this sample app has been emphasized more on usage part</b>

### Stacks

- `Spring cloud Camden.SR3`
- `Spring Boot 1.4.3.RELEASE`
- `Zuul core 1.3.0`
- `Hystrix core 1.5.6`
- `Docker`

### Back ground

 In typical pod base architecture (where some set of users lies in one pod and some other set of user in some other pod), there should be mechanism
 for doing pod redirection based on the header/cookie.
 In case request served by a random pod, then there should have a way for redirect to the correct pod.


### App Desgin

![alt tag](https://github.com/jayarampradhan/images/blob/master/zuul_proxy_poc_01.png?raw=true)


### How Zuul Proxy Helps here

- Request comes without any pod header, it will redirect to any of the pod (i.e pod 7, pod 8, pod 9) healthy backend lets say pod 8 serving request,
  and the serving backend feels its not the right pod and needs to be redirected, sets the appropriate header and cookie (i.e `X-Redirect-To 9`)
  and from `zuul proxy route filter` sets the appropriate the `serviceId` and refire the request to backend

- Request comes with appropriate pod header (i.e `X-Route-Id 7`), from `zull proxy pre filter`, sets the appropriate `serviceId`, which will direct the request to pod 7

- Request comes with appropriate pod header (i.e `X-Route-Id 7`), from `zull proxy pre filter`, sets the appropriate `serviceId`, which will direct the request to pod 7
  But the serving pod detect user is belongs to the pod 8, then as mentioned in point 1 the request will be again re routed to pod 8

- Using Hystrix, request never land up in the unhealthy server


### Application Set up

sample app is split into two modules
1- Proxy (contains the zull filters)
2- proxy-api (A simple hello world spring boot jersey application)

### Step to start

#### Using Vagrant

- Use Vagrant to bring up the machine and navigate to `cd /dev/workspace`
- Use `gradle build` to build the artifacts
- navigate to `cd docker` copy to jars to this folder i.e `cp ../proxy/build/libs/proxy.jar .;cp ../proxy-api/build/libs/proxy-api.jar .`
- `docker-compose -f docker-compose.yml -p zull-proxy up -d`
- Verify all the instances are up apart from instances named as bad

Useful commands:
- `docker ps -a` to list all the docker container
- `docker logs <container_id> --follow` to checks the container log

#### Don't have Vagrant?

No worries, need some manual work, but sample stacks can be up in few steps

- Change `application.yml` `listOfServers` sections to point to the correct spring boot api port and path. i.e replace `api_app_08_00` with `localhost:<appropriate_spring_boot_port>`
- As both the app i.e `proxy` and `proxy-api` are spring boot tomcat app, don't forget to change the port for both as appropriate



### Test

- `curl -H "X-Route-Id: 8" -H "X-Redirect-To: 9" http://localhost:8080/api/v1/hello -v` will out put as
```json
{"pod":"9","podSubNumber":"2"}
```
Here as `X-Route-Id` is specified in the req header request lands in pod 8, and pod8 sends response with `302` and pod 9 as header as part of response header
and finally request lands to pod 9 and gave the response


### More Filter POC (Wish List)

- Should explore how to add zuul dynamic filters
- Should explore to add zuul filters for surgical redirect (to redirect a request to debug server)
- Should explore more on Hystrix Circuit break feature
- Should explore toset up Hystrix dashboard





