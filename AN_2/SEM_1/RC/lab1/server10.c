#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>

#define BUFFER_SIZE 256

int main() {
    int s, c;
    struct sockaddr_in server, client;
    socklen_t l = sizeof(client);
    char sir1[BUFFER_SIZE], sir2[BUFFER_SIZE];
    int frecv = 0;
    char caracter_frecvent = '\0';

    s = socket(AF_INET, SOCK_STREAM, 0);
    if (s < 0) {
        printf("Eroare la crearea socket-ului");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY; 
    server.sin_port = htons(1234);

    if (bind(s, (struct sockaddr *)&server, sizeof(server)) < 0) {
        printf("Eroare la bind");
        return 1;
    }

    listen(s, 5);

    while (1) {
        c = accept(s, (struct sockaddr *)&client, &l);
        printf("S-a conectat un client.\n");

        recv(c, sir1, sizeof(sir1), 0);
        recv(c, sir2, sizeof(sir2), 0);

        int lung = strlen(sir1) < strlen(sir2) ? strlen(sir1) : strlen(sir2);
        int car[256] = {0}; 

        for (int i = 0; i < lung; i++) {
            if (sir1[i] == sir2[i]) {
                car[(unsigned char)sir1[i]]++;
                if (car[(unsigned char)sir1[i]] > frecv) {
                    frecv = car[(unsigned char)sir1[i]];
                    caracter_frecvent = sir1[i];
                }
            }
        }

        send(c, &caracter_frecvent, sizeof(caracter_frecvent), 0);
        send(c, &frecv, sizeof(frecv), 0);
        close(c);
    }
    return 0;
}
