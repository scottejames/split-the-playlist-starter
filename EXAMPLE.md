# Worked example, step by step

This walks through the problem by hand, using two real examples (the
same cases as `test_data/simple/08_spike_in_the_middle.txt` and
`test_data/medium/04_order_matters.txt`, so you can cross-check both
final answers against those files). The goal here is to make sure the
*rules* of the problem are completely clear before you write any code —
it deliberately stops short of showing you an efficient way to search
for the answer on a large playlist. See [README.md](README.md) for the
full problem statement and constraints.

## The setup

```
songs: 1, 1, 1, 100, 1, 1, 1
d: 3
```

A **split** cuts the playlist into exactly `d` sides, each one a run of
*consecutive* songs in the original order — no reordering, no skipping
around, no empty sides. We want the split whose fullest (longest) side
is as short as possible.

## Stage 1 — try the obvious shortcut, and see it fail

A natural first guess: add everything up and divide by the number of
sides.

```
total = 1+1+1+100+1+1+1 = 106
106 / 3 ≈ 35.3
```

So maybe the answer is somewhere around 36? But look at the fourth song:
it's 100 minutes long, all by itself. Whichever side that song ends up
on, that side's length is *at least* 100 — a single song can never be
split across two sides. The "divide the total" shortcut ignores that
entirely, and it's not a small error here: it's off by more than 2×.

## Stage 2 — the dominant song sets a floor

Since the 100-length song can't be shrunk or divided, no split can ever
do better than 100 for its longest side. That's a hard floor, regardless
of how the other six songs (all length 1) get arranged around it.

## Stage 3 — check that floor is actually reachable

Is there a split that hits exactly 100, and no more? Group the songs on
either side of the big one onto their own sides:

```
[1, 1, 1] | [100] | [1, 1, 1]
```

Three sides, covering all seven songs in order, nothing skipped. Side
lengths: `3`, `100`, `3`. The longest side is `100` — matching the floor
from Stage 2 exactly. No split can beat this, and this split reaches it,
so it's optimal.

**Answer: `min_longest_side([1,1,1,100,1,1,1], 3)` = `100`.**

---

## A second wrinkle: order can't be rearranged, even to get a better answer

Different example:

```
songs: 1, 50, 1, 1, 1, 50, 1
d: 2
```

The two big songs (both 50) are far apart in the list. If sides could be
*any* grouping of songs, you'd want to pair the two 50s together to keep
the other side small — but sides must be contiguous runs of the
*original* order, so that's not on the table. The only choices are
*where to make the cut* between side one and side two.

There are 6 possible places to cut a 7-song list into 2 sides. Try each
one and total up both sides:

| Cut after song # | Side 1 | Side 1 total | Side 2 | Side 2 total | Longest side |
|---|---|---|---|---|---|
| 1 | `1` | 1 | `50,1,1,1,50,1` | 104 | 104 |
| 2 | `1,50` | 51 | `1,1,1,50,1` | 54 | 54 |
| 3 | `1,50,1` | 52 | `1,1,50,1` | 53 | **53** |
| 4 | `1,50,1,1` | 53 | `1,50,1` | 52 | **53** |
| 5 | `1,50,1,1,1` | 54 | `50,1` | 51 | 54 |
| 6 | `1,50,1,1,1,50` | 104 | `1` | 1 | 104 |

The smallest value in the "longest side" column is `53`, reached by
cutting after the third or fourth song. Notice it's *not* achieved by
trying to balance the two 50s against each other — that would require
reordering, which isn't allowed. It comes purely from choosing the best
of the six legal cut points.

**Answer: `min_longest_side([1,50,1,1,1,50,1], 2)` = `53`.**
