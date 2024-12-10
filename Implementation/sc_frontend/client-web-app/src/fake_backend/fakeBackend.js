const fakeDatabase = [
  {
    id: 1,
    username: "user1",
    password: "password1",
    profile: {
      name: "NameOne",
      surname: "SurnameOne",
      email: "user1@example.com",
    },
  },
  {
    id: 2,
    username: "user2",
    password: "password2",
    profile: {
      name: "NameTwo",
      surname: "SurnameTwo",
      email: "user2@example.com",
    },
  },
];

//fakes the credential matching in the fakeDatabase
export const fakeLogin = (email, password) => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      const user = fakeDatabase.find(
        (u) => u.profile.email === email && u.password === password
      );
      console.log("sono il backend che sta facendo cose");
      if (user) resolve({ token: "fake-tonen", userId: user.id });
      else reject("Invalid Credentials");
    }, 1000); //one second of delay
  });
};

//fakes the user infos fetching
export const fakeFetchUserData = (userId) => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      const user = fakeDatabase.find((u) => u.id === userId);
      resolve(user ? user.profile : null);
    }, 500);
  });
};
