# Ideas


## Planning
One idea is to model human emotions and actions.
So we have a sad human who has multiple ways of becoming happy.
One is giving flower to another.
We can then use a planning system (STRIPS) to plan out the events.
Once we have the events in a certain order we can generate sentences based off of those events.
```clojure
[{:name "Bob" :state :sad}, {:action :give :item :flowers :to "Jill"},  {:name "Bob" :state :happy}]
```

Turns into "Bob is sad. He gives flowers to Jill. Bob is happy."

There is more that could be added though random happenings (disater or other humans).

I could use https://github.com/primaryobjects/strips

## Function first, beauty second
The story should make sense first even though it might only be made of "subject verb object".
The novel can be made beautiful later

## Limited Random

If I go with some sort of planning algorithm there is a degree of randomness that will be needed.
The start states could be frozen(not generated) then I would be able to get similar stories each time.

## Incomplete information
My goal work is dark fantasy based.
I will have to study that meaning deeper but one thing in Dark Souls and Berserk is the sense of mystery.
For example in Dark Souls when you enter a fight you try different way of defeating the boss.
Sometimes you fail.
Maybe a Planner with an assumptive step (fire works against swamp beat) and then when the action is taken a rectification happens when it does not work and a new assumptive plan formed.

## Locational information
There was a paper that mentions many of these similar ideas (STRIPS plan to sentences) but it also modeled the world as a graph.
This allows a way to calculate travel between places.
It also allows multiple character interaction.

## Fantasy name generation
I better find some way of generating Fantasy names.
https://github.com/konsumer/randopeep.

## Word list
I need words from some location. I could scrap from Dark Souls I, II and III and Bloodborne.

## STRIPS to Tracery
Take STRIPS planning then use it to generate lists for Tracery and from there get a somewhat readable sentence.
This allows follows the previous idea of Function then Beauty.

## Simplification of Actions
There might be an opportunity for simplification of actions output from STRIPS.
For example our hero is search a town and houses in a town.
Instead of...
"Hero enters an old house. He does not find what he wants. He leaves the house. Hero enters an old house..."
we could do...
"Hero searched several house but didn't find anything."

## Asymmetrical stories
Stories might be more enjoyable if they are Asymmetrical.
It makes the story slightly unexpected.

## Issue with Strips
Strips is callback heavy.
Unfortunately this makes my code more complex.
I think I am bypass it by loading PEG directly with the grammar being defined in the code.
Then I think I can just call the strips.solve fn and everything should be happy.
It would be annoying to use the callbacks maybe core async can help

## STRIPS Actions

There should be a specific number of actions defined by the global strips domain.
Maybe the agents will happen upon different actions but we need to have all these actions defined.
Would be interesting to auto generate actions but maybe another time.
I have a few planned out.
"moveself"
"pickup"
"putdown"

These are fairly easy. I don't forsee much problem with them.

I would like to do some others though.

"equip"
"unequip"
"useitem"
"attack"

These require some knowledge of the effects on status and auto generation of actions.


## Agent Status
Each agent in the story needs some basic status
```clojure
{
  :name "Agent" ;; Simple enough to explain
  :location "Cave" ;; Also easy
  :items [] ;;A lit of items
  :emotions {} ;;More complex and will have to look into later
  :status {} ;;More complex
}
```
