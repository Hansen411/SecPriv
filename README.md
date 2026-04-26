# SecPriv

## Part 1. messageDigest
The program demonstrates the use of hashing using MD5 and SHA (SHA-1 and SHA-256) schemes
and the MessageDigest class.

### messageDigest Output
<img width="836" height="202" alt="image" src="https://github.com/user-attachments/assets/209f91e4-ec83-4ae8-b3a9-94781784b6d4" />


## Part 2. Various Crypto Techniques
The objective of this exercise is to use Java features to write some security mechanisms that can be used in
applications

### A) Authentication
Using the skeleton Java code to implement double-strength password login using a message digest.

<ul>
  <li>
     <i>Protection</i>, which provides three functions makeBytes, makeDigest (version 1), and makeDigest
(version 2).
    <ul>
      <li>makeBytes takes in a long integer and a double, then converts them into a single byte array.
  makeBytes has already been implemented for you.</li>
      <li>makeDigest (version 1) takes in a byte array, a timestamp, and a random number, then
  generates a digest using SHA. This function has already been implemented for you</li>
      <li>makeDigest (version 2) takes in a user name, a password, a timestamp, and a random
  number, then generates a digest using SHA. You need to implement this function.</li>
      </ul>
  </li>
  <li>
    <i>ProtectedClient</i>, which implements the client. There are two functions:
      <ul>
        <li>main and sendAuthentication. main is the starting point of the client program and has
already been implemented for you. Make sure the host variable is set to the correct server
address (it is currently set to “localhost” – you can also use “hostname” in your system to
obtain the name of your machine)</li>
        <li>sendAuthentication is the function that you need to implement. It takes in user name,
password, and an output stream as the function inputs. In this function, you should
implement double-strength password authentication and send to the server by writing to
the variable ‘out’. </li>
      </ul>
  </li>
  <li>
    <i>ProtectedServer</i>, which implements the server. There are three functions: main, lookupPassword,
and authenticate
    <ul>
      <li> main is the starting point of the server program and has already been implemented for you.
It creates a server process that waits for an incoming connection. Once a connection is
established, authenticate is called to authenticate the user. If the user is successfully
authenticate,d your program should print out “Client logged in.</li>
      <li> lookupPassword, which simply returns the password of the user stored on the server.</li>
      <li> authenticate is the function which you need to implement to authenticate the user trying to
log in. </li>
    </ul>
  </li>
</ul>

### Authentication Code Output
<img width="600" height="108" alt="image" src="https://github.com/user-attachments/assets/3f94e7c7-9e00-4a6e-b4ac-b2cd40daa4c9" />
