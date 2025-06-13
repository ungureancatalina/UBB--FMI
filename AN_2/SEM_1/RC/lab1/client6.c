#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

int main() {
    int c;
    struct sockaddr_in server;
    char sir[256],caracter;
    uint16_t pozitii[100],count;

    c = socket(AF_INET, SOCK_STREAM, 0);
    if (c < 0) {
        printf("Eroare la crearea socketului client\n");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_port = htons(1234);  
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr("127.0.0.1");

    if (connect(c, (struct sockaddr *)&server, sizeof(server)) < 0) {
        printf("Eroare la conectarea la server\n");
        return 1;
    }

    printf("Introduceti sirul de caractere: ");
    fgets(sir, 256, stdin);
    send(c, sir, strlen(sir) + 1, 0); 

    printf("Introduceti caracterul cautat: ");
    scanf(" %c", &caracter);
    send(c, &caracter, sizeof(caracter), 0);

    recv(c, &count, sizeof(count), 0);
    count = ntohs(count);

    recv(c, pozitii, sizeof(uint16_t) * count,0);

     if (count > 0) {
        printf("Caracterul '%c' apare pe pozitiile: ", caracter);
        for (int i = 0; i < count; i++) {
            printf("%hu ", ntohs(pozitii[i]));
        }
        printf("\n");
    } else {
        printf("Caracterul '%c' nu a fost gasit in sir.\n", caracter);
    }

    close(c);
    return 0;
}
