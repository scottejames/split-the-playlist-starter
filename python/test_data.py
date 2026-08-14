"""
Loads Split the Playlist test cases from ../test_data/{simple,medium,hard}/*.txt.

File format:

    name=<case name>
    d=<int>
    expected=<int>
    songs=
    <duration 0>
    <duration 1>
    ...
"""

import os
from dataclasses import dataclass
from typing import List

TEST_DATA_DIR = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "test_data")


@dataclass
class TestCase:
    name: str
    songs: List[int]
    d: int
    expected: int


def load_case(path: str) -> TestCase:
    with open(path) as f:
        lines = f.read().splitlines()

    name = None
    d = None
    expected = None
    songs_start = None

    for i, line in enumerate(lines):
        if line == "songs=":
            songs_start = i + 1
            break
        key, _, value = line.partition("=")
        if key == "name":
            name = value
        elif key == "d":
            d = int(value)
        elif key == "expected":
            expected = int(value)

    songs = [int(line) for line in lines[songs_start:]]
    return TestCase(name=name, songs=songs, d=d, expected=expected)


def load_tier(tier: str) -> List[TestCase]:
    tier_dir = os.path.join(TEST_DATA_DIR, tier)
    cases = []
    for filename in sorted(os.listdir(tier_dir)):
        if filename.endswith(".txt"):
            cases.append(load_case(os.path.join(tier_dir, filename)))
    return cases
