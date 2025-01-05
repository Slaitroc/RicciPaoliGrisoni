# API Endpoint

## Proxy Endpoints

All the calls are routed to the Proxy that handles authentication redirecting private calls to the Authenticator service. That means that, in a sense, all the endpoints are Proxy Endpoints. However there are some calls to which the Proxy add middleware endpoints that don't involve the simple token validation procedures common to all the private calls.
The Token to let the user authenticate is added to the header of the private request. The Proxy will then route the call to the Authenticator service that will validate the token and return the UserID to the Proxy that will then route the call to the right service: Application now or new standalone services in the future.

- POST api/auth/login ✔️
  - Request Body : UserCredentials : Object
  - Responses:
    - 201 Created : Token : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object
  - Proxy -> api/auth/validate-credentials -> api/auth/validate-token

- POST api/auth/create-token ✔️
  - Request Body : UserCredentials : Object
  - Responses:
    - 201 Created : Token : Object
    - 400 Bad Request :  InvalidError : Object
    - 409 Conflict :  ConflictError : Object
    - 500 Internal Server Error :  InternalServerError : Object
  - Proxy -> api/auth/insert-credentials -> api/auth/generate-token

## Application Endpoints

These calls are routed by the Proxy to the Application service that handles the business logic of the application. If the call has the private keyword in his address then the Proxy routes it to `api/auth/validate` middleware to validate the token. That means that every private call shall contain the Token Object in its body.

- POST api/account/public/register ✔️
  - Request Body : UserData : Object
  - Responses:
    - 201 Created : UserIndex : Object
    - 400 Bad Request :  InvalidError : Object
    - 409 Conflict :  ConflictError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/notify/private/send-conf-email ✔️
  - Request Body : UserIndex : Object
  - Responses:
    - 201 Created : Message : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/notify/private/conf-email ✔️
  - Responses:
    - 200 OK
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/sub/private/update-sub ✔️
  - Request Body : SubmissionData : Object
  - Responses:
    - 201 Created : SubmissionID, Suggestions : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 409 Conflict :  ConflictError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- GET api/sub/private/internships/{CompanyID} ✔️
  - Responses:
    - 200 OK : InternshipsList : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 409 Conflict :  ConflictError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- GET api/sub/private/cv/{StudentID} ✔️
  - Responses:
    - 200 OK : CV : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 409 Conflict :  ConflictError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/recommendations/private/{RecommendationID}/accept ✔️
  - Responses:
    - 201 Created :
      - askFeedback : Object
      - Message : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 409 Conflict :  ConflictError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/feedback/private/{RecommendationID}/{FeedbackID}/submit ✔️
  - Request Body : Feedback : Object
  - Responses:
    - 200 OK : Message : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/sub/private/application/{CompanyID} ✔️
  - Responses:
    - 200 OK : Message : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 409 Conflict :  ConflictError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/comm/private/application/{ApplicationID}/accept ✔️
  - Responses:
    - 200 OK : Message : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 409 Conflict :  ConflictError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/interviews/private/send-answer/{InterviewID} ✔️
  - Request Body : Answer : Object
  - Responses:
    - 200 OK : Message : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/interviews/private/send-interview/{InterviewID} ✔️
  - Request Body : InterviewTemplate : Object
  - Responses:
    - 201 Created : Message : Object
    - 400 Bad Request : InvalidError : Object
    - 401 Unauthorized : UnauthorizedError : Object
    - 409 Conflict : ConflictError : Object
    - 500 Internal Server Error : InternalServerError : Object

- POST api/interviews/private/save-template-interview/{InterviewID} ✔️
  - Request Body : InterviewTemplate : Object
  - Responses:
    - 201 Created : Message : Object
    - 400 Bad Request : InvalidError : Object
    - 401 Unauthorized : UnauthorizedError : Object
    - 409 Conflict : ConflictError : Object
    - 500 Internal Server Error : InternalServerError : Object
  
- POST api/interviews/private/{TemplateInterviewID}/send-template-interview/ ✔️
  - Responses:
    - 200 OK : Message : Object
    - 400 Bad Request : InvalidError : Object
    - 401 Unauthorized : UnauthorizedError : Object
    - 409 Conflict : ConflictError : Object
    - 500 Internal Server Error : InternalServerError : Object

- POST api/interviews/private/evaluate-interview/{InterviewID} ✔️
  - Request Body : Evaluation : Object
  - Responses:
    - 201 Created : Message : Object
    - 400 Bad Request : InvalidError : Object
    - 401 Unauthorized : UnauthorizedError : Object
    - 409 Conflict : ConflictError : Object
    - 500 Internal Server Error : InternalServerError : Object

