from enum_structure import Enum

from declare import IMPORTED_CONSTANT, ImportedEnumConfig

print(IMPORTED_CONSTANT)
print(ImportedEnumConfig.ENUM_CONSTANT)

UPPER_CASE_CONSTANT = 42
print(UPPER_CASE_CONSTANT)

lower_case_constant = 2
print(lower_case_constant)


class EnumConfig(Enum):
    ENUM_CONSTANT = "Enum value"


print(EnumConfig.ENUM_CONSTANT)


class EnumDefault(Enum):
    enum_lower_case_constant = 6
    ENUM_UPPER_CASE_CONSTANT = 5
    ENUM_STRING_VALUE_CONSTANT = "Value"


print(EnumDefault.ENUM_UPPER_CASE_CONSTANT)
print(EnumDefault.enum_lower_case_constant)
print(EnumDefault.ENUM_STRING_VALUE_CONSTANT)

