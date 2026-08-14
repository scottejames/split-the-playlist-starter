"""Demo runner — runs min_longest_side on one example case and prints the result."""

import os

from split_playlist import min_longest_side
from test_data import TEST_DATA_DIR, load_case


def main() -> None:
    path = os.path.join(TEST_DATA_DIR, "medium", "01_trap_at_scale.txt")
    case = load_case(path)

    print(f"Case: {case.name}")
    print(f"Songs: {case.songs}")
    print(f"Sides (d): {case.d}")

    result = min_longest_side(case.songs, case.d)
    print(f"Minimum possible longest side: {result}")


if __name__ == "__main__":
    main()
