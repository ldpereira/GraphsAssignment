/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphsassignment.model;

/**
 *
 * @author Lucas Pereira e Renan Bertoldo
 */
public class Edge implements Comparable<Edge> {

    private Vertex initialVertex;
    private Vertex finalVertex;
    private int weight;

    public Edge(Vertex one, Vertex two) {
        this(one, two, 1);
    }

    public Edge(Vertex initialVertex, Vertex finalVertex, int weight) {
        this.initialVertex = (initialVertex.getName().compareTo(finalVertex.getName()) <= 0) 
                ? initialVertex : finalVertex;
        this.finalVertex = (this.initialVertex == initialVertex) 
                ? finalVertex : initialVertex;
        this.weight = weight;
    }

    public Vertex getNeighbor(Vertex current) {
        if (!(current.equals(initialVertex) || current.equals(finalVertex))) {
            return null;
        }
        return (current.equals(initialVertex)) ? finalVertex : initialVertex;
    }

    public Vertex getInitialVertex() {
        return initialVertex;
    }

    public Vertex getFinalVertex() {
        return finalVertex;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge compareEdge) {
        return weight - compareEdge.weight;
    }

    @Override
    public String toString() {
        return "({" + initialVertex + ", " + finalVertex + "}, " + weight + ")";
    }

    @Override
    public int hashCode() {
        return (initialVertex.getName() + finalVertex.getName()).hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Edge)) {
            return false;
        }
        Edge e = (Edge) other;
        return e.initialVertex.equals(initialVertex) && e.finalVertex.equals(finalVertex);
    }
}