- GET api/applications/private/get-spontaneous-applications  ✔️
  - Responses:
    - 200 OK : Applications : Object
    - 400 Bad Request : InvalidError : Object
    - 401 Unauthorized : UnauthorizedError : Object
    - 500 Internal Server Error : InternalServerError : Object

- GET api/applications/private/get-matches ✔️
  - Responses:
    - 200 OK : Matches
    - 400 Bad Request : InvalidError
    - 401 Unauthorized : UnauthorizedError
    - 500 Internal Server Error : InternalServerError

- POST api/comm/private/{commID}/answer ✔️
  - Request Body : Answer : Object
  - Responses:
    - 201 Created : Message : Object
    - 400 Bad Request : InvalidError : Object
    - 401 Unauthorized : UnauthorizedError : Object
    - 409 Conflict : ConflictError : Object
    - 500 Internal Server Error : InternalServerError : Object

- GET api/comm/private/communications/{CommID} ✔️
  - Responses:
    - 200 OK : Result : Object
    - 400 Bad Request : InvalidError : Object
    - 401 Unauthorized : UnauthorizedError : Object
    - 500 Internal Server Error : InternalServerError : Object

- POST api/comm/private/create ✔️
  - Request Body : Communication : Object
  - Responses:
    - 201 Created : Message : Object
    - 400 Bad Request : InvalidError : Object
    - 401 Unauthorized : UnauthorizedError : Object
    - 500 Internal Server Error : InternalServerError : Object

- GET api/comm/private/communications ✔️
  - Responses:
    - 200 OK : Communications : Object
    - 401 Unauthorized : UnauthorizedError : Object
    - 500 Internal Server Error : InternalServerError : Object

- POST api/comm/private/{commID}/interrupt-internship ✔️
  - Request Body : Reason : Object
  - Responses:
    - 200 OK : Message : Object
    - 400 Bad Request : InvalidError : Object
    - 401 Unauthorized : UnauthorizedError : Object
    - 409 Conflict : ConflictError : Object
    - 500 Internal Server Error : InternalServerError : Object

- POST api/comm/private/{commID}/terminate ✔️
  - Responses:
    - 200 OK : Message : Object
    - 400 Bad Request : InvalidError : Object
    - 401 Unauthorized : UnauthorizedError : Object
      - NotOwner
    - 409 Conflict : ConflictError : Object
    - 500 Internal Server Error : InternalServerError : Object

- POST api/interviews/private/{InterviewID}/send-int-pos-off ✔️
  - Responses:
    - 200 OK : Message : Object
    - 400 Bad Request : InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
      - NotOwner
    - 409 Conflict : ConflictError : Object
    - 500 Internal Server Error : InternalServerError : Object

- POST api/interview/private/accept-int-pos-off/{intPosOffID} ✔️
  - Responses:
    - 200 OK : Message : Object
    - 400 Bad Request : InvalidError : Object
    - 401 Unauthorized : UnauthorizedError : Object
      - NotOwner
    - 409 Conflict : ConflictError : Object
    - 500 Internal Server Error : InternalServerError : Object

## Authenticator Endpoints

These calls are routed by the Proxy to the Authenticator service that handles the authentication and token generation.

- POST api/auth/insert-credentials ✔️
  - Request Body : UserCredentials : Object
  - Responses:
    - 200 OK : Message : Object
    - 400 Bad Request :  InvalidError : Object
    - 409 Conflict :  ConflictError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/auth/validate-credentials ✔️
  - Request Body : UserCredentials : Object
  - Responses:
    - 200 OK : Message : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/auth/generate-token ✔️
  - Request Body : UserCredentials : Object
  - Responses:
    - 201 Created : Token : Object
    - 400 Bad Request :  InvalidError : Object
    - 401 Unauthorized :  UnauthorizedError : Object
    - 500 Internal Server Error :  InternalServerError : Object

- POST api/auth/refresh-token ✔️
  - Request Body : RefreshToken : Object
  - Responses:
    - 201 Created : Token : Object
    - 400 Bad Request : InvalidError : Object
    - 401 Unauthorized : UnauthorizedError : Object
      - RefreshTokenExpired
      - InvalidToken
    - 500 Internal Server Error : InternalServerError : Object

- GET api/auth/validate ✔️
  - Responses:
    - 200 OK : UserID : Object
    - 400 Bad Request : InvalidError : Object
    - 401 Unauthorized : UnauthorizedError : Object
      - TokenExpired
      - InvalidToken
    - 500 Internal Server Error : InternalServerError : Object
