from importlib import import_module

__all__=["a", "b"]

Configs={}

def merge_configs():
    for s in __all__:
        m = import_module("."+s, package="pkg")
        for k, v in m.Config.items():
            Configs[k] = v

merge_configs()

del merge_configs
del import_module
