# Day 2 - 2nd Nov 2016

Got a lot done today.
Setup Tracery which was suggest in some thread for NaNoGenMo.
I can see how it could be very powerful and allow the generation of novels.
I wonder if that one Space Cadet novel from last(?) year used it.

I also started to use thesaurus-com might switch to moby instead since someone mentioned that.
It is actually really nice with Tracery.
It allows the use of a key emotion like "sadness" to be switched out.
There are of course bad combos.
For example I got the synonyms of "desire" and "move" to use in the following way.
"There was a #desire# to #move#"
Here are some bad examples.
- "There was a lechery to variation"
- "There was a pine to submit"
- "There was a like to turn"
I will have to brain storm later on how to handle cases that are similar to move but I don't want them included.

It has been a while since I used ClojureScript but I think it overall will be the correct choice.
Ran into issues because I forgot the property operator (.-prop) and was calling JS in such a way that `this` was incorrect.

There are some other things I want to do like getting more involved in the community for NaNoGenMo and research that I have not yet dove into yet.
I might spend sometime at the end of the day doing that.

Tomorrow I am thinking of playing with STRIPS and Tracery together.
A general idea is that a STRIPS action can map to a Tracery sentence(s) with the correct nouns setup.
It might be a better idea to setup tools in CLJS to deal with dynamically creating STRIPS and Tracery objects for use.
