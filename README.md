# Idea behind
"When emergencies occur, rapid medical treatment increases chances of survival exponentially. Using specially equipped motorcycle ambulances" United Hatzalah <br/>
This application helps people that have emergency situations call the nearest equiped motorcycle ambulance.
# BreathBackend
Breath is a distributed application that consists of a backend logic developped with JEE specification and two clients(mobile and web), this repository contains the backend logic
# Application architecture
I had the intention to build from scrash this application in order to experience some Design Paterns, and to help me get better into "clean code" and SOLID approaches, espacially when it comes to add new features and how to make your application extensible. "A minimun features modification/extension need to have a minimum code modification".
## Modals
Modals used in this App:
### Modals related to App users (roles/entities):
<strong>Urgencier</strong>: The person in charge of helping people in a emergency situation. <br/>
<strong>SimpleUser</strong>: The final user of the application (person in a emergency situation). <br/>
<strong>Candidat</strong>: Each person that want to become an "Urgencier" he must subscribe in order to join a training session, and if he passes the test he becomes an "Urgencier". <br/>
<strong>Admin</strong>: Person that handle accounts management, adding/removing/altering training sessions.<br/>
all of this modals inherite from a commun entity to help adapt the Domain Driven Design in a effectif way that is we only use a common Abstract class to handle all the common use cases.
### Modals related to Business
<strong>Formation</strong>: each candidat(role) can subscribe to a "Formation" and after passing the test he can become an "Urgencier"
## Repositories
<strong>AbstractRepo: </strong>This repo helps handle all the crud operation related to each entity, that helps using a clean DDD design and not repeating code in useless way.
<strong>RoleRepo: </strong>Each role has a specific repo that handle all the special operations.
## Resources
The how we handle App resources is that each Role use cases are build after a base URL named after the user role.<strong>i.e:</strong><br/>
<strong>candidat resources: </strong> candidat/....
## Authentication & authorization
Authentication and authorization are build using filters, each resource that demands authentication is put after the base url "/secured" (so that we intercept ony if a user reach secured resources).<br/>
to handle authorization: we handle autorization by testing the equality between the usertype (role) and the base url that he reaches.
