from dataclasses import dataclass
from functools import cached_property


class Test:
    def __init__(self, variable):
        self.variable = variable

    @property
    def property_value(self):
        return self.variable

    @cached_property
    def cashed_property_value(self):
        return self.variable

    def print(self):
        print(self.variable)
        print(self.property_value)
        print(self.cashed_property_value)


@dataclass
class TestDataclass:
    variable: str
    VARIABLE: str

    @property
    def property_value(self):
        return self.variable

    @cached_property
    def cashed_property_value(self):
        return self.variable

    def print(self):
        v = TestDataclass("a", "b")
        print(v.variable)
        print(v.VARIABLE)
        print(v.property_value)
        print(v.cashed_property_value)

        print(self.variable)
        print(self.VARIABLE)
        print(self.property_value)
        print(self.cashed_property_value)
