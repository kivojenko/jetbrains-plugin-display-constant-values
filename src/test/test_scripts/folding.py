from enum import Enum

from declare import IMPORTED_CONSTANT, ImportedConfig

print(IMPORTED_CONSTANT)
print(ImportedConfig.ENUM_CONSTANT)

CONSTANT = "Hello, World!"
print(CONSTANT)


class Config(Enum):
    ENUM_CONSTANT = "Test"


print(Config.ENUM_CONSTANT)
