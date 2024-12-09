#include <unistd.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
 
int main() {
  int c;
  struct sockaddr_in server;
  uint16_t count, number, divizori[100];
  
  c = socket(AF_INET, SOCK_STREAM, 0);
  if (c < 0) {
    printf("Eroare la crearea socketului client\n");
    return 1;
  }
  
  memset(&server, 0, sizeof(server));
  server.sin_port = htons(1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = inet_addr("127.0.0.1");
  
  if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {
    printf("Eroare la conectarea la server\n");
    return 1;
  }
  
  printf("Introduceti un numar");
  scanf("%hu", &number);

  uint16_t net_number = htons(number);
  send(c, &net_number, sizeof(net_number), 0);

  recv(c, &count, sizeof(count), 0);
  count = ntohs(count);

  recv(c,divizori,sizeof(uint16_t)*count,0);

  printf("Divizorii numarului %hu sunt: \n ", number);
    for (int i = 0; i < count; i++) {
        printf("%hu ", ntohs(divizori[i]));
    }
    printf("\n");
  close(c);
}
