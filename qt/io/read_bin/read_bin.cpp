#include <QFile>
#include <QIODevice>
#include <QDataStream>

static void testMapDataRead(const char * fileName) {
    QFile file(fileName);
    file.open(QIODevice::ReadOnly);
    QDataStream in(&file);

    unsigned size = 1024;
    char * buffer = new char[size];
    printf("testMapDataRead, buffer: %p\n", buffer);

    int r = in.readRawData(buffer, size);
    printf("testMapDataRead, read bytes: %d\n", r);
    printf("testMapDataRead, buffer: %p\n", buffer);
    printf("testMapDataRead, buffer size: %d\n", size);
    file.close();

    if (buffer) {
        printf("testMapDataRead, buffer head 2 byte:%d %d\n", buffer[0], buffer[1]);
        delete[] buffer;
    }
}

int main(int argc, char ** argv) {
    if (argc < 2){
        printf ("Usage: %s <file_name>\n", argv[0]);
        return -1;
    }   

    char * fname = argv[1];
    testMapDataRead (fname);
}
