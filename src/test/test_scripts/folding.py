from enum import Enum

from declare import IMPORTED_CONSTANT, ImportedEnumConfig

print(IMPORTED_CONSTANT)
print(ImportedEnumConfig.ENUM_CONSTANT)

CONSTANT = "42"
print(CONSTANT)


class EnumConfig(Enum):
    ENUM_CONSTANT = "Enum value"


print(EnumConfig.ENUM_CONSTANT)