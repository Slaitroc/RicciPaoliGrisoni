# APICALLS 

- POST api/auth/login
  - Url Parameters : NO
  - Request Body : UserCredentials : Object
  - Responses:
    - 200 OK : Token : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object

## APICALLS API BACKEND

- POST api/account/public/register
  - Url Parameters : NO
  - Request Body : UserData : Object
  - Responses:
    - 201 Created : UserID : Object
    - 400 Bad Request :  InvalidError : Object
    - 409 Conflict :  ConflictError : Object

- POST api/notify/private/send-conf-email
  - Url Parameters : NO
  - Request Body : UserIndex, Token : Object
  - Responses:
    - 200 OK : Message : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/notify/private/conf-email
  - Url Parameters : Token, UserIndex
  - Request Body : NO
  - Responses:
    - 200 OK 
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/account/private/session
  - Url Parameters : NO
  - Request Body : NO
  - Responses:
    - 200 OK : Message : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/sub/private/update-sub
  - Url Parameters : NO
  - Request Body : SubmissionData : Object
  - Responses:
    - 201 Created : SubmissionID, Suggestions : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 409 Conflict :  ConflictError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- GET api/sub/private/internships/{CompanyID}
  - Url Parameters : NO
  - Responses:
    - 200 OK : InternshipsList : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 409 Conflict :  ConflictError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- GET api/sub/private/cv/{StudentID}
  - Url Parameters : NO
  - Responses:
    - 200 OK : CV : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 409 Conflict :  ConflictError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/recommendations/private/{RecommendationID}/accept
  - Url Parameters : NO
  - Responses:
    - 200 OK : askFeedback : Object
    - 201 Created 
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 409 Conflict :  ConflictError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/feedback/private/{RecommendationID}/{FeedbackID}/submit
  - Url Parameters : NO
  - Responses:
    - 200 OK :
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/sub/private/application/{CompanyID}
  - Url Parameters : Company
  - Responses:
    - 200 OK :
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/comm/private/application/{ApplicationID}/accept
  - Url Parameters : ApplicationID
  - Responses:
    - 200 OK :
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 409 Conflict :  ConflictError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/interviews/private/send-answer/{InterviewID}
  - Url Parameters : InterviewID
  - Responses:
    - 200 OK :
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/interviews/private/send-interview/{InterviewID}
  - Url Parameters : 
  - Responses:
    - 200 OK :
    - 201 Created :
    - 400 Bad Request :
    - 401 Unauthorized :
    - 409 Conflict :
    - 500 Internal Server Error :

- PPOST api/interviews/private/evaluate-interview/{InterviewID}
  - Url Parameters : 
  - Responses:
    - 200 OK :
    - 201 Created :
    - 400 Bad Request :
    - 401 Unauthorized :
    - 409 Conflict :
    - 500 Internal Server Error :

- GET api/applications/private/get-spontaneous-applications
  - Url Parameters : 
  - Responses:
    - 200 OK :
    - 201 Created :
    - 400 Bad Request :
    - 401 Unauthorized :
    - 409 Conflict :
    - 500 Internal Server Error :

- GET api/applications/private/get-matches
  - Url Parameters : 
  - Responses:
    - 200 OK :
    - 201 Created :
    - 400 Bad Request :
    - 401 Unauthorized :
    - 409 Conflict :
    - 500 Internal Server Error :


- POST api/comm/private/{commID}/answer
  - Url Parameters : 
  - Responses:
    - 200 OK :
    - 201 Created :
    - 400 Bad Request :
    - 401 Unauthorized :
    - 409 Conflict :
    - 500 Internal Server Error :

<!-- questa avrebbe i parametri tipo ?<params> 
Non so  se va aggiunta qualche descrizione per dirlo -->
- GET api/comm/private/communications/{CommID} 
  - Url Parameters : 
  - Responses:
    - 200 OK :
    - 201 Created :
    - 400 Bad Request :
    - 401 Unauthorized :
    - 409 Conflict :
    - 500 Internal Server Error :

<!-- questa avrebbe i parametri tipo ?<params> 
Non so  se va aggiunta qualche descrizione per dirlo -->
- POST api/comm/private/create
  - Url Parameters : 
  - Responses:
    - 200 OK :
    - 201 Created :
    - 400 Bad Request :
    - 401 Unauthorized :
    - 409 Conflict :
    - 500 Internal Server Error :

<!-- questa avrebbe i parametri tipo ?<params> 
Non so  se va aggiunta qualche descrizione per dirlo -->
- GET api/comm/private/communications
  - Url Parameters : 
  - Responses:
    - 200 OK :
    - 201 Created :
    - 400 Bad Request :
    - 401 Unauthorized :
    - 409 Conflict :
    - 500 Internal Server Error :

- POST api/comm/private/{commID}/interrupt-internship 
  - Url Parameters : 
  - Responses:
    - 200 OK :
    - 201 Created :
    - 400 Bad Request :
    - 401 Unauthorized :
    - 409 Conflict :
    - 500 Internal Server Error :

- POST api/comm/private/{commID}/close-complaint
  - Url Parameters : 
  - Responses:
    - 200 OK :
    - 201 Created :
    - 400 Bad Request :
    - 401 Unauthorized :
    - 409 Conflict :
    - 500 Internal Server Error :

- POST api/interviews/private/{InterviewID}/send-int-pos-off
  - Url Parameters : 
  - Responses:
    - 200 OK :
    - 201 Created :
    - 400 Bad Request :
    - 401 Unauthorized :
    - 409 Conflict :
    - 500 Internal Server Error :

- POST api/interview/private/accept-int-pos-off/{intPosOffID}
  - Url Parameters : 
  - Responses:
    - 200 OK :
    - 201 Created :
    - 400 Bad Request :
    - 401 Unauthorized :
    - 409 Conflict :
    - 500 Internal Server Error :

## AUTHENTICATOR

- POST api/auth/insert-credentials
  - Url Parameters : NO
  - Request Body : UserCredentials : Object
  - Responses:
    - 200 OK : Token : Object
    - 400 Bad Request :  InvalidError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/auth/validate-credentials
  - Url Parameters : NO
  - Request Body : UserCredentials : Object
  - Responses:
    - 200 OK : Message : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/auth/generate-token
  - Url Parameters : NO
  - Request Body : UserCredentials : Object
  - Responses:
    - 200 OK : Token : Object
    - 400 Bad Request :  InvalidError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/auth/create-token
  - Url Parameters : NO
  - Request Body : UserCredentials : Object
  - Responses:
    - 200 OK : Token : Object
    - 400 Bad Request :  InvalidError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/auth/refresh-toke
  - Url Parameters : 
  - Responses:
    - 200 OK :
    - 201 Created :
    - 400 Bad Request :
    - 401 Unauthorized :
    - 409 Conflict :
    - 500 Internal Server Error :

- GET api/auth/validate
  - Url Parameters : 
  - Responses:
    - 200 OK :
    - 201 Created :
    - 400 Bad Request :
    - 401 Unauthorized :
    - 409 Conflict :
    - 500 Internal Server Error :
