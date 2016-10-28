/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphsassignment.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Lucas Pereira e Renan Bertoldo
 */
public class Graph {

    private final HashMap<String, Vertex> vertices;
    private final HashMap<Integer, Edge> edges;

    public Graph() {
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
    }

    public Graph(ArrayList<Vertex> vertices) {
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
        vertices.stream().forEach((v) -> {
            this.vertices.put(v.getName(), v);
        });
    }

    public boolean addEdge(Vertex initialVertex, Vertex finalVertex) {
        return addEdge(initialVertex, finalVertex, 1);
    }

    public boolean addEdge(Vertex initialVertex, Vertex finalVertex, int weight) {
        if (initialVertex.equals(finalVertex)) {
            return false;
        }
        Edge e = new Edge(initialVertex, finalVertex, weight);
        if (edges.containsKey(e.hashCode())) {
            return false;
        } else if (initialVertex.containsNeighbor(e) || finalVertex.containsNeighbor(e)) {
            return false;
        }
        edges.put(e.hashCode(), e);
        initialVertex.addNeighbor(e);
        finalVertex.addNeighbor(e);
        return true;
    }

    public boolean containsEdge(Edge e) {
        if (e.getInitialVertex() == null || e.getFinalVertex() == null) {
            return false;
        }
        return this.edges.containsKey(e.hashCode());
    }

    public Edge removeEdge(Edge e) {
        e.getInitialVertex().removeNeighbor(e);
        e.getFinalVertex().removeNeighbor(e);
        return this.edges.remove(e.hashCode());
    }

    public boolean containsVertex(Vertex vertex) {
        return this.vertices.get(vertex.getName()) != null;
    }

    public Vertex getVertex(String label) {
        return vertices.get(label);
    }

    public boolean addVertex(Vertex vertex, boolean overwriteExisting) {
        Vertex current = this.vertices.get(vertex.getName());
        if (current != null) {
            if (!overwriteExisting) {
                return false;
            }
            while (current.getNeighborCount() > 0) {
                this.removeEdge(current.getNeighbor(0));
            }
        }
        vertices.put(vertex.getName(), vertex);
        return true;
    }

    public Vertex removeVertex(String label) {
        Vertex v = vertices.remove(label);
        while (v.getNeighborCount() > 0) {
            this.removeEdge(v.getNeighbor((0)));
        }
        return v;
    }

    public Set<String> vertexKeys() {
        return this.vertices.keySet();
    }

    public Set<Edge> getEdges() {
        return new HashSet<>(this.edges.values());
    }
}
