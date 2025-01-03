from enum import Enum, IntEnum, StrEnum


class EnumClass(Enum):
    A = 1
    B = "2 - value"
    c = 3
    d = "4 - value"


print(EnumClass.A)
print(EnumClass.B)
print(EnumClass.c)
print(EnumClass.d)

class IntEnumClass(IntEnum):
    A = 1
    B = "2 - value"
    c = 3
    d = "4 - value"

print(IntEnumClass.A)
print(IntEnumClass.B)
print(IntEnumClass.c)
print(IntEnumClass.d)


class StringEnumClass(StrEnum):
    A = 1
    B = "2 - value"
    c = 3
    d = "4 - value"

print(StringEnumClass.A)
print(StringEnumClass.B)
print(StringEnumClass.c)
print(StringEnumClass.d)