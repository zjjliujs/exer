#include <QObject>

#include "counter.h"

Counter::Counter() {
    m_value = 0;
}

int Counter::value() const {
    return m_value;
}


void Counter::setValue(int value) {
    if (value != m_value) {
        m_value = value;
        emit valueChanged(value);
    }
}

