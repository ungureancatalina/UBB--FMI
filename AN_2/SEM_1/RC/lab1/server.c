#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>

int main() {
    int s;
    struct sockaddr_in server, client;
    int c;
    socklen_t l;

    s = socket(AF_INET, SOCK_STREAM, 0);
    if (s < 0) {
        printf("Eroare la crearea socketului server\n");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;

    if (bind(s, (struct sockaddr *)&server, sizeof(server)) < 0) {
        printf("Eroare la bind\n");
        return 1;
    }

    listen(s, 5);
    
    l = sizeof(client);
    memset(&client, 0, sizeof(client));

    while (1) {
        uint16_t count, number, add = 0;
        c = accept(s, (struct sockaddr *)&client, &l);
        printf("S-a conectat un client.\n");

        recv(c, &count, sizeof(count), MSG_WAITALL);
        count = ntohs(count);

        for (int i = 0; i < count; i++) {
            recv(c, &number, sizeof(number), MSG_WAITALL);
            number = ntohs(number);
            add += number;
        }
        add = htons(add);
        send(c, &add, sizeof(add), 0);
        close(c);
    }
    return 0;
}
