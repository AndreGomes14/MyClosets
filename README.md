# MyClosets
MyCloset is a full back-end application designed for football enthusiasts who want to organize and display their collection of retro football jerseys. With MyCloset, you can easily catalog your jerseys by team, era, or any other criteria you choose. You can add detailed information about each jersey, such as the team, player, and year, and even attach memories or special significance to them. MyCloset includes features like personalized tagging and search options, making it simple to manage and find specific jerseys within your collection. It also has some specific statistics and the user can create his "Wish Closet" and add the jerseys he wants to buy in the future!

To try this app you will need to run it and test the endpoints on *Postman*.

Here is the full list of features:

User-> 
  Create User; /user/createUser
  Get all users; /user/allUsers
  Get user by id; /user/getUserByID
  delete user by id; /user/deleteUser
  addClosetToUser; /user/addClosetToUser
  getUserStatisticsByID; /user/getUserStatisticsByID

Picture->
  Upload picture; /picture/upload
  Get Pictures by Jersey id; /picture/getPicturesByJerseyID
  Get picture by id; /picture/getPictureByID
  Delete picture by id; /picture/deleteImageByID

Jersey->
  Create Jersey; /jersey/createJersey
  Get all jerseys; /jersey/getAllJerseys
  Get Jersey by id; /jersey/getJerseyByID
  Delete Jersey; /jersey/deleteJersey

Closet->
  Create Closet; /closet/createCloset
  Get all Closets; /closet/getAllClosets
  Get Closet by User id; /closet/getClosetByUserID
  Delete Closet; /closet/deleteCloset 
  Add Jerseys to Closet; /closet/addJerseysToCloset
  Get all jerseys by Closet id; /closet/getAllJerseysByClosetID
  
WishJersey->
  Create Wish Jersey; /wishJersey/createWishJersey
  Get All Wish Jerseys; /wishJersey/getAllJerseys
  Get WishJersey by id; /wishJersey/getJerseyByID
  Delete WishJersey; /wishJersey/deleteWishJersey

WishCloset->
  Create WishCloset; /wishCloset/createWishCloset
  Get All Closets; /wishCloset/getAllClosets
  Get WishCloset by User id; /wishCloset/getWishClosetByUserID
  Delete WishCloset; /wishCloset/deleteWishCloset
  Add Jerseys to WishCloset; /wishCloset/addJerseysToWishCloset
  Get All Jerseys by Closet id; /wishCloset/getAllJerseysByClosetID

The application is covered with over 70% of Unit Tests (using JUnit 5 and Mockito)!
Deployment using Docket, minikube and Kubernetes!

The next step for this application is to develop a user-friendly front-end, providing easy access to the application for regular users and ensuring a seamless interaction experience.
