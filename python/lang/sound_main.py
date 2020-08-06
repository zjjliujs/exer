# -*- coding: UTF-8 -*-
import sound

print ("sound package name:{}".format(sound.__name__))

from sound import formats

print ("formats package name:{}".format(formats.__name__))

from sound.formats import *

wav = formats.wavread.Wav()
wav.read()

from sound.effects import *

from sound.filters import *
