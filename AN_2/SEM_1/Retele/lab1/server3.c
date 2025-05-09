#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>

int main() {
    int s, c;
    struct sockaddr_in server, client;
    socklen_t l;
    char sir[256];

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

    while (1)
        {
        c = accept(s, (struct sockaddr *)&client, &l);
        if (c < 0) {
            printf("Eroare la accept\n");
            continue;
        }
        printf("S-a conectat un client.\n");

        recv(c, sir, sizeof(sir), 0);

	int lungime=strlen(sir);
	char oglindit[256];
        for (int i = 0; i < lungime; i++) 
	{
            oglindit[i]=sir[lungime-1-i];
        }

        oglindit[lungime]= '\0';
        send(c, oglindit, sizeof(oglindit), 0);

        close(c);
    }
    return 0;
}
