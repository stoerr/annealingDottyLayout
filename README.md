Graph layout with simulated annealing as a processor for Dotty files (for GraphViz)
===================================================================================

This is a processor for Dotty files that uses the simulated annealing algorithm
http://en.wikipedia.org/wiki/Simulated_annealing
to layout a graph given as a dotty file that can then be rendered with GraphViz
http://en.wikipedia.org/wiki/Graphviz

Reason: Graphviz does an excellent job rendering many - especially hierarchical - graphs.
If the graph is large, however, it often happens that the rendering is extremely wide
and thus not printable. This rendering algorithm yields a graph that uses the space
of a page much better by placing the nodes evenly spaced on a grid and just choosing the
locations of the nodes on the grid such that the edge length is minimized.

The basic idea is that we put the nodes randomly on a rectangular grid that is
a little bit larger than the graph itself. Then we optimize the layout such that
the cumulative length of all edges is minimized by swapping grid points. (That can be
two nodes of the graph, or a node of the graph and an empty place in the grid.)

In each step two random points of the grid are chosen. We check whether swapping
them reduces the length of the edges. If so we do swap them. If not - and that's
where the simulated annealing comes in - we do still swap them with a probability
that is large in the beginning but becomes smaller and smaller over time. This
reduces the probability that we are stuck in a local minimum where swapping of
only two points does not reduce the edge length, but where there are much better
solutions if we move more stuff around.


Status: it works, but the graphs it produces are not looking too usable. :-(
Still, it is an interesting study what can be done with scala with <100 LOC.