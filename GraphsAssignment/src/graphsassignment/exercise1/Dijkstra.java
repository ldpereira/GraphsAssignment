/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphsassignment.exercise1;

import graphsassignment.model.Vertex;
import graphsassignment.model.Graph;
import graphsassignment.model.Edge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucas Pereira e Renan Bertoldo
 */
public class Dijkstra {

    private Graph graph;
    private String initialVertexLabel;
    private HashMap<String, String> predecessors;
    private HashMap<String, Integer> distances;
    private PriorityQueue<Vertex> availableVertices;
    private HashSet<Vertex> visitedVertices;

    public Dijkstra(Graph graph, String initialVertexLabel) {
        this.graph = graph;
        Set<String> vertexKeys = this.graph.vertexKeys();

        if (!vertexKeys.contains(initialVertexLabel)) {
            JOptionPane.showMessageDialog(null, "O vértice definido como inicial, não existe!");
            throw new IllegalArgumentException("O vértice definido como inicial, não existe!.");
        }

        this.initialVertexLabel = initialVertexLabel;
        this.predecessors = new HashMap<>();
        this.distances = new HashMap<>();
        this.availableVertices = new PriorityQueue<>(vertexKeys.size(), (Vertex initialVertex, Vertex finalVertex) -> {
            int weightOne = Dijkstra.this.distances.get(initialVertex.getName());
            int weightTwo = Dijkstra.this.distances.get(finalVertex.getName());
            return weightOne - weightTwo;
        });

        this.visitedVertices = new HashSet<>();

        vertexKeys.stream().map((key) -> {
            this.predecessors.put(key, null);
            return key;
        }).forEach((key) -> {
            this.distances.put(key, Integer.MAX_VALUE);
        });

        this.distances.put(initialVertexLabel, 0);

        Vertex initialVertex = this.graph.getVertex(initialVertexLabel);
        ArrayList<Edge> initialVertexNeighbors = initialVertex.getNeighbors();

        initialVertexNeighbors.stream().map((e) -> {
            Vertex other = e.getNeighbor(initialVertex);
            this.predecessors.put(other.getName(), initialVertexLabel);
            this.distances.put(other.getName(), e.getWeight());
            return other;
        }).forEach((other) -> {
            this.availableVertices.add(other);
        });
        this.visitedVertices.add(initialVertex);
        processGraph();
    }

    private void processGraph() {
        while (this.availableVertices.size() > 0) {

            Vertex next = this.availableVertices.poll();
            int distanceToNext = this.distances.get(next.getName());
            List<Edge> nextNeighbors = next.getNeighbors();

            nextNeighbors.stream().forEach((e) -> {
                Vertex other = e.getNeighbor(next);
                if (!(this.visitedVertices.contains(other))) {
                    int currentWeight = this.distances.get(other.getName());
                    int newWeight = distanceToNext + e.getWeight();
                    if (newWeight < currentWeight) {
                        this.predecessors.put(other.getName(), next.getName());
                        this.distances.put(other.getName(), newWeight);
                        this.availableVertices.remove(other);
                        this.availableVertices.add(other);
                    }
                }
            });
            this.visitedVertices.add(next);
        }
    }

    public List<Vertex> getPathTo(String destinationLabel) {
        LinkedList<Vertex> path = new LinkedList<>();
        path.add(graph.getVertex(destinationLabel));

        while (!destinationLabel.equals(this.initialVertexLabel)) {
            Vertex predecessor = graph.getVertex(this.predecessors.get(destinationLabel));
            destinationLabel = predecessor.getName();
            path.add(0, predecessor);
        }
        return path;
    }

    public int getDistanceTo(String destinationLabel) {
        return this.distances.get(destinationLabel);
    }

}
