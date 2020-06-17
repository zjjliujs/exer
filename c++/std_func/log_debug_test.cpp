#include <stdio.h>
#include <string.h>

static void appendVector(char * buffer, float * value, unsigned int len) {
    char * p;
    p = buffer + strlen(buffer);
    snprintf(p, 1024 - strlen(buffer), "[");
    for (unsigned int i = 0; i < len; ++i) {
        p = buffer + strlen(buffer);
        snprintf(p, 1024 - strlen(buffer), "%.2f ", value[i]);
        if (i < len - 1) {
            p = buffer + strlen(buffer);
            sprintf(p, " ");
        }
    }
    p = buffer + strlen(buffer);
    sprintf(p, "]");
}

void log_debug(const char * msg
               , float * value
               , unsigned int len) {
    char buffer[1024];
    snprintf(buffer, 1024, "%s \n", msg);
    appendVector(buffer, value, len);
    printf ("%s\n", buffer);
}

int main(){
    float t[3] = {1,2,3};
    log_debug("test", t, 3);
}
