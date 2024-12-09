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
  uint16_t count, number, add;
  
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
  
  printf("Cate numere vor fi adunate?");
  scanf("%hu", &count);

  uint16_t net_count = htons(count);
  send(c, &net_count, sizeof(net_count), 0);

  for(int i=0;i<count;i++)
	{printf("Numarul %d: ", i+1);
	scanf("%hu", &number);
	number=htons(number);
	send(c,&number,sizeof(number),0);
	}

  recv(c, &add, sizeof(add), 0);
  add = ntohs(add);
  printf("Suma este %hu\n", add);
  close(c);
}
