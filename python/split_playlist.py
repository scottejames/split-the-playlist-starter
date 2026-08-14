"""
Split the Playlist.

Implement min_longest_side below. See ../README.md for the full problem
statement, constraints, and worked examples.

The helper functions are optional scaffolding — use them, change their
signatures, or delete them and structure your solution however you like.
"""

from typing import List


def total_duration(songs: List[int]) -> int:
    """Return the combined duration of every song."""
    # TODO: implement
    raise NotImplementedError


def longest_single_song(songs: List[int]) -> int:
    """Return the duration of the longest individual song."""
    # TODO: implement
    raise NotImplementedError


def min_longest_side(songs: List[int], d: int) -> int:
    """
    Split songs into exactly d contiguous, non-empty groups (in their
    original order — songs can't be reordered or split apart), so as to
    minimize the largest total duration among the d groups.

    Return that minimum possible "longest side" duration, or -1 if there
    are more sides than songs.
    """
    # TODO: implement
    raise NotImplementedError
