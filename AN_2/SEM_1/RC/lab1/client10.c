#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>

#define BUFFER_SIZE 256

int main() {
    int c;
    struct sockaddr_in server;
    char sir1[BUFFER_SIZE], sir2[BUFFER_SIZE];
    char caracter_frecvent;
    int frecv;

    c = socket(AF_INET, SOCK_STREAM, 0);
    if (c < 0) {
        printf("Eroare la crearea socket-ului");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_port = htons(1234); 
    server.sin_addr.s_addr = inet_addr("127.0.0.1"); 

    if (connect(c, (struct sockaddr *)&server, sizeof(server)) < 0) {
        printf("Eroare la conectarea la server");
        return 1;
    }

    printf("Introduceti primul sir de caractere: ");
    fgets(sir1, 256, stdin);

    printf("Introduceti al doilea sir de caractere: ");                             
    fgets(sir2, 256, stdin);

    send(c, sir1, strlen(sir1) + 1, 0);
    send(c, sir2, strlen(sir2) + 1, 0);

    recv(c, &caracter_frecvent, sizeof(caracter_frecvent), 0);
    recv(c, &frecv, sizeof(frecv), 0);

    printf("Caracterul cu cele mai multe aparitii pe pozitii identice este: '%c' (%d aparitii)\n", caracter_frecvent, frecv);
    close(c);
    return 0;
}
