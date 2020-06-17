#include <QDebug>

#include "counter.h"

int main(int argc, char ** argv) {
    Counter a, b;
    QObject::connect(&a, &Counter::valueChanged,
                     &b, &Counter::setValue);
    /*这里，如果不判断value是否改变，是会无限循环的
    QObject::connect(&b, &Counter::valueChanged,
                     &a, &Counter::setValue);
    */
    qDebug("counter a: %d, b: %d", a.value(), b.value());
    a.setValue(12);     // a.value() == 12, b.value() == 12
    qDebug("counter a: %d, b: %d, after a set to 12", a.value(), b.value());
    b.setValue(48);     // a.value() == 12, b.value() == 48
    qDebug("counter a: %d, b: %d, after b set to 48", a.value(), b.value());
}
