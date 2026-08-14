# Test data

Every case for Split the Playlist lives here as a plain text file,
grouped into three difficulty tiers. Both `python/run_tests.py` and
`java/src/TestRunner.java` load directly from this directory — nothing is
duplicated in code.

## File format

```
name=<case name>
d=<int>
expected=<int>
songs=
<duration 0>
<duration 1>
...
```

Everything from the line after `songs=` to the end of the file is the
song list, one duration per line. Filenames are numbered (`01_...`,
`02_...`) purely so both loaders sort them into a stable, predictable
order when they list a directory — the number carries no other meaning.

`expected` was never computed by hand — it comes from a working reference
implementation kept outside this repository, checked once and then
treated as ground truth. Take it as correct.

## Simple — `simple/`

Tiny playlists. Each one isolates a single mechanic rather than combining
several, so a failure here points at a specific piece of missing logic
rather than "something is wrong somewhere."

| Case | Songs | d | Expected | Tests |
|---|---|---|---|---|
| `01_whole_sum_one_side` | `3, 1, 4` | 1 | 8 | Only one side, so it has to hold everything. The answer is just the total. |
| `02_one_song_per_side` | `3, 1, 4` | 3 | 4 | As many sides as songs, so every side holds exactly one song. The answer is the longest individual song. |
| `03_more_sides_than_songs` | `3, 1` | 5 | -1 | More sides requested than there are songs to fill them. Every side must be non-empty, so this is impossible. |
| `04_trap_dominant_song` | `9, 1, 1, 1` | 2 | 9 | **The first trap.** A solution that just divides the total (12) by the number of sides (2) and rounds up gets 6 — but no side can ever be smaller than the longest individual song (9), since that song can't be split across two sides. |
| `05_single_song` | `7` | 1 | 7 | One song, one side — the simplest possible case. |
| `06_two_equal_songs` | `5, 5` | 2 | 5 | A clean, even split with nothing to balance. |
| `07_already_balanced` | `2, 2, 2, 2` | 2 | 4 | Checks the boundary between "2 pairs" and other splits comes out right when everything is already equal. |
| `08_spike_in_the_middle` | `1, 1, 1, 100, 1, 1, 1` | 3 | 100 | Same idea as the first trap, more extreme: dividing the total (106) by 3 suggests something around 36, but the single 100-duration song forces the answer up to 100 regardless of how the rest is arranged. |

## Medium — `medium/`

Still small enough to work out with pencil and paper, but big enough that
the interesting behaviour is a genuine decision, not a one-glance
inspection.

| Case | Songs | d | Expected | Tests |
|---|---|---|---|---|
| `01_trap_at_scale` | `10, 2, 3, 4, 5, 2, 1` | 3 | 10 | The same "divide the total and round up" trap as the simple tier, at a size where it's no longer obvious by inspection that the shortcut is wrong. |
| `02_tight_boundary` | nine `4`s | 3 | 12 | All songs identical. Checks that grouping comes out exactly right at the edges — off-by-one errors in how songs get bucketed into sides tend to show up here first. |
| `03_single_outlier` | `5, 5, 5, 20, 5, 5, 5` | 3 | 20 | One long song in the middle of otherwise uniform ones. However the other six get grouped around it, no side containing the 20 can end up smaller than 20. |
| `04_order_matters` | `1, 50, 1, 1, 1, 50, 1` | 2 | 53 | **The second trap.** A solution that sorts the songs before deciding how to split them will get a different, wrong answer — sides have to be unbroken runs of the *original* order, not any convenient regrouping of the durations. |
| `05_uniform_songs` | nine `4`s | 4 | 12 | Every song the same length again, but with a song count that doesn't divide evenly by the number of sides. Dividing the total by `d` suggests 9; the real constraint (whole songs, contiguous groups) pushes it up to 12. |

## Hard — `hard/`

Two different kinds of case, both too large to work out by hand.

**Wide-range cases (`01`, `02`, `06`).** Only a handful of songs, but
their durations are spread across a huge range — so the gap between the
smallest and largest plausible answer is enormous, even though there's
almost nothing to look at. An approach that checks every possible answer
one at a time, in order, pays for that whole gap. An approach that
narrows the range down instead barely notices it.

**Large-scale cases (`03`–`05`).** Tens or hundreds of thousands of
songs. Mostly a straightforward performance and correctness check at
realistic scale — nothing adversarial about the shape of the data, just
a lot of it. One of these (`05`) is sized so the total duration of every
song added together is too large to fit in a standard 32-bit integer —
worth keeping in mind for how you store and compare durations, especially
in Java.

| Case | Songs | Expected | Tests |
|---|---|---|---|
| `01_wide_range_n12` | 12 | 20,698,104 | A dozen songs spanning single digits of millions to tens of millions. Smallest of the wide-range cases. |
| `02_wide_range_n20` | 20 | 21,407,379 | A wider spread of songs, similar range. |
| `03_large_n_50000` | 50,000 | 53,364 | Realistic scale, nothing adversarial — mostly a performance check. |
| `04_large_n_150000` | 150,000 | 53,242 | Roughly triple the scale of `03`. |
| `05_overflow_n200000` | 200,000 | 225,422 | The total duration of every song together exceeds what fits in a 32-bit integer, even though the *answer* itself doesn't. Checks that intermediate totals are computed with enough headroom, not just the final result. |
| `06_wide_range_n8` | 8 | 777,391,922 | The most extreme wide-range case in the suite — only 8 songs, but spanning hundreds of millions in duration. |
