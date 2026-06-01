import java.util.*;

public class BFSBounded {

    static Set<String> bfsBounded(Map<String, List<String>> adj,
                                  String source,
                                  int maxDepth) {

        Set<String> visited = new HashSet<>();
        visited.add(source);

        Deque<Object[]> queue = new ArrayDeque<>();
        queue.offer(new Object[]{source, 0});

        Set<String> reached = new HashSet<>();

        while (!queue.isEmpty()) {

            Object[] cur = queue.poll();

            String u = (String) cur[0];
            int depth = (int) cur[1];

            // TODO 1: Stop expansion at maxDepth
            if (depth == maxDepth)
                continue;

            // TODO 2 & TODO 3
            for (String v : adj.getOrDefault(u, new ArrayList<>())) {

                if (!visited.contains(v)) {

                    visited.add(v);
                    reached.add(v);

                    queue.offer(new Object[]{v, depth + 1});
                }
            }
        }

        return reached;
    }

    public static void main(String[] args) {

        Map<String, List<String>> graph = new HashMap<>();

        graph.put("A", Arrays.asList("B", "C"));
        graph.put("B", Arrays.asList("D", "E"));
        graph.put("C", Arrays.asList("E", "F"));
        graph.put("D", Arrays.asList("G"));
        graph.put("E", Arrays.asList("G", "H"));
        graph.put("F", Arrays.asList("H", "I"));
        graph.put("G", new ArrayList<>());
        graph.put("H", new ArrayList<>());
        graph.put("I", new ArrayList<>());

        Set<String> result = bfsBounded(graph, "A", 3);

        System.out.println("Users reached within depth 3:");
        System.out.println(result);
    }
}