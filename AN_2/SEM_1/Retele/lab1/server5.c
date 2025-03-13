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
        uint16_t count, number, divizori[100];
        c = accept(s, (struct sockaddr *)&client, &l);
        printf("S-a conectat un client.\n");

        recv(c, &number, sizeof(number), MSG_WAITALL);
        number = ntohs(number);

	count=0;
        for (int i = 1; i <= number; i++) {
            if(number%i==0)
		divizori[count++]=htons(i);
        }
        uint16_t net_count = htons(count);
        send(c, &net_count, sizeof(net_count), 0);

	send(c,divizori,sizeof(uint16_t)*count,0);
        close(c);
    }
    return 0;
}
