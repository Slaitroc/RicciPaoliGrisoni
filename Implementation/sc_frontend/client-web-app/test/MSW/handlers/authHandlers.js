import { http, HttpResponse } from "msw";

const fakeAuthDatabase = [
  {
    token: 1,
    email: "test@example.com",
    password: "password123",
  },
  {
    token: 2,
    email: "user@example.com",
    password: "securepass",
  },
];

const fakeApplicationDatabase = [
  {
    uuid: 1,
    name: "John Doe",
    email: "test@example.com",
    secondaryEmail: "secondary.mail@example.com",
    phone: "+1234567890",
    address: "1234 Elm Street",
    city: "Springfield",
  },
  {
    uuid: 2,
    name: "Jane Doe",
    email: "user@example.com",
    secondaryEmail: "secondary@example.com",
    phone: "+0987654321",
    address: "5678 Oak Street",
    city: "Shelbyville",
  },
];

export const authHandlers = [
  http.post("/auth-api/login", async ({ request }) => {
    const body = await request.json();

    if (!body.email || !body.password || !body) {
      return new HttpResponse(null, { status: 400 });
    }

    const user = fakeAuthDatabase.find(
      (u) => u.email === body.email && u.password === body.password
    );

    if (user) {
      return new HttpResponse(JSON.stringify({ token: user.token }), {
        status: 200,
      });
    } else {
      return new HttpResponse(null, {
        status: 204,
        statusText: "No Content: unknown credentials",
      });
    }
  }),

  http.get(
    "/application-api/account/private/retrieve-profile",
    async ({ request }) => {
      try {
        const authHeader = request.headers.get("Authorization");
        if (!authHeader || !authHeader.startsWith("Bearer ")) {
          return new HttpResponse(null, { status: 401 });
        }

        const uuid = parseInt(authHeader.split(" ")[1], 10);
        const user = fakeApplicationDatabase.find((u) => u.uuid === uuid);

        if (user) {
          return new HttpResponse(
            JSON.stringify({
              uuid: user.uuid,
              name: user.name,
              email: user.email,
              secondaryEmail: user.secondaryEmail,
              phone: user.phone,
              address: user.address,
              city: user.city,
            }),
            {
              status: 200,
            }
          );
        } else {
          return new HttpResponse(null, {
            status: 204,
            statusText: "No Content: no matching token",
          });
        }
      } catch (error) {
        return new HttpResponse(null, {
          status: 500,
          statusText: "Internal Server Error",
        });
      }
    }
  ),
];
