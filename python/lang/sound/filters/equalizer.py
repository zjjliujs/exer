print("module: {}".format(__name__))

import sound.formats.wavread as wr

wav = wr.Wav()
wav.read()
