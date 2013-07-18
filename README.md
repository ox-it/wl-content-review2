wl-content-review2
==================

Content review tool using Turnitin to examine documents.

The most recent code is currently on the wip branch, there are some note on the WebLearn Wiki: https://wiki.it.ox.ac.uk/weblearn/TurnItIn.

This code requires the turnitin SDK which has to be got directly from Turnitin and currently can't be published publicly, although we could put it in a non-public local maven repository.

Currently there is no persistance for the implementation.
There's no Sakai component to put this in Sakai.
There's no configuration handling at the moment, but most of it will just be creating the TurnItIn API which gets injected everywhere else.

Queuing is half done, can push onto it but not pop off it.



