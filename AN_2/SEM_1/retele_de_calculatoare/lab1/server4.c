#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>

#define BUFFER_SIZE 256

void interclasare(char *sir1, char *sir2, char *final)
{
    int i = 0, j = 0, k = 0;
    while (sir1[i] != '\0' && sir2[j] != '\0') 
    {
        final[k++] = sir1[i++];
        final[k++] = sir2[j++];
    }
    while (sir1[i] != '\0') 
    {
        final[k++] = sir1[i++];
    }
    while (sir2[j] != '\0') {
        final[k++] = sir2[j++];
    }
    final[k] = '\0';
}

int main() {
    int s, c;
    struct sockaddr_in server, client;
    socklen_t l = sizeof(client);
    char sir1[BUFFER_SIZE], sir2[BUFFER_SIZE];
    char final[BUFFER_SIZE*2];

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

	interclasare(sir1,sir2,final);

        send(c, final, sizeof(final), 0);
        close(c);
    }
    return 0;
}
