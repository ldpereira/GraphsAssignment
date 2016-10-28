/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphsassignment.model;

import java.util.ArrayList;

/**
 *
 * @author Lucas Pereira e Renan Bertoldo
 */
public class Vertex {

    private final String name;
    private final ArrayList<Edge> neighborhood;    

    public Vertex(String name) {
        this.name = name;
        neighborhood = new ArrayList<>();
    }

    public void addNeighbor(Edge edge) {
        if (neighborhood.contains(edge)) {
            return;
        }
        neighborhood.add(edge);
    }

    public boolean containsNeighbor(Edge other) {
        return neighborhood.contains(other);
    }

    public Edge getNeighbor(int index) {
        return neighborhood.get(index);
    }

    public Edge removeNeighbor(int index) {
        return neighborhood.remove(index);
    }

    public void removeNeighbor(Edge e) {
        neighborhood.remove(e);
    }

    public int getNeighborCount() {
        return neighborhood.size();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Vertex " + name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object comparabeVertex) {
        if (!(comparabeVertex instanceof Vertex)) {
            return false;
        }
        Vertex v = (Vertex) comparabeVertex;
        return name.equals(v.name);
    }

    public ArrayList<Edge> getNeighbors() {
        return new ArrayList<>(neighborhood);
    }
}
