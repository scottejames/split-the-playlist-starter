<p align="center">
  <img src="logo.svg" alt="Split the Playlist" width="760">
</p>

# Split the Playlist — starter project

This one's a warm-up. A friend has handed you a playlist — a fixed
running order of songs, back to back — and asked for it split across a
few CDs, or a few DJ sets, or however many chunks they need. The only
rule: whatever you burn onto disc one has to be a run of *consecutive*
songs from the start, disc two picks up right where disc one left off,
and so on. No shuffling, no cherry-picking a favourite track out of
order.

Naturally, some discs are going to end up more packed than others. Your
friend just wants to know: however you split it up, what's the shortest
possible length you can get the *fullest* disc down to?

This repo is where you build that up, in Python or Java, whichever
you're happier in. Both are graded the same way, so pick on comfort, not
on which one you think looks better.

## The problem

You're given `songs` — a list of song durations, in the order they play
— and `d`, the number of sides (discs, sets, whatever you want to call
them) to split the playlist into. Every side must be a contiguous run of
one or more songs, the sides have to cover the whole playlist in order,
and you must end up with exactly `d` of them.

**Your job:**

```
min_longest_side(songs, d) -> int
```

Find the split into `d` sides that makes the longest side as short as
possible, and return that length. If there are more sides requested than
there are songs to fill them, that's not possible — return `-1`.

### Worked example

```
songs: 9, 1, 1, 1
d: 2
```

Splitting evenly by total duration might suggest something like 6 (12
total, divided by 2) — but that ignores that the first song alone is 9
minutes long, and it can't be cut in half. Whichever side it ends up on,
that side is at least 9. The best split is `[9]` and `[1, 1, 1]`: sides
of length 9 and 3.

- Answer → `9`

### Constraints

Nothing sneaky here — just the numbers to design around:

- `1 ≤ number of songs ≤ 200,000`
- `1 ≤ d ≤ 200,000`
- `1 ≤ duration of each song ≤ 10^9`

## Layout

```
split-the-playlist-starter/
  test_data/
    simple/    <- 8 tiny, hand-traceable playlists
    medium/    <- 5 bigger hand-designed cases, still traceable on paper
    hard/      <- 6 generated cases — too large to solve by hand
  python/
    split_playlist.py    <- implement your solution here
    test_data.py           loads cases from ../test_data
    main.py                 a small demo runner (prints one example)
    run_tests.py             the test harness — every case, PASS/FAIL, timing, an efficiency band
    scripts/
      compile.sh             syntax-checks the Python files
      run.sh                  runs main.py
      test.sh                 runs run_tests.py
  java/
    src/
      SplitPlaylist.java    <- implement your solution here
      TestData.java           loads cases from ../test_data
      Main.java                a small demo runner (prints one example)
      TestRunner.java           the test harness — every case, PASS/FAIL, timing, an efficiency band
    scripts/
      compile.sh             javac's everything into java/build
      run.sh                  compiles, then runs Main
      test.sh                 compiles, then runs TestRunner
```

You only need to touch `split_playlist.py` / `SplitPlaylist.java` —
everything else is scaffolding that's already wired up and ready to go:
the test data, the demo runner, the test harness, the shell scripts.

Each solution file has a couple of empty helper methods already sketched
in (the total length of the playlist, the longest individual song). Use
them, rename them, rip them out entirely — whatever gets you to a
solution you're happy with. They're there to save you some typing, not
to tell you how to think about the problem.

## Test data tiers

- **Simple** (`test_data/simple/`) — a handful of songs, small enough to
  check your basic splitting logic just by looking at it.
- **Medium** (`test_data/medium/`) — still small enough to trace on
  paper if you want to sanity-check an answer, but it takes real
  attention — a couple of cases are built specifically so that the
  obvious back-of-envelope shortcut (just divide the total by `d`) gives
  the wrong number.
- **Hard** (`test_data/hard/`) — nobody's tracing these by hand. Some
  have a huge spread between the shortest and longest song even though
  there are barely a dozen of them; others are just large, tens or
  hundreds of thousands of songs, to check your solution holds up at
  realistic scale. If a run hangs or drags on the `hard` tier, that's
  worth digging into — the playlist isn't broken, your approach probably
  needs a rethink.

## Quick start

Python (needs Python 3.8+, no other dependencies):

```bash
cd python
./scripts/test.sh     # run the test suite
./scripts/run.sh       # run the demo on one example case
```

Java (needs a JDK on your PATH, no build tool required):

```bash
cd java
./scripts/test.sh     # compiles, then runs the test suite
./scripts/run.sh       # compiles, then runs the demo on one example case
```

## Definition of done

`./scripts/test.sh` should print `TOTAL: 19 passed, 0 failed` in both
languages, ending with `Efficiency band: Efficient (< 2s total)`. Right
now every test fails with `NOT IMPLEMENTED` — that's your starting line,
not a bug.

That last line is reading the `hard` tier's total time: `Efficient` under
2 seconds, `Adequate` up to 10, `Slow` beyond that. A correct, reasonably
efficient solution should land comfortably in `Efficient`. If you're
seeing `Adequate` or `Slow`, or the hard tier just never finishes, take
that seriously — it's telling you something real about your approach, not
just filling space at the bottom of the output.

This one's meant to be a confidence builder — a clean pass here is a good
sign heading into the rest of the assessment. Good luck.
