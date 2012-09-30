Graph layout with simulated annealing as a processor for dotty files
====================================================================

(In Progress - the layout works but no dotty file reading yet.)

This is a processor for dotty files that uses the simulated annealing algorithm
http://en.wikipedia.org/wiki/Simulated_annealing
to layout a graph given as a dotty file that can then be rendered with GraphViz
http://en.wikipedia.org/wiki/Graphviz

The basic idea is that we put the points randomly on a rectangular grid that is
a little bit larger than the graph itself. Then we optimize the layout such that
the cumulative length of all edges is minimized by swapping points. (That can be
two points of the graph, or a point of the graph and an empty place in the grid.)

In each step two random points of the grid are chosen. We check whether swapping
them reduces the length of the edges. If so we do swap them. If not - and that's
where the simulated annealing comes in - we do still swap them with a probability
that is large in the beginning but becomes smaller and smaller over time. This
reduces the probability that we are stuck in a local minimum where swapping of
only two points does not reduce the edge length, but where there are much better
solutions if we move more stuff around.

