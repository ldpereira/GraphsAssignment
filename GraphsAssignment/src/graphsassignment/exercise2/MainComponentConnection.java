/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphsassignment.exercise2;

import graphsassignment.model.Graph;
import graphsassignment.model.Vertex;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Renan Bertoldo e Lucas Pereira
 */
public class MainComponentConnection {

    public static void main(String[] args) {
        try {
            File file = new File("C:\\temp\\questao2.txt");
            if (file.exists()) {

                List<String> lines = Files.readAllLines(file.toPath());
                if (!lines.isEmpty()) {
                    StringBuilder sb = new StringBuilder("");
                    int numberOfCases = -1;
                    int actualCase = 1;
                    int numberOfEdges = -1;
                    Graph graph = null;
                    for (String line : lines) {
                        String lineTrim = line.trim();
                        if (numberOfCases == -1) {
                            numberOfCases = Integer.valueOf(lineTrim);
                        } else if (graph == null) {
                            graph = new Graph();
                            String[] values = lineTrim.split(" ");
                            int numberOfVertices = Integer.valueOf(values[0]);
                            for (int index = 0; index < numberOfVertices; index++) {
                                graph.addVertex(new Vertex(Character.toString((char) (97 + index))), true);
                            }
                            numberOfEdges = Integer.valueOf(values[1]);
                        } else if (numberOfEdges > 0) {
                            String[] values = lineTrim.split(" ");
                            graph.addEdge(graph.getVertex(values[0]), graph.getVertex(values[1]));
                            numberOfEdges--;
                        } else {
                            sb.append("Case #").append(actualCase).append(":\n");
                            generateComponents(graph, sb);
                            actualCase++;
                            graph = null;

                            if (!lineTrim.isEmpty()) {
                                graph = new Graph();
                                String[] values = lineTrim.split(" ");
                                int numberOfVertices = Integer.valueOf(values[0]);
                                for (int index = 0; index < numberOfVertices; index++) {
                                    graph.addVertex(new Vertex(Character.toString((char) (97 + index))), true);
                                }
                                numberOfEdges = Integer.valueOf(values[1]);
                            }
                        }
                    }

                    if (graph != null) {
                        sb.append("Case #").append(actualCase).append(":\n");
                        generateComponents(graph, sb);
                    }
                    System.out.println(sb.toString());

                    File fileWrite = new File("C:\\temp\\resposta_questao2.txt");
                    if (!fileWrite.exists()) {
                        fileWrite.createNewFile();
                    }
                    Files.write(fileWrite.toPath(), sb.toString().getBytes());
                    JOptionPane.showMessageDialog(null, "Resposta gerada em C:\\temp\\resposta_questao2.txt");
                } else {
                    JOptionPane.showMessageDialog(null, "O arquivo C:\\temp\\questao2.txt está vazio!");
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainComponentConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainComponentConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int pai(int nodo, int[] vertices) {
        while (nodo != vertices[nodo]) {
            nodo = vertices[nodo];
        }
        return nodo;
    }

    public static ArrayList<String> verifyNeighbors(Vertex v, ArrayList<String> list) {
        if (!list.contains(v.getName())) {
            list.add(v.getName());
        }

        v.getNeighbors().stream().map((edge) -> {
            if (!list.contains(edge.getInitialVertex().getName())) {
                verifyNeighbors(edge.getInitialVertex(), list);
            }
            return edge;
        }).filter((edge) -> (!list.contains(edge.getFinalVertex().getName()))).forEach((edge) -> {
            verifyNeighbors(edge.getFinalVertex(), list);
        });
        return list;
    }

    public static void generateComponents(Graph graph, StringBuilder sb) {
        Set<String> vertices = graph.vertexKeys();
        String verticesValidated = "";
        HashMap<String, ArrayList<String>> list = new HashMap<>();
        for (String vertex : vertices) {
            if (list.isEmpty() || !verticesValidated.contains(vertex)) {
                ArrayList<String> neighbors = new ArrayList<>();
                neighbors = verifyNeighbors(graph.getVertex(vertex), neighbors);
                list.put(vertex, neighbors);
                verticesValidated += neighbors;
            }
        }

        list.values().stream().map((itens) -> {
            itens.stream().forEach((item) -> {
                sb.append(item).append(",");
            });
            return itens;
        }).forEach((_item) -> {
            sb.append("\n");
        });
        sb.append(list.size()).append(" connected components\n\n");
    }
}
