# Idea behind
"When emergencies occur, rapid medical treatment increases chances of survival exponentially. Using specially equipped motorcycle ambulances" United Hatzalah <br/>
This application helps people that have emergency situations call the nearest equiped motorcycle ambulance.
# BreathBackend
Breath is a distributed application that consists of a backend logic developped with JEE specification and two clients(mobile and web), this repository contains the backend logic
# Application architecture
## Modals
Modals used in this App:
### Modals related to App users (roles/entities):
<strong>Urgencier</strong>: The person in charge of helping people in a emergency situation. <br/>
SimpleUser: The final user of the application (person in a emergency situation). <br/>
Candidat: Each person that want to become an "Urgencier" he must subscribe in order to join a training session, and if he passes the test he becomes an "Urgencier". <br/>
Admin: Person that handle accounts management, adding/removing/altering training sessions.<br/>
all of this modals inherite from a commun entity to help adapt the Domain Driven Design in a effectif way that is we only use a common Abstract class to handle all the common use cases.
### Modals related to Business
Formation: each candidat(role) can subscribe to a "Formation" and after passing the test he can become an "Urgencier"
## Repositories
## Authentication & authorization
