# ChatApp

OBJECTIVE.
Build a simple chat application using UDP sockets in Java.

DESCRIPTION.
In this application, clients will join a chat interface through a server. Once the 
client has joined the server, they will be able to send messages to the channel, 
public to all other clients connected to the server. Clients also have the option of 
leaving the server at any time. At the center of the chat application, the server is 
responsible for listening to and handling messages from clients.

IMPLEMENTATION.
To meet the objective of this project, I tackle three milestones. I first allow the 
client application to send a simple message to the server using a UDP socket. Following 
that, I define a message format so that the client can send different types of messages, 
and then I update the server to handle each message type. Once these two milestones are 
met, I enhance the server to contain broadcast functionality, so that it posts messages 
to the screens of all connected clients. Each client instance uses multi-threading to 
listen for messages from the server.

CONCLUSION.
Through this project, I learned how UDP sockets can be used to provide a connection 
between a server and its clients. By converting simple and complex messages into byte 
arrays, we can send them as packets through a socket, allowing another application 
connected to the same socket to receive and eventually decode that message. I also 
learned how multi-threading can be utilized to allow applications to both send and receive 
messages through a socket. This was very helpful in providing broadcasting functionality 
for the designed chat interface. Overall, I gained a better understanding of how different 
applications can connect in the transport layer. 
